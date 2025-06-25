package be.kuleuven.hotelrestservice.data.booking;

import be.kuleuven.hotelrestservice.entity.Booking;
import be.kuleuven.hotelrestservice.enums.EBookingSearchStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookingSearchResult {
    private Booking booking;
    private EBookingSearchStatus searchStatus;
}
