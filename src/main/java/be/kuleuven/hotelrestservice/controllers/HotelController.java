package be.kuleuven.hotelrestservice.controllers;

import be.kuleuven.hotelrestservice.data.hotel.HotelResponse;
import be.kuleuven.hotelrestservice.data.hotel.HotelResponseDto;
import be.kuleuven.hotelrestservice.service.HotelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelsService hotelsService;

    @Autowired
    public HotelController(HotelsService hotelsService) {
        this.hotelsService = hotelsService;
    }

    @GetMapping
    public ResponseEntity<List<HotelResponse>> getHotels(
            @RequestParam(name = "arrivalLocation")
            String arrivalLocation,
            @RequestParam(name = "checkInDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate checkInDate,
            @RequestParam(name = "checkOutDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate checkOutDate,
            @RequestParam(name = "numberOfRooms")
            Integer numberOfRooms,
            @RequestParam(name = "numberOfAdults")
            Integer numberOfAdults,
            @RequestParam(name = "numberOfChildren")
            Integer numberOfChildren
    ) {
        List<HotelResponse> hotels = hotelsService.getAllHotels(arrivalLocation, checkInDate, checkOutDate, numberOfRooms, numberOfAdults, numberOfChildren);

        if (hotels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDto> getHotelById(@PathVariable("id") String id) {
        return hotelsService.findHotelById(id)
                .map(hotel -> new ResponseEntity<>(hotel, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
