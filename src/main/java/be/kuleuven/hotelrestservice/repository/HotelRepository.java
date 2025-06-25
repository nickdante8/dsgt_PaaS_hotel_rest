package be.kuleuven.hotelrestservice.repository;

import be.kuleuven.hotelrestservice.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String>, QuerydslPredicateExecutor<Hotel> {
}
