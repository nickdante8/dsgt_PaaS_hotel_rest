package be.kuleuven.hotelrestservice.data.booking;

import be.kuleuven.hotelrestservice.data.visitor.VisitorResponseDto;
import be.kuleuven.hotelrestservice.enums.EBookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class BookingResponseDto {
    private String packageBookingId;
    private String hotelId;
    private EBookingStatus status;
    private BigDecimal totalPrice;
    private Integer adultCount;
    private Integer childCount;
    private List<VisitorResponseDto> visitors;
}
