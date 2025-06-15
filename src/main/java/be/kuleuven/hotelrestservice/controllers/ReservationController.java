package be.kuleuven.hotelrestservice.controllers;

import be.kuleuven.hotelrestservice.data.reservation.ReservationRequest;
import be.kuleuven.hotelrestservice.data.reservation.ReservationResponseDto;
import be.kuleuven.hotelrestservice.data.reservation.ReservationResponsePatch;
import be.kuleuven.hotelrestservice.service.ReservationsService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationsService reservationsService;

    @GetMapping("/{packageBookingId}")
    public ResponseEntity<ReservationResponseDto> getReservationByPackageBookingId(
            @RequestHeader(value = "Authorization") @NotNull String authorization,
            @PathVariable("packageBookingId") String packageBookingId) {

        return reservationsService.findReservationByPackageBookingId(authorization, packageBookingId)
                .map(reservation -> new ResponseEntity<>(reservation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(
            @RequestHeader(value = "Authorization") @NotNull String authorization,
            @RequestBody ReservationRequest reservationRequest) {

        return reservationsService.createReservation(authorization, reservationRequest)
                .map(reservation -> new ResponseEntity<>(reservation, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PatchMapping("/cancel/{packageBookingId}")
    public ResponseEntity<ReservationResponsePatch> cancelReservation(
            @RequestHeader(value = "Authorization") @NotNull String authorization,
            @PathVariable("packageBookingId") String packageBookingId) {

        return reservationsService.patchReservationStatus(authorization, packageBookingId)
                .map(reservation -> new ResponseEntity<>(reservation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
