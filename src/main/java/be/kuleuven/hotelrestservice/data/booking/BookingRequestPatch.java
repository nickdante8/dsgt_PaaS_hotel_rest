package be.kuleuven.hotelrestservice.data.booking;

import be.kuleuven.hotelrestservice.enums.EBookingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequestPatch {
    private EBookingStatus status;
}
