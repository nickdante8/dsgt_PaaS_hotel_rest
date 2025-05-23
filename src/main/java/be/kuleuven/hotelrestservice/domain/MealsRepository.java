package be.kuleuven.hotelrestservice.domain;

import be.kuleuven.hotelrestservice.exceptions.MealNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class MealsRepository {
    // map: id -> meal
    private static final Map<String, Meal> meals = new HashMap<>();

    @PostConstruct
    public void initData() {

        Meal a = new Meal();
        a.setId("5268203c-de76-4921-a3e3-439db69c462a");
        a.setName("Steak");
        a.setDescription("Steak with fries");
        a.setMealType(MealType.MEAT);
        a.setKcal(1100);
        a.setPrice((10.00));
        a.setCount(10);

        meals.put(a.getId(), a);

        Meal b = new Meal();
        b.setId("4237681a-441f-47fc-a747-8e0169bacea1");
        b.setName("Portobello");
        b.setDescription("Portobello Mushroom Burger");
        b.setMealType(MealType.VEGAN);
        b.setKcal(637);
        b.setPrice((7.00));
        b.setCount(10);

        meals.put(b.getId(), b);

        Meal c = new Meal();
        c.setId("cfd1601f-29a0-485d-8d21-7607ec0340c8");
        c.setName("Fish and Chips");
        c.setDescription("Fried fish with chips");
        c.setMealType(MealType.FISH);
        c.setKcal(950);
        c.setPrice(5.00);
        c.setCount(10);

        meals.put(c.getId(), c);
    }

    public Meal findMeal(String id) {
        Assert.notNull(id, "The meal id must not be null");
        Meal meal = meals.get(id);
        if (meal == null) {
            throw new MealNotFoundException(id);
        }
        return meal;
    }

    public Optional<Meal> deleteMeal(String id) {
        Assert.notNull(id, "The meal id must not be null");
        Meal meal = meals.remove(id);
        return Optional.ofNullable(meal);
    }

    public Meal findCheapestMeal() {

        return meals.values().stream()
                .min(Comparator.comparingDouble(Meal::getPrice)).get();
    }

    public Meal findLargestMeal() {

        return meals.values().stream()
                .max(Comparator.comparingDouble(Meal::getKcal)).get();
    }

    public Meal createMeal(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(UUID.randomUUID().toString());
        }
        meals.put(meal.getId(), meal);
        return meals.get(meal.id);
    }
    public Meal updateMeal(String id, Meal mealRequest) {
        Assert.notNull(id, "The meal id must not be null");
        Meal meal = meals.get(id);
        if (meal == null) {
            throw new MealNotFoundException(id);
        }
        mealRequest.setId(id);
        meals.put(meal.getId(), mealRequest);
        return meals.get(meal.id);
    }

    public Collection<Meal> getAllMeal() {
        return meals.values();
    }
}
