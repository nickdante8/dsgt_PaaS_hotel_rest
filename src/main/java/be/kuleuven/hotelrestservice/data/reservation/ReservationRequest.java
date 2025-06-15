package be.kuleuven.hotelrestservice.data.reservation;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequest {
    private String packageBookingId;
    private Integer hotelId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Integer numAdults;
    private Integer numChildren;

}
