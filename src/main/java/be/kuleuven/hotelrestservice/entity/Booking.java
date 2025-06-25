package be.kuleuven.hotelrestservice.entity;
import be.kuleuven.hotelrestservice.enums.EBookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private String userId; // agency or travel agent

    @NotNull
    @Column(name = "from_date")
    private LocalDateTime fromDate;

    @NotNull
    @Column(name = "to_date")
    private LocalDateTime toDate;

    @NotNull
    @Column(name = "num_adults")
    private Integer numAdults;

    @NotNull
    @Column(name = "num_children")
    private Integer numChildren;

    @NotNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private EBookingStatus status;

    @ManyToOne
    @JoinColumn(name="hotel_id", referencedColumnName = "id")
    private Hotel hotelId;

    @OneToMany(mappedBy = "bookings")
    private Set<Visitor> visitors = new HashSet<>();

}
