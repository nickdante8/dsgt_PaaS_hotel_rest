package be.kuleuven.hotelrestservice.data.booking;

import be.kuleuven.hotelrestservice.enums.EBookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookingResponsePatch {
    private String packageBookingId;
    private EBookingStatus status;
}
