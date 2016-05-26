package com.gmail.liliyayalovchenko;

import com.gmail.liliyayalovchenko.controllers.*;
import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.domains.Ingredient;
import com.gmail.liliyayalovchenko.domains.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private IngredientController ingredientController;
    private WarehouseController warehouseController;
    private DishController dishController;
    private MenuController menuController;
    private OrderController orderController;
    private ReadyMealController readyMealController;


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        Main main = applicationContext.getBean(Main.class);
        main.start();
    }

    private void start() {
        startAplication();
        Scanner sc = new Scanner(System.in);
        String selection = null;

        while (true) {

            selection = sc.next();

            if (selection.equals("d")) {
                System.out.println("Dish page. You have following options:");
                System.out.println("Add new dish - enter d01\nRemove dish - enter d02\n" +
                        "Get dish by name - enter d03\nGet all dishes - enter d04");
                System.out.println("To exit database - enter q");
                System.out.println("To start menu - enter 'start'");
                selection = sc.next();
                if (workWithDishTable(selection)) break;

            } else if (selection.equals("e")) {
                System.out.println("Employee page. You have following options:");
                System.out.println("Add new employee - enter e01\nRemove employee - enter e02\n" +
                        "Get all employees - enter e03\nFind employee by name - enter e04");
                System.out.println("To exit database - enter q");
                System.out.println("To start menu - enter 'start'");
                selection = sc.next();
                if (workWithEmployeeTable(selection)) break;

            } else if (selection.equals("m")) {
                System.out.println("Menu page. You have following options:");
                System.out.println("Add new menu - enter m01\nRemove menu - enter m02\n" +
                        "Get menu by name - enter m03\nTo see all menus - enter m04\n" +
                        "To remove dish from menu - enter m05\nTo add dish to menu - enter m06");
                System.out.println("To exit database - enter q");
                System.out.println("To start menu - enter 'start'");
                selection = sc.next();
                if (workWithMenuTable(selection)) break;

            } else if (selection.equals("o")) {
                System.out.println("Orders page. You have following options:");
                System.out.println("Create order - enter o01\nAdd dish to open order - enter o02\n" +
                        "Delete order - enter o03\nTo change order status - enter o04\n" +
                        "To get all open or closed orders - enter o05");
                System.out.println("To exit database - enter q");
                System.out.println("To start menu - enter 'start'");
                selection = sc.next();
                if (workWithOrderTable(selection)) break;

            } else if (selection.equals("rm")) {
                System.out.println("Ready meals page. You have following options:");
                System.out.println("Get all ready meals - enter rm01\nAdd new ready meal - enter rm02");
                System.out.println("To exit database - enter q");
                System.out.println("To start menu - enter 'start'");
                selection = sc.next();
                if (workWithReadyMealTable(selection)) break;

            } else if (selection.equals("w")) {
                System.out.println("Warehouse page. You have following options:");
                System.out.println("Add ingredient to warehouse - enter w01\nRemove ingredient - enter w02\n" +
                        "Change amount of ingredient - enter w03\nFind ingredient by name - enter w04\n " +
                        "To see ingredients in lack - enter w05");
                System.out.println("To exit database - enter q");
                System.out.println("To start menu - enter 'start'");
                selection = sc.next();
                if (workWithWarehouseTable(selection)) break;

            } else {
                System.out.println("Wrong input!!! Try again");
            }
        }


       /* List<Ingredient> ingredientList = ingredientController.getAllIngredients();
        ingredientList.forEach(System.out::println);*/
       // List<Warehouse> warehouseList = warehouseController.getAllWarehouseIngred();
       // warehouseList.forEach(System.out::println);

     //   Ingredient ingredient = ingredientController.getIngredientByName("rucolla");
     //   System.out.println(ingredient.getId() + " rucolla");
      //  warehouseController.changeAmount(ingredient, 20, true);
      //  Warehouse warehouseIngred = warehouseController.findByName("chiken");
      //  System.out.println(warehouseIngred);
      //  warehouseController.removeIngredient("chiken");
     // warehouseController.removeIngredient("bread");
      //  Ingredient chiken = ingredientController.getIngredientByName("chiken");
        Ingredient bread = ingredientController.getIngredientByName("bread");
        System.out.println(bread);
      //  warehouseController.addIngredient(chiken, 100);
       // warehouseController.addIngredient(bread, 5);
        List<Warehouse> warehouseList = warehouseController.getLuckIngredients();
        for (Warehouse warehouse : warehouseList) System.out.println(warehouse);


    }

    private boolean workWithWarehouseTable(String selection) {
        if ("w01".equals(selection)) {

        } else if ("w02".equals(selection)) {

        } else if ("w03".equals(selection)) {

        } else if ("w04".equals(selection)) {

        } else if ("w05".equals(selection)) {

        } else if ("start".equals(selection)) {
            start();
        } else {
            System.out.println("Good Bay!");
            return true;
        }
        return false;
    }

    private boolean workWithReadyMealTable(String selection) {
        if ("rm01".equals(selection)) {

        } else if ("rm02".equals(selection)) {

        } else if ("start".equals(selection)) {
            start();
        } else {
            System.out.println("Good Bay!");
            return true;
        }
        return false;
    }

    private boolean workWithOrderTable(String selection) {
        if ("o01".equals(selection)) {

        } else if ("o02".equals(selection)) {

        } else if ("o03".equals(selection)) {

        } else if ("o04".equals(selection)) {

        } else if ("o05".equals(selection)) {

        } else if ("start".equals(selection)) {
            start();
        } else {
            System.out.println("Good Bay!");
            return true;
        }
        return false;
    }

    private boolean workWithMenuTable(String selection) {
        if ("m01".equals(selection)) {

        } else if ("m02".equals(selection)) {

        } else if ("m03".equals(selection)) {

        } else if ("m04".equals(selection)) {

        } else if ("m05".equals(selection)) {

        } else if ("m06".equals(selection)) {

        } else if ("start".equals(selection)) {
            start();
        } else {
            System.out.println("Good Bay!");
            return true;
        }
        return false;
    }

    private boolean workWithEmployeeTable(String selection) {
        if ("e01".equals(selection)) {

        } else if ("e02".equals(selection)) {

        } else if ("e03".equals(selection)) {

        } else if ("e04".equals(selection)) {

        } else if ("start".equals(selection)) {
            start();
        } else {
            System.out.println("Good Bay!");
            return true;
        }
        return false;
    }

    private boolean workWithDishTable(String selection) {
        try (Scanner scanner = new Scanner(System.in)) {
            if ("d01".equals(selection)) {              // add new dish
                Dish dish = new Dish();
                System.out.println("Enter dish name");
                String name = scanner.next();
                dish.setName(name);
                System.out.println("Enter category");
                dish.setCategory(scanner.next());
                System.out.println("Enter price");
                dish.setPrice(scanner.nextDouble());
                System.out.println("Enter weight");
                dish.setPrice(scanner.nextInt());
                System.out.println("Enter ingredients. Then enter - f");
                List<Ingredient> ingredientList = new ArrayList<>();
                String ingre = null;
                while (!"f".equals(ingre)) {
                    ingre = scanner.next();
                    ingredientList.add(ingredientController.getIngredientByName(ingre));
                }
                dish.setIngredients(ingredientList);
                dishController.addDish(dish);

            } else if ("d02".equals(selection)) {

            } else if ("d03".equals(selection)) {

            } else if ("d04".equals(selection)) {

            } else if ("start".equals(selection)) {
                start();
            } else {
                System.out.println("Good Bay!");
                scanner.close();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e);
            return false;
        }
        return false;
    }

    private void startAplication() {
        System.out.println("Hi! You entered to restaurant database");
        System.out.println("Select you next step");
        System.out.println("d - work with dish information\ne - work with info about employee");
        System.out.println("m - work with menu information\no - work with info about orders");
        System.out.println("rm - work with ready meals information\nw - work with info about ingredients in warehouse");
    }

    private boolean isValid(String s) {
       return (s.equals("d") || s.equals("e") || s.equals("m") ||
               s.equals("o") || s.equals("rm") || s.equals("w"));
    }

    public void setIngredientController(IngredientController ingredientController) {
        this.ingredientController = ingredientController;
    }

    public void setWarehouseController(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
    }

    public void setDishController(DishController dishController) {
        this.dishController = dishController;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    public void setReadyMealController(ReadyMealController readyMealController) {
        this.readyMealController = readyMealController;
    }
}
