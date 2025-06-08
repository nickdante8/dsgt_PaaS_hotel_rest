package be.kuleuven.hotelrestservice.exceptions;

import be.kuleuven.hotelrestservice.consts.ErrorEnum;

public class GenericException extends ErrorResponseMessage {
    public GenericException(final ErrorEnum error) {
        super(error.getType(),
                error.getTitle(),
                error.getMessage(),
                error.getCode());
    }
    public GenericException(final ErrorEnum error, String field) {
        super(error.getType(),
                error.getTitle(),
                String.format(error.getMessage(), field),
                error.getCode());
    }

    public GenericException(final String type,
                            final String title,
                            final String message,
                            final Integer code) {
        super(type,
                title,
                message,
                code);
    }

}