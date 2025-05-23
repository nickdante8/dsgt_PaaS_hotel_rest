package be.kuleuven.hotelrestservice.controllers;

import be.kuleuven.hotelrestservice.domain.Meal;
import be.kuleuven.hotelrestservice.domain.MealsRepository;
import be.kuleuven.hotelrestservice.domain.OrderConfirmation;
import be.kuleuven.hotelrestservice.domain.OrderRepository;
import be.kuleuven.hotelrestservice.exceptions.MealNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class MealsRestController {

    private final MealsRepository mealsRepository;
    private final OrderRepository orderRepository;

    @Autowired
    MealsRestController(MealsRepository mealsRepository, OrderRepository orderRepository) {
        this.mealsRepository = mealsRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/rest/meals/{id}")
    ResponseEntity<EntityModel<Meal>> getMealById(@PathVariable String id) {
        Meal meal = mealsRepository.findMeal(id);

        return ResponseEntity.status(HttpStatus.OK).body(mealToEntityModel(id, meal));
    }

    @GetMapping("/rest/meals")
    CollectionModel<EntityModel<Meal>> getMeals() {
        Collection<Meal> meals = mealsRepository.getAllMeal();

        List<EntityModel<Meal>> mealEntityModels = new ArrayList<>();
        for (Meal m : meals) {
            EntityModel<Meal> em = mealToEntityModel(m.getId(), m);
            mealEntityModels.add(em);
        }
        return CollectionModel.of(mealEntityModels,
                linkTo(methodOn(MealsRestController.class).getMeals()).withSelfRel());
    }

    @GetMapping("/rest/meals/cheapest")
    ResponseEntity<EntityModel<Meal>> getCheapestMeal() {
        Meal meal = mealsRepository.findCheapestMeal();
        return ResponseEntity.status(HttpStatus.OK).body(mealToEntityModel(meal.getId(), meal));
    }
    @GetMapping("/rest/meals/largest")
    ResponseEntity<EntityModel<Meal>> getLargestMeal() {
        Meal meal = mealsRepository.findLargestMeal();
        return ResponseEntity.status(HttpStatus.OK).body(mealToEntityModel(meal.getId(), meal));
    }
    @PostMapping("/rest/meals")
    ResponseEntity<EntityModel<Meal>> createMeal(@RequestBody Meal mealRequest) {
        Meal meal = mealsRepository.createMeal(mealRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(mealToEntityModel(meal.getId(), meal));
    }
    @PutMapping("/rest/meals/{id}")
    ResponseEntity<EntityModel<Meal>> updateMeal(@PathVariable String id,
                                    @RequestBody Meal mealRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(mealToEntityModel(id, mealsRepository.updateMeal(id, mealRequest)));
    }

    @DeleteMapping("/rest/meals/{id}")
    ResponseEntity<EntityModel<Void>> deleteMealById(@PathVariable String id) {
        mealsRepository.deleteMeal(id).orElseThrow(() -> new MealNotFoundException(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping("/rest/orders")
    ResponseEntity<OrderConfirmation> addOrder(@RequestBody OrderConfirmation orderConfirmation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.addOrder(orderConfirmation));
    }
    private EntityModel<Meal> mealToEntityModel(String id, Meal meal) {
        return EntityModel.of(meal,
                linkTo(methodOn(MealsRestController.class).getMealById(id)).withSelfRel(),
                linkTo(methodOn(MealsRestController.class).getMeals()).withRel("rest/meals"),
                linkTo(methodOn(MealsRestController.class).getCheapestMeal()).withSelfRel(),
                linkTo(methodOn(MealsRestController.class).getLargestMeal()).withRel("rest/meals/largest"),
                linkTo(methodOn(MealsRestController.class).createMeal(meal)).withRel("rest/meals"),
                linkTo(methodOn(MealsRestController.class).deleteMealById(id)).withRel("rest/meals/{id}"),
                linkTo(methodOn(MealsRestController.class).updateMeal(id, meal)).withRel("rest/meals/{id}"));
    }
}
