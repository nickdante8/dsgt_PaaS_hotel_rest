package be.kuleuven.hotelrestservice.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private String roomType;
    private int capacityAdults;
    private int capacityChildren;
    private boolean roomService;
    private boolean breakfast;
    private double price;
    private boolean available;

    // Getters and Setters
}
