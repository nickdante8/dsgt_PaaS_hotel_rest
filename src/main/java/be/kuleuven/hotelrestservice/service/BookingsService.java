package be.kuleuven.hotelrestservice.service;

import be.kuleuven.hotelrestservice.data.booking.*;
import be.kuleuven.hotelrestservice.entity.Booking;
import be.kuleuven.hotelrestservice.entity.Hotel;
import be.kuleuven.hotelrestservice.entity.QBooking;
import be.kuleuven.hotelrestservice.enums.EBookingStatus;
import be.kuleuven.hotelrestservice.repository.BookingRepository;
import be.kuleuven.hotelrestservice.repository.HotelRepository;
import be.kuleuven.hotelrestservice.security.jwt.JwtUtils;
import com.querydsl.core.BooleanBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static be.kuleuven.hotelrestservice.entity.QBooking.booking;
import static be.kuleuven.hotelrestservice.enums.EBookingSearchStatus.BOOKING_SEARCH_STATUS_FOUND;
import static be.kuleuven.hotelrestservice.enums.EBookingSearchStatus.BOOKING_SEARCH_STATUS_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class BookingsService {

    private final BookingRepository bookingsRepository;
    private final HotelRepository hotelRepository;
    private final JwtUtils jwtUtils;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<BookingSearchResult> chckIfExists(String token, String packageBookingId) {
        String userId = jwtUtils.getUserIdFromJwtToken(token);
        BooleanBuilder builder = new BooleanBuilder();
        QBooking bookings = booking;

        Assert.notNull(packageBookingId, "The package booking id must not be null.");
        Assert.notNull(token, "The package booking token must not be null.");
        Assert.notNull(userId, "The package booking user must not be null.");

        /* Assembly builder search */
        builder.and(bookings.packageBookingId.eq(packageBookingId));
        builder.and(bookings.userId.eq(userId));

        /* Create response */
        Optional <Booking> booking = StreamSupport.stream(bookingsRepository.findAll(builder).spliterator(), false).findFirst();
        return booking.map(value -> BookingSearchResult.builder()
                .searchStatus(BOOKING_SEARCH_STATUS_FOUND)
                .booking(value)
                .build()).or(() -> Optional.ofNullable(BookingSearchResult.builder()
                .searchStatus(BOOKING_SEARCH_STATUS_NOT_FOUND)
                .booking(null)
                .build()));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<BookingResponseDto> findBookingByPackageBookingId(String token, String packageBookingId) {
        Optional <BookingSearchResult> bookingSearchResult = chckIfExists(token, packageBookingId);

        if (bookingSearchResult.get().getSearchStatus().equals(BOOKING_SEARCH_STATUS_FOUND)) {
            return bookingSearchResult.map(b -> BookingResponseDto.builder()
                    .packageBookingId(b.getBooking().getPackageBookingId())
                    .hotelId(b.getBooking().getHotelId().getId())
                    .status(b.getBooking().getStatus())
                    .totalPrice(b.getBooking().getTotalPrice())
                    .adultCount(b.getBooking().getNumAdults())
                    .childCount(b.getBooking().getNumChildren())
                    .build());
        } else  {
            return Optional.empty();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<BookingResponseDto> createBooking(@NotNull String token, BookingRequest bookingRequest) {
        try {
            String userId = jwtUtils.getUserIdFromJwtToken(token);

            Optional <BookingSearchResult> bookingSearchResult = chckIfExists(token, bookingRequest.getPackageBookingId());
            if (bookingSearchResult.get().getSearchStatus().equals(BOOKING_SEARCH_STATUS_FOUND)) {
                return Optional.empty();
            } else {
                if (bookingRequest.getHotelId() != null) {
                    // Fetch flights from DB
                    Optional<Hotel> hotelOpt = hotelRepository.findById(bookingRequest.getHotelId());

                    if (hotelOpt.isEmpty()) {
                        return Optional.empty(); // flights not found
                    }

                    Hotel hotel = hotelOpt.get();

//                    int totalRequestedSeats = bookingRequest.getAdultCount() + bookingRequest.getChildCount();
//
//                    // Check seat availability
//                    if (hotel.getAvailableSeats() < totalRequestedSeats ||
//                            arrivalFlight.getAvailableSeats() < totalRequestedSeats) {
//                        return Optional.empty(); // not enough seats
//                    }

                    // Prepare and save booking
                    Booking bookingToSave = new Booking();
                    bookingToSave.setPackageBookingId(bookingRequest.getPackageBookingId());
                    bookingToSave.setUserId(userId);
                    bookingToSave.setHotelId(hotel); // assumes @ManyToOne mapping
                    bookingToSave.setFromDate(bookingRequest.getCheckInDate().atStartOfDay());
                    bookingToSave.setToDate(bookingRequest.getCheckOutDate().atStartOfDay());
                    bookingToSave.setNumAdults(bookingRequest.getAdultCount());
                    bookingToSave.setNumChildren(bookingRequest.getChildCount());
                    bookingToSave.setTotalPrice(
                            hotel.getPriceAdult()
                                    .multiply(BigDecimal.valueOf(bookingRequest.getAdultCount()))
                                    .add(hotel.getPriceChild()
                                            .multiply(BigDecimal.valueOf(bookingRequest.getChildCount()))));
//                    bookingToSave.getFlightDeparture().setAvailableSeats(departureFlight.getAvailableSeats() - totalRequestedSeats);
//                    bookingToSave.getFlightArrival().setAvailableSeats(arrivalFlight.getAvailableSeats() - totalRequestedSeats);
                    bookingToSave.setStatus(EBookingStatus.BOOKING_STATUS_CONFIRMED);

                    bookingsRepository.save(bookingToSave);

                    /* Get updated package from DB and send back as a response */
                    return this.findBookingByPackageBookingId(token, bookingRequest.getPackageBookingId());
                } else {
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<BookingResponsePatch> patchBookingStatus(@NotNull String token, String packageBookingId) {
        try {
            Optional <BookingSearchResult> bookingSearchResult = chckIfExists(token, packageBookingId);

            if (bookingSearchResult.get().getSearchStatus().equals((BOOKING_SEARCH_STATUS_NOT_FOUND))) {
                return Optional.empty();
            } else {
                Booking bookingToPatch = bookingSearchResult.get().getBooking();
                bookingToPatch.setStatus(EBookingStatus.BOOKING_STATUS_CANCELLED);
                bookingsRepository.save(bookingToPatch);
                return Optional.ofNullable(BookingResponsePatch.builder()
                        .packageBookingId(bookingToPatch.getPackageBookingId())
                        .status(bookingToPatch.getStatus())
                        .build());
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
