package be.kuleuven.hotelrestservice.controllers;

import be.kuleuven.hotelrestservice.data.booking.*;
import be.kuleuven.hotelrestservice.enums.EBookingSearchStatus;
import be.kuleuven.hotelrestservice.service.BookingsService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingsService bookingService;

    @Autowired
    BookingController(BookingsService bookingService) {
        this.bookingService = bookingService;
    }


    @GetMapping("/{packageBookingId}")
    public ResponseEntity<BookingResponseDto> getBookingById(@RequestHeader(value = "Authorization") @NotNull String authorization,
                                                             @PathVariable("packageBookingId") String packageBookingId) {
        return bookingService.findBookingByPackageBookingId(authorization, packageBookingId)
                .map(booking -> new ResponseEntity<>(booking, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> setBooking(@RequestHeader(value = "Authorization") @NotNull String authorization,
                                                             @RequestBody BookingRequest bookingRequest) {
        Optional<BookingSearchResult> bookingSearchResult = bookingService.chckIfExists(authorization, bookingRequest.getPackageBookingId());

        if (bookingSearchResult.get().getSearchStatus().equals(EBookingSearchStatus.BOOKING_SEARCH_STATUS_FOUND)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return bookingService.createBooking(authorization, bookingRequest)
                    .map(booking -> new ResponseEntity<>(booking, HttpStatus.CREATED))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }
    }

    @PatchMapping("/cancel/{packageBookingId}")
    public ResponseEntity<BookingResponsePatch> updateBookingStatus(@RequestHeader(value = "Authorization") @NotNull String authorization,
                                                                    @PathVariable("packageBookingId") String packageBookingId) {
        return bookingService.patchBookingStatus(authorization, packageBookingId)
                .map(booking -> new ResponseEntity<>(booking, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
