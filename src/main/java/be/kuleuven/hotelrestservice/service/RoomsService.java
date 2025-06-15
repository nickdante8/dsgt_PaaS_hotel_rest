package be.kuleuven.hotelrestservice.service;

import be.kuleuven.hotelrestservice.data.room.RoomResponse;
import be.kuleuven.hotelrestservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoomsService {
    private final RoomRepository roomsRepository;

    public List<RoomResponse> getRoomsByHotelId(String hotelId) {
        return roomsRepository.findByHotelId(Integer.valueOf(hotelId)).stream()
                .map(room -> RoomResponse.builder()
                        .id(String.valueOf(room.getId()))
                        //.id(seat.getId())
                        .type(room.getType())
                        .build()).collect(Collectors.toList());
    }
}
