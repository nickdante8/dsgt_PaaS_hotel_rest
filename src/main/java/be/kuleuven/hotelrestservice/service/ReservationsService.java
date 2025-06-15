package be.kuleuven.hotelrestservice.service;

import be.kuleuven.hotelrestservice.data.reservation.ReservationRequest;
import be.kuleuven.hotelrestservice.data.reservation.ReservationResponseDto;
import be.kuleuven.hotelrestservice.data.reservation.ReservationResponsePatch;
import be.kuleuven.hotelrestservice.entity.Hotel;
import be.kuleuven.hotelrestservice.entity.Room;
import be.kuleuven.hotelrestservice.enums.EReservationStatus;
import be.kuleuven.hotelrestservice.entity.QReservation;
import be.kuleuven.hotelrestservice.entity.Reservation;
import be.kuleuven.hotelrestservice.enums.ERoomType;
import be.kuleuven.hotelrestservice.repository.HotelRepository;
import be.kuleuven.hotelrestservice.repository.ReservationRepository;
import be.kuleuven.hotelrestservice.security.jwt.JwtUtils;
import com.querydsl.core.BooleanBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class ReservationsService {

    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final JwtUtils jwtUtils;

    public Optional<ReservationResponseDto> findReservationByPackageBookingId(String token, String packageBookingId) {
        String userId = jwtUtils.getUserIdFromJwtToken(token);
        BooleanBuilder builder = new BooleanBuilder();
        QReservation r = QReservation.reservation;

        Assert.notNull(packageBookingId, "The package booking id must not be null.");
        Assert.notNull(token, "The token must not be null.");
        Assert.notNull(userId, "The user must not be null.");

        builder.and(r.packageBookingId.eq(packageBookingId));
        builder.and(r.userId.eq(userId));

        Optional<Reservation> reservation = StreamSupport
                .stream(reservationRepository.findAll(builder).spliterator(), false)
                .findFirst();

        return reservation.map(res -> ReservationResponseDto.builder()
                .packageBookingId(res.getPackageBookingId())
                .hotelId(res.getHotelId())
                .fromDate(res.getFromDate())
                .toDate(res.getToDate())
                .status(res.getStatus())
                .numAdults(res.getNumAdults())
                .numChildren(res.getNumChildren())
                .totalPrice(res.getTotalPrice())
                .roomType(res.getRoomType())
                .build());
    }

    public Optional<ReservationResponseDto> createReservation(@NotNull String token, ReservationRequest request) {
        try {
            String userId = jwtUtils.getUserIdFromJwtToken(token);

            if (request.getHotelId() != null) {
                Optional<Hotel> hotelOpt = hotelRepository.findById(String.valueOf(request.getHotelId()));

                if (hotelOpt.isEmpty()) return Optional.empty();

                Hotel hotel = hotelOpt.get();

                long nights = ChronoUnit.DAYS.between(request.getFromDate(), request.getToDate());
                if (nights <= 0) return Optional.empty();

                BigDecimal totalPrice = hotel.getPriceAdult()
                        .multiply(BigDecimal.valueOf(request.getNumAdults()))
                        .add(hotel.getPriceChild().multiply(BigDecimal.valueOf(request.getNumChildren())))
                        .multiply(BigDecimal.valueOf(nights));

                Reservation res = new Reservation();
                res.setPackageBookingId(request.getPackageBookingId());
                res.setUserId(userId);
                res.setHotelId(hotel.getId());
                res.setHotels(hotel);
                res.setFromDate(request.getFromDate());
                res.setToDate(request.getToDate());
                res.setNumAdults(request.getNumAdults());
                res.setNumChildren(request.getNumChildren());
                res.setTotalPrice(totalPrice);
                res.setStatus(EReservationStatus.RESERVATION_STATUS_CONFIRMED);
                res.setRoomType(ERoomType.Deluxe);

                reservationRepository.save(res);

                return this.findReservationByPackageBookingId(token, request.getPackageBookingId());
            }

            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<ReservationResponsePatch> patchReservationStatus(@NotNull String token, String packageBookingId) {
        try {
            Optional<ReservationResponseDto> reservationDto = this.findReservationByPackageBookingId(token, packageBookingId);

            if (reservationDto.isEmpty()) return Optional.empty();

            Reservation reservation = reservationRepository.findByPackageBookingId(packageBookingId);
            reservation.setStatus(EReservationStatus.RESERVATION_STATUS_CANCELLED);

            reservationRepository.save(reservation);

            return Optional.of(ReservationResponsePatch.builder()
                    .packageBookingId(reservation.getPackageBookingId())
                    .status(reservation.getStatus())
                    .build());

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
