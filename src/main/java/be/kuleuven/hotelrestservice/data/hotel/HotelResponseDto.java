package be.kuleuven.hotelrestservice.data.hotel;
import be.kuleuven.hotelrestservice.data.room.RoomResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class HotelResponseDto {
    private String id;
    private String hotelName;
    private String location;
    private Boolean roomService;
    private Boolean breakfast;
    private Integer numberOfStars;
    private Integer availableRooms;
    private Integer totalRooms;
    private BigDecimal priceAdult;
    private BigDecimal priceChild;
    private List<RoomResponse> rooms;
}


