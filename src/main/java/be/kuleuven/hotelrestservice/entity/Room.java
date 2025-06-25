package be.kuleuven.hotelrestservice.entity;
import be.kuleuven.hotelrestservice.enums.ERoomType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @Column(name = "hotel_id", insertable=false, updatable=false)
    private String hotelId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ERoomType type;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @Column(name = "from_date")
    private LocalDateTime fromDate;

    @NotNull
    @Column(name = "to_date")
    private LocalDateTime toDate;

    @NotNull
    @Column(name = "is_available")
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;
}
