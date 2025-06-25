

package be.kuleuven.hotelrestservice.service;

import be.kuleuven.hotelrestservice.data.hotel.HotelResponse;
import be.kuleuven.hotelrestservice.data.hotel.HotelResponseDto;
import be.kuleuven.hotelrestservice.data.room.RoomResponse;
import be.kuleuven.hotelrestservice.entity.Hotel;
import be.kuleuven.hotelrestservice.entity.QHotel;
import be.kuleuven.hotelrestservice.repository.HotelRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HotelsService {

    private final HotelRepository hotelRepository;

    public List<HotelResponse> getAllHotels(
            String location,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            Integer adultCount,
            Integer childCount) {

        BooleanBuilder builder = new BooleanBuilder();
        QHotel qHotel = QHotel.hotel;

        if (location != null && !location.isBlank()) {
            builder.and(qHotel.location.eq(location.trim()));
        }

        int totalGuests = (adultCount != null ? adultCount : 0) + (childCount != null ? childCount : 0);
        if (totalGuests > 0) {
            builder.and(qHotel.availableRooms.goe(1)); // You can enhance this to match rooms per guest
        }

        List<Hotel> hotels = (List<Hotel>) hotelRepository.findAll(builder);

        if (hotels.isEmpty()) return Collections.emptyList();

        return hotels.stream().map(hotel -> HotelResponse.builder()
//                .id(String.valueOf(hotel.getId()))
                .id(hotel.getId())
                .hotelName(hotel.getHotelName())
                .location(hotel.getLocation())
                .numberOfStars(hotel.getNumberOfStars())
                .availableRooms(hotel.getAvailableRooms())
                .priceAdult(hotel.getPriceAdult())
                .priceChild(hotel.getPriceChild())
                .roomService(hotel.isRoomService())
                .breakfast(hotel.isBreakfast())
                .build()
        ).collect(Collectors.toList());
    }

    public Optional<HotelResponseDto> findHotelById(Integer id) {
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
                .roomService(hotel.isRoomService())
                .breakfast(hotel.isBreakfast())
                .rooms(hotel.getRooms().stream()
                        .map(room -> RoomResponse.builder()
                                .id(String.valueOf(room.getId()))
                                .type(room.getType())
                                .build())
                        .collect(Collectors.toList()))
                .build());
    }
}
