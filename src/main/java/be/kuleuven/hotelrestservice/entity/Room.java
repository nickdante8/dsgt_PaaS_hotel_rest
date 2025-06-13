package be.kuleuven.hotelrestservice.entity;
import be.kuleuven.hotelrestservice.enums.ERoomType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class Room extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EnumType type;

    @NotBlank
    @Column(name = "price")
    private BigDecimal price;

    @NotBlank
    @Column(name = "from_date")
    private LocalDateTime fromDate;

    @NotBlank
    @Column(name = "to_date")
    private LocalDateTime toDate;

    @NotBlank
    @Column(name = "is_available")
    private boolean is_available;

    @NotBlank
    @Column(name = "hotel_id", insertable=false, updatable=false)
    private String hotelId;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

}
