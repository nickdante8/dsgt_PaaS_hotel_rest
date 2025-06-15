package be.kuleuven.hotelrestservice.data.reservation;
import be.kuleuven.hotelrestservice.data.visitor.VisitorResponseDto;
import be.kuleuven.hotelrestservice.enums.EReservationStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder

public class ReservationResponseDto {
    private String packageBookingId;
    private Integer hotelId;
    private EReservationStatus status;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private BigDecimal totalPrice;
    private Integer numAdults;
    private Integer numChildren;
    private List<VisitorResponseDto> visitors;
}
