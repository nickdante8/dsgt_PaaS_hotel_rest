//package be.kuleuven.hotelrestservice.entity;
//
//public class Reservation {
//}

package be.kuleuven.hotelrestservice.entity;
import be.kuleuven.hotelrestservice.enums.EReservationStatus;
import be.kuleuven.hotelrestservice.enums.ERoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "reservations")
public class Reservation extends AbstractEntity {
    @NotBlank
    @Column(name = "package_booking_id")
    private String packageBookingId;

    @NotBlank
    @Column(name = "user_id")
    private String userId; // agency or travel agent

    @NotBlank
    @Column(name = "hotel_id", insertable=false, updatable=false)
    private Integer hotelId;

    @NotBlank
    @Column(name = "from_date")
    private LocalDateTime fromDate;

    @NotBlank
    @Column(name = "to_date")
    private LocalDateTime toDate;

    @NotBlank
    @Column(name = "num_adults")
    private int numAdults;

    @NotBlank
    @Column(name = "num_children")
    private int numChildren;

    @NotBlank
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private EReservationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private ERoomType roomType;

    @ManyToOne
    @JoinColumn(name="hotel_id", referencedColumnName = "id")
    private Hotel hotels;

    @OneToMany(mappedBy = "reservations")
    private Set<Visitor> visitors = new HashSet<>();

}
