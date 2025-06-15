//package be.kuleuven.hotelrestservice.entity;
//
//public class Hotel {
//}

package be.kuleuven.hotelrestservice.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "hotels")
public class Hotel {
    @Id
    @NotBlank
    private Integer id;

    @NotBlank
    @Column(name = "hotel_name")
    private String hotelName;

    @NotBlank
    @Column(name = "location")
    private String location;

    @NotBlank
    @Column(name = "room_service")
    private boolean roomService;

    @NotBlank
    @Column(name = "breakfast")
    private boolean breakfast;

    @NotBlank
    @Column(name = "number_of_stars")
    private int numberOfStars;

    @NotBlank
    @Column(name = "available_rooms")
    private int availableRooms;

    @NotBlank
    @Column(name = "total_rooms")
    private int totalRooms;

    @NotBlank
    @Column(name = "price_adult")
    private BigDecimal priceAdult;

    @NotBlank
    @Column(name = "price_child")
    private BigDecimal priceChild;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "hotels_rooms",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private Set<Room> rooms = new HashSet<>();
}
