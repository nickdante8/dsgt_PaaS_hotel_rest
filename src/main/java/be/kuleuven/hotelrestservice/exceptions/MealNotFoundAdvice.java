package be.kuleuven.hotelrestservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class RestControllerAdvice {

    @ResponseBody
    @ExceptionHandler(MealNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String mealNotFoundHandler(MealNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotEnoughtMealsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String mealNotFoundHandler(NotEnoughtMealsException ex) {
        return ex.getMessage();
    }


}
