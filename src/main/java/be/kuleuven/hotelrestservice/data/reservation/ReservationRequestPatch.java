package be.kuleuven.hotelrestservice.data.reservation;

import be.kuleuven.hotelrestservice.enums.EReservationStatus;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ReservationRequestPatch {
    private EReservationStatus status;
}
