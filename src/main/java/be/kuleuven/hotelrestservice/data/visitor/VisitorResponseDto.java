package be.kuleuven.hotelrestservice.data.visitor;
import be.kuleuven.hotelrestservice.enums.ESexType;
import be.kuleuven.hotelrestservice.enums.EVisitorType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class VisitorResponseDto {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private ESexType sexType;
    private EVisitorType type;
}

