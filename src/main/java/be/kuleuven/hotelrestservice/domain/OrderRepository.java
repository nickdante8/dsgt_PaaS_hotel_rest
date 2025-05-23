package be.kuleuven.hotelrestservice.domain;

import be.kuleuven.hotelrestservice.exceptions.NotEnoughtMealsException;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class OrderRepository {
    // map: id -> order
    private static final Map<String, OrderConfirmation> orders = new HashMap<>();
    private final MealsRepository mealsRepository;

    public OrderRepository(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    public OrderConfirmation addOrder(OrderConfirmation orderConfirmation) {
        if (orderConfirmation.getId() == null) {
            orderConfirmation.setId(UUID.randomUUID().toString());
        }
        orders.put(orderConfirmation.getId(), orderConfirmation);
        return getOrderWithNames(orderConfirmation.id);
    }

    private OrderConfirmation getOrderWithNames(String id) {
        OrderConfirmation orderConfirmation = orders.get(id);
        orderConfirmation.getMeals().forEach(mealOrder -> {
            Meal meal = mealsRepository.findMeal(mealOrder.getMealId());
            if (meal != null) {
                mealOrder.setName(meal.getName());
            }
            if (meal.getCount() >= mealOrder.getQuanity()) {
                meal.setCount(meal.getCount() - mealOrder.getQuanity());
            } else {
                orders.remove(id);
                throw new NotEnoughtMealsException(meal.getName(), meal.getCount());
            }
            mealsRepository.updateMeal(meal.getId(), meal);
        });
        return orderConfirmation;
    }

    public Collection<OrderConfirmation> getAllOrders() {
        return orders.values();
    }
}
