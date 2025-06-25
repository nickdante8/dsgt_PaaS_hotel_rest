//package be.kuleuven.hotelrestservice.entity;
//
//public class Hotel {
//}

package be.kuleuven.hotelrestservice.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String id;

    @NotBlank
    @Column(name = "hotel_name")
    private String hotelName;

    @NotBlank
    @Column(name = "location")
    private String location;

    @NotNull
    @Column(name = "room_service")
    private Boolean roomService;

    @NotNull
    @Column(name = "breakfast")
    private Boolean breakfast;

    @NotNull
    @Column(name = "number_of_stars")
    private Integer numberOfStars;

    @NotNull
    @Column(name = "available_rooms")
    private Integer availableRooms;

    @NotNull
    @Column(name = "total_rooms")
    private Integer totalRooms;

    @NotNull
    @Column(name = "price_adult")
    private BigDecimal priceAdult;

    @NotNull
    @Column(name = "price_child")
    private BigDecimal priceChild;

    @OneToMany(mappedBy = "hotel")
    private Set<Room> rooms = new HashSet<>();
}
