package be.kuleuven.hotelrestservice.entity;

import be.kuleuven.hotelrestservice.enums.EBookingStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class Booking extends AbstractEntity {
    @NotBlank
    @Column(name = "package_booking_id")
    private String packageBookingId;

    @NotBlank
    @Column(name = "user_id")
    private String userId;

    @NotBlank
    @Column(name = "flight_id", insertable=false, updatable=false)
    private String flightId;

    @NotNull
    @Column(name = "total_seat_count")
    private Integer totalSeatCount;

    @NotNull
    @Column(name = "adult_seat_count")
    private Integer adultSeatCount;

    @NotNull
    @Column(name = "child_seat_count")
    private Integer childSeatCount;

    @NotNull
    @Column(name = "infant_seat_count")
    private Integer infantSeatCount;

    @NotNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EBookingStatus status;
}
