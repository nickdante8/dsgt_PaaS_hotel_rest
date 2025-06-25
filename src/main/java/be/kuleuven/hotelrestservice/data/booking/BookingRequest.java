package be.kuleuven.hotelrestservice.data.booking;

import be.kuleuven.hotelrestservice.enums.ERoomType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingRequest {
    private String packageBookingId;
    private String hotelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ERoomType roomType;
    private Integer adultCount;
    private Integer childCount;
}
