

package be.kuleuven.hotelrestservice.service;

import be.kuleuven.hotelrestservice.data.hotel.HotelResponse;
import be.kuleuven.hotelrestservice.data.hotel.HotelResponseDto;
import be.kuleuven.hotelrestservice.data.room.RoomResponse;
import be.kuleuven.hotelrestservice.entity.Hotel;
import be.kuleuven.hotelrestservice.entity.QHotel;
import be.kuleuven.hotelrestservice.entity.QRoom;
import be.kuleuven.hotelrestservice.repository.HotelRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class HotelsService {

    private final JPAQueryFactory queryFactory;
    private final HotelRepository hotelRepository;

    public List<HotelResponse> getAllHotels(
            String arrivalLocation,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            Integer numberOfRooms,
            Integer numberOfAdults,
            Integer numberOfChildren) {

        BooleanBuilder hotelBuilder = new BooleanBuilder();
        BooleanBuilder roomBuilder = new BooleanBuilder();
        QHotel hotels = QHotel.hotel;
        QRoom rooms = QRoom.room;

        /* Construct arrival locations */
        if (arrivalLocation != null && !arrivalLocation.isBlank()) {
            hotelBuilder.and(hotels.location.eq(arrivalLocation.replaceAll("^\"|\"$", "").trim()));
        }

        /* Check-in / Check-out Availability in Room */
        if (checkInDate != null && checkOutDate != null) {
            LocalDateTime checkIn = checkInDate.atStartOfDay();
            LocalDateTime checkOut = checkOutDate.atTime(LocalTime.MAX);

            roomBuilder.and(rooms.fromDate.goe(checkIn));
            roomBuilder.and(rooms.toDate.loe(checkOut));
            roomBuilder.and(rooms.isAvailable.isTrue());
        }

        int totalGuests = (numberOfAdults != null ? numberOfAdults : 0) + (numberOfChildren != null ? numberOfChildren : 0);
        if (numberOfRooms != null && numberOfRooms > 0) {
            hotelBuilder.and(hotels.availableRooms.goe(numberOfRooms));
        }

        List<Hotel> hotelsList = queryFactory
                .selectDistinct(hotels)
                .from(hotels)
                .leftJoin(hotels.rooms, rooms)
                .where(hotelBuilder.and(roomBuilder))
                .fetch();

        if (hotelsList.isEmpty()) return Collections.emptyList();

        return hotelsList.stream()
                .map(hotel -> HotelResponse.builder()
//                .id(String.valueOf(hotel.getId()))
                .id(hotel.getId())
                .name(hotel.getHotelName())
                .location(hotel.getLocation())
                .numberOfStars(hotel.getNumberOfStars())
                .availableRooms(hotel.getAvailableRooms())
                .priceAdult(hotel.getPriceAdult())
                .priceChild(hotel.getPriceChild())
//                .roomService(hotel.isRoomService())
//                .breakfast(hotel.isBreakfast())
                .build())
                .collect(Collectors.toList());
    }

    public Optional<HotelResponseDto> findHotelById(String id) {
        Assert.notNull(id, "Hotel ID must not be null");

        return hotelRepository.findById(id).map(hotel -> HotelResponseDto.builder()
//                return hotelRepository.findById(id).map(hotel -> HotelResponseDto.builder()
                .id(hotel.getId())
                .hotelName(hotel.getHotelName())
                .location(hotel.getLocation())
                .numberOfStars(hotel.getNumberOfStars())
                .availableRooms(hotel.getAvailableRooms())
                .totalRooms(hotel.getTotalRooms())
                .priceAdult(hotel.getPriceAdult())
                .priceChild(hotel.getPriceChild())
//                .roomService(hotel.isRoomService())
//                .breakfast(hotel.isBreakfast())
                .rooms(hotel.getRooms().stream()
                        .map(room -> RoomResponse.builder()
                                .id(String.valueOf(room.getId()))
                                .type(room.getType())
                                .build())
                        .collect(Collectors.toList()))
                .build());
    }
}
