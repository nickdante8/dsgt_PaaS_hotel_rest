package be.kuleuven.hotelrestservice.data.hotel;
import be.kuleuven.hotelrestservice.enums.ERoomType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder

public class HotelResponse {
    private String id;
    private String name;
    private String location;
    private ERoomType roomType;
    private Boolean roomService;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Boolean breakfast;
    private Integer numberOfStars;
    private BigDecimal priceAdult;
    private BigDecimal priceChild;
    private Integer availableRooms;
}


