package be.kuleuven.hotelrestservice.data.room;


import be.kuleuven.hotelrestservice.enums.ERoomType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder

public class RoomResponse {
    private String id;
    private ERoomType type;
    private BigDecimal price;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Boolean is_available;

}
