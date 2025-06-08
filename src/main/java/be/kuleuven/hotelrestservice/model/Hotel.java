package be.kuleuven.hotelrestservice.model;
// src/main/java/be/kuleuven/hotelrestservice/model/
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String location;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;

    // Getters and Setters
}
