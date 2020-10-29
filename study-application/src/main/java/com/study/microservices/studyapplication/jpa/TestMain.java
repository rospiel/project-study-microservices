package com.study.microservices.studyapplication.jpa;

import com.study.microservices.studyapplication.StudyApplication;
import com.study.microservices.studyapplication.domain.model.*;
import com.study.microservices.studyapplication.infrastructure.repository.*;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;

public class TestMain {



    public static void main2(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(StudyApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        InsertKitchen insertKitchen = applicationContext.getBean(InsertKitchen.class);
        List<Kitchen> kitchens = insertKitchen.listar();

        kitchens.stream().forEach(k -> {
            System.out.println(k.getName());
        });

    }

    public static void main4(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(StudyApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        InsertKitchen insertKitchen = applicationContext.getBean(InsertKitchen.class);
        Kitchen kitchen = insertKitchen.find(1L);

        System.out.println(kitchen.getName());


    }

    public static void main5(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(StudyApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        InsertKitchen insertKitchen = applicationContext.getBean(InsertKitchen.class);
        Kitchen kitchen = insertKitchen.find(1L);
        System.out.println(kitchen.getName());
        kitchen.setName("Mexican");
        insertKitchen.add(kitchen);

        System.out.println(kitchen.getName());


    }

    public static void main19(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(StudyApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        InsertKitchen insertKitchen = applicationContext.getBean(InsertKitchen.class);
        Kitchen kitchen = insertKitchen.find(1L);
        System.out.println(kitchen.getName());
        insertKitchen.remove(kitchen);


        List<Kitchen> kitchens = insertKitchen.listar();

        kitchens.stream().forEach(k -> {
            System.out.println(k.getName());
        });


    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(StudyApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CityRepositoryImpl insertRestaurant = applicationContext.getBean(CityRepositoryImpl.class);
/*
        Restaurant restaurant = new Restaurant();
        restaurant.setFreightRate(TEN);
        restaurant.setName("Paulista");
        insertRestaurant.save(restaurant);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setFreightRate(ONE);
        restaurant2.setName("Osasco");
        insertRestaurant.save(restaurant2);*/

/*
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription("Cartão de crédito");
        insertRestaurant.save(paymentMethod); */

/*
        Permission permission = new Permission();
        permission.setName("Adm");
        permission.setDescription("Administrativo");
        insertRestaurant.save(permission);*/
/*
        State state = new State();
        state.setName("Minas Gerais");
        insertRestaurant.save(state);*/

        City city = new City();
        city.setName("Osasco");
        State state = new State();
        state.setId(2L);
        city.setState(state);
        insertRestaurant.save(city);

        List<City> restaurants = insertRestaurant.findAll();

        restaurants.stream().forEach(k -> {
            System.out.println(k.getState().getName());
        });

    }


}
