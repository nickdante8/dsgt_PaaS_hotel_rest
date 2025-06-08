package be.kuleuven.hotelrestservice.exceptions;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"stackTrace", "suppressedExceptions",
        "suppressed", "localizedMessage",
        "cause", "detailMessage"})
public class ErrorResponseMessage extends RuntimeException {
    String type;
    String title;
    String message;
    Integer code;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorResponseMessage that = (ErrorResponseMessage) o;
        return Objects.equals(type, that.type)
                && Objects.equals(title, that.title)
                && Objects.equals(message, that.message)
                && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, title, message, code);
    }
}