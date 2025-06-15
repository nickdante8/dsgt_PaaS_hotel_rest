package be.kuleuven.hotelrestservice.data.hotel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder

public class HotelResponse {
    private Integer id;
    private String hotelName;
    private String location;
    private Boolean roomService;
    private Boolean breakfast;
    private Integer numberOfStars;
    private Integer availableRooms;
    private Integer totalRooms;
    private BigDecimal priceAdult;
    private BigDecimal priceChild;

}


