package be.kuleuven.hotelrestservice.data.reservation;

import be.kuleuven.hotelrestservice.enums.EReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class ReservationResponsePatch {
    private String packageBookingId;
    private EReservationStatus status;
}
