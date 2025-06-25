package be.kuleuven.hotelrestservice.repository;

import be.kuleuven.hotelrestservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String>, QuerydslPredicateExecutor<Booking> {

    Booking findByPackageBookingId(String packageBookingId);
}
