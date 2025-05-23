package be.kuleuven.hotelrestservice.controllers;

import be.kuleuven.hotelrestservice.domain.Meal;
import be.kuleuven.hotelrestservice.domain.MealsRepository;
import be.kuleuven.hotelrestservice.domain.OrderConfirmation;
import be.kuleuven.hotelrestservice.domain.OrderRepository;
import be.kuleuven.hotelrestservice.exceptions.MealNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class MealsRestRpcStyleController {

    private final MealsRepository mealsRepository;
    private final OrderRepository ordersRepository;

    @Autowired
    MealsRestRpcStyleController(MealsRepository mealsRepository, OrderRepository ordersRepository) {
        this.mealsRepository = mealsRepository;
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/restrpc/meals/{id}")
    ResponseEntity<Meal> getMealById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(mealsRepository.findMeal(id));
    }

    @GetMapping("/restrpc/meals")
    ResponseEntity<Collection<Meal>> getMeals() {
        return ResponseEntity.status(HttpStatus.OK).body(mealsRepository.getAllMeal());
    }

    @GetMapping("/restrpc/meals/cheapest")
    ResponseEntity<Meal> getCheapestMeal() {
        return ResponseEntity.status(HttpStatus.OK).body(mealsRepository.findCheapestMeal());
    }
    @GetMapping("/restrpc/meals/largest")
    ResponseEntity<Meal> getLargestMeal() {
        return ResponseEntity.status(HttpStatus.OK).body(mealsRepository.findLargestMeal());
    }
    @PostMapping("/restrpc/meals")
    ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mealsRepository.createMeal(meal));
    }
    @PutMapping("/restrpc/meals/{id}")
    ResponseEntity<Meal> updateMeal(@PathVariable String id,
                    @RequestBody Meal mealRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(mealsRepository.updateMeal(id, mealRequest));
    }

    @DeleteMapping("/restrpc/meals/{id}")
    ResponseEntity<Void> deleteMealById(@PathVariable String id) {
         mealsRepository.deleteMeal(id).orElseThrow(() -> new MealNotFoundException(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping("/restrpc/orders")
    ResponseEntity<OrderConfirmation> addOrder(@RequestBody OrderConfirmation orderConfirmation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordersRepository.addOrder(orderConfirmation));
    }

    @GetMapping("/restrpc/orders")
    ResponseEntity<Collection<OrderConfirmation>> getOrders() {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordersRepository.getAllOrders());
    }
}
