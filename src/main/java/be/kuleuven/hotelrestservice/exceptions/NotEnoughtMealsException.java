package be.kuleuven.hotelrestservice.exceptions;

public class NotEnoughtMealsException extends RuntimeException {

    public NotEnoughtMealsException(String mealName, Integer amount) {
        super(String.format("There are not enough meals to order. Available amount of %s is %s ", mealName, amount ));
    }
}
