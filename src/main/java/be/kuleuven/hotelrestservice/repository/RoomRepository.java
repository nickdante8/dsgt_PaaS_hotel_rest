package be.kuleuven.hotelrestservice.repository;

import be.kuleuven.hotelrestservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String>, QuerydslPredicateExecutor<Room> {
    List<Room> findByHotelId(String hotelId);
}
