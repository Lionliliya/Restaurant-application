package com.gmail.liliyayalovchenko;

import com.gmail.liliyayalovchenko.controllers.IngredientController;
import com.gmail.liliyayalovchenko.controllers.WarehouseController;
import com.gmail.liliyayalovchenko.domains.Ingredient;
import com.gmail.liliyayalovchenko.domains.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private IngredientController ingredientController;
    private WarehouseController warehouseController;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        Main main = applicationContext.getBean(Main.class);
        main.start();
    }

    private void start() {
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

    public void setIngredientController(IngredientController ingredientController) {
        this.ingredientController = ingredientController;
    }

    public void setWarehouseController(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
    }
}
