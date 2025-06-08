package be.kuleuven.hotelrestservice.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum ErrorEnum {
    CREATE_PWD_FAILURE(
            "Create-Pwd-Failure",
            "Creation password fail",
            "Creation of password failed",
            500),
    FLIGHT_NOT_FOUND(
            "Hotel-Not-Found",
            "Hotel not found",
            "Hotel with this ID not found: %s",
            400);
    String type;
    String title;
    String message;
    Integer code;

    public static Optional<ErrorEnum> getErrorByType(final String type) {
        return Arrays.stream(ErrorEnum.values())
                .filter(err -> err.type.equals(type))
                .findFirst();
    }
}
