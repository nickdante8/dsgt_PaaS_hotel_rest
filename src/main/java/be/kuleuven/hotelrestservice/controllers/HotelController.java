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
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "fromDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(name = "toDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(name = "adultCount", required = false, defaultValue = "1") Integer adultCount,
            @RequestParam(name = "childCount", required = false, defaultValue = "0") Integer childCount
    ) {
        List<HotelResponse> hotels = hotelsService.getAllHotels(location, fromDate, toDate, adultCount, childCount);

        if (hotels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDto> getHotelById(@PathVariable("id") Long id) {
        return hotelsService.findHotelById(Math.toIntExact(id))
                .map(hotel -> new ResponseEntity<>(hotel, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
