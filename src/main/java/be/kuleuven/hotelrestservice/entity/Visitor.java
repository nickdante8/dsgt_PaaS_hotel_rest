package be.kuleuven.hotelrestservice.entity;
import be.kuleuven.hotelrestservice.enums.EVisitorType;
import be.kuleuven.hotelrestservice.enums.ESexType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "visitors")
public class Visitor extends AbstractEntity {
    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EVisitorType type;

    @NotBlank
    @Size(max = 100)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(max = 100)
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "sex_type")
    private ESexType sexType;

    @NotNull
    @Column(name = "room_id", insertable=false, updatable=false)
    private Integer room_id;

    @NotNull
    @Column(name = "reservation_id", insertable=false, updatable=false)
    private Long reservationID;

    @ManyToOne
    @JoinColumn(name="room_id", referencedColumnName = "id")
    private Room rooms;

    @ManyToOne
    @JoinColumn(name="reservation_id", referencedColumnName = "id")
    private Reservation reservations;
//
//    @ManyToOne
//    @JoinColumn(name="hotel_id", referencedColumnName = "id")
//    private Hotel hotels;

}
