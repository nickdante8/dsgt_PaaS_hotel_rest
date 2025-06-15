package be.kuleuven.hotelrestservice.repository;

import be.kuleuven.hotelrestservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String>, QuerydslPredicateExecutor<Reservation> {
    Reservation findByPackageBookingId(String packageBookingId);
}
