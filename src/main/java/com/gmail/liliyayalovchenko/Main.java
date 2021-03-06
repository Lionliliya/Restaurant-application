package com.gmail.liliyayalovchenko;

import com.gmail.liliyayalovchenko.controllers.*;
import com.gmail.liliyayalovchenko.domains.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private IngredientController ingredientController;
    private WarehouseController warehouseController;
    private DishController dishController;
    private MenuController menuController;
    private OrderController orderController;
    private ReadyMealController readyMealController;
    private EmployeeController employeeController;
    private boolean stopApp;
    private int orderNumber;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        Main main = applicationContext.getBean(Main.class);
        main.start();
    }

    private void start() {
        orderNumber = 1005;
        startApplication();
        Scanner sc = new Scanner(System.in);
        String selection = null;
        stopApp = false;
        while (!"q".equals(selection) && !stopApp) {

            selection = sc.next();

            if (selection.equals("d")) {
               goToDishPage(sc, selection);

            } else if (selection.equals("e")) {
                goToEmployeePage(sc, selection);


            } else if (selection.equals("m")) {
                goToMenuPage(sc, selection);

            } else if (selection.equals("o")) {
                goToOrdersPage(sc, selection);

            } else if (selection.equals("rm")) {
                goToReadyMealPage(sc, selection);

            } else if (selection.equals("w")) {
                goToWarehousePage(sc, selection);

            } else if ("q".equals(selection)) {
                System.out.println("Good Bay!");
                stopApp = true;
                LOGGER.info("User left the application");
                break;
            } else {
                System.out.println("Wrong input!!! Try again");
            }
            System.out.println("Do you want continue? - enter 'y'");
            String next = sc.next();
            if (next.equals("y")) {
                selection = "continue";
                stopApp = false;
                startApplication();
            } else {
                stopApp = true;
                LOGGER.info("User left the application");
                break;
            }
        }
    }

    private void goToDishPage(Scanner sc, String selection) {
        System.out.println("Dish page. You have following options:");
        System.out.println("Add new dish - enter d01\nRemove dish - enter d02\n" +
                "Get dish by name - enter d03\nGet all dishes - enter d04");
        System.out.println("To exit database - enter q");
        System.out.println("To start menu - enter 'start'");

        while (!"q".equals(selection) && !stopApp) {
            selection = sc.next();
            if (selection.equals("d01")) {
                addNewDish(sc);

            } else if (selection.equals("d02")) {
                deleteDish(sc);

            } else if (selection.equals("d03")) {
                getDishByName(sc);

            } else if (selection.equals("d04")) {
                getAllDishes();

            } else if (selection.equals("start")) {
                break;

            } else if (selection.equals("q")){
                stopApp = true;
                break;
            } else {
                System.out.println("Wrong input! Try again!");
            }
            System.out.println("Dish page. You have following options:");
            System.out.println("Add new dish - enter d01\nRemove dish - enter d02\n" +
                    "Get dish by name - enter d03\nGet all dishes - enter d04");
            System.out.println("To exit database - enter q");
            System.out.println("To start menu - enter 'start'");
        }

    }
    /**
     * Starts private methods for dish page **/
    private void getAllDishes() {
        try {
            List<Dish> dishList = dishController.getAllDishes();
            dishList.forEach(System.out::println);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot get all dishes " + ex);
        }
    }

    private void getDishByName(Scanner sc) {
        showAllDishNames();
        System.out.println("Enter dish name");
        String name = sc.next();
        Dish dish;
        try{
            dish = dishController.getDishByName(name);
            System.out.println(dish);
        } catch (RuntimeException e) {
            LOGGER.error("Cant get dish by this name");
            System.out.println("dish with such name does not exist");
        }
    }

    private void deleteDish(Scanner sc) {
        showAllDishNames();
        System.out.println("Enter dish name to delete");
        String name = sc.next();
        Dish dish;
        try{
            dish = dishController.getDishByName(name);
            dishController.removeDish(dish);
        } catch (RuntimeException e) {
            LOGGER.error("Cannot get dish bi this name.");
            System.out.println("dish with such name does not exist");
        }
    }

    private void addNewDish(Scanner sc) {
        Dish dish = new Dish();
        System.out.println("Enter dish name");
        String name = sc.next();
        dish.setName(name);
        System.out.println("Enter category");
        String category = sc.next();
        dish.setCategory(category);
        try {
            System.out.println("Enter price");
            dish.setPrice(sc.nextDouble());
            System.out.println("Enter weight");
            dish.setWeight(sc.nextInt());
        } catch (InputMismatchException ex) {
            LOGGER.error("Error wile parsing " + ex);
            System.out.println("Wrong input");
        }
        System.out.println("Enter ingredients. Then enter - f");
        List<Ingredient> ingredientList = new ArrayList<>();
        try {
            while (true) {
                String ingre = sc.next();
                if (ingre.equals("f")) break;
                Ingredient ingredientByName = ingredientController.getIngredientByName(ingre);
                ingredientList.add(ingredientByName);
            }
            dish.setIngredients(ingredientList);
            dishController.addDish(dish);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot add dish " + ex);
        }
    }

    private void showAllDishNames() {
        for (Dish dish : dishController.getAllDishes()) {
            System.out.println(dish.getName());
        }

    }
    /**
     * Stop private methods for dish page**/

    private void goToEmployeePage(Scanner sc, String selection) {
        System.out.println("Employee page. You have following options:");
        System.out.println("Add new employee - enter e01\nRemove employee - enter e02\n" +
                "Get all employees - enter e03\nFind employee by name - enter e04");
        System.out.println("To exit database - enter q");
        System.out.println("To start menu - enter 'start'");

        while (!"q".equals(selection)) {
            selection = sc.next();
            if (selection.equals("e01")) {
                addNewEmployee(sc);

            } else if (selection.equals("e02")) {
                removeEmployee(sc);

            } else if (selection.equals("e03")) {
               getAllEmployees();

            } else if (selection.equals("e04")) {
                findEmployeeByName(sc);

            } else if (selection.equals("start")) {
                break;

            } else if (selection.equals("q")){
                stopApp = true;
                break;
            } else {
                System.out.println("Wrong input!Try again!");
            }
            System.out.println("Employee page. You have following options:");
            System.out.println("Add new employee - enter e01\nRemove employee - enter e02\n" +
                    "Get all employees - enter e03\nFind employee by name - enter e04");
            System.out.println("To exit database - enter q");
            System.out.println("To start menu - enter 'start'");
        }
    }

    /**
     * Start private methods for employee page**/
    private void findEmployeeByName(Scanner sc) {
        showAllEmplNames();
        System.out.println("Enter second name of employee");
        String secondName = sc.next();
        System.out.println("Enter first name of employee");
        String firstName = sc.next();

        Employee employee;
        try {
            employee = employeeController.findEmployeeByName(firstName, secondName);
            System.out.println(employee);
        } catch (RuntimeException ex) {
            LOGGER.error("Exception " + ex);
            System.out.println("Can't find employee by this name");
        }
    }

    private void showAllEmplNames() {
        for (Employee employee : employeeController.getAllEmployees()) {
            System.out.println(employee.getSecondName() + " " + employee.getFirstName());
        }
    }

    private void getAllEmployees() {
        try {
            List<Employee> employeeList = employeeController.getAllEmployees();
            employeeList.forEach(System.out::println);
        } catch (RuntimeException e) {
            LOGGER.error("Cannot get all employee " +e);
        }
    }

    private void removeEmployee(Scanner sc) {
        showAllEmplNames();
        System.out.println("Enter second name of employee to delete");
        String secondName = sc.next();
        System.out.println("Enter first name of employee");
        String firstName = sc.next();
        try {
            employeeController.removeEmployee(firstName, secondName);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot remove employee by this name");
        }
    }

    private void addNewEmployee(Scanner sc) {
        Employee employee = new Employee();
        System.out.println("Enter Second name of new employee");
        employee.setSecondName(sc.next());
        System.out.println("Enter First name of new employee");
        employee.setFirstName(sc.next());
        System.out.println("Enter date of employment in format yyyy-mm-dd. For example, 2008-10-3");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date date;
        try {
            date = format.parse(sc.next());
            employee.setEmplDate(date);
            System.out.println("Enter phone");
            employee.setPhone(sc.next());
            System.out.println("Enter position");
            employee.setPosition(sc.next());
            System.out.println("Enter salary");
            employee.setSalary(sc.nextInt());
            employeeController.addEmployee(employee);
        } catch (ParseException e) {
            LOGGER.error("Exception while parsing date" + e);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot add employee " + ex);
        }
    }
    /**
     * Stop private methods for employee page**/

    private void goToMenuPage(Scanner sc, String selection) {
        System.out.println("Menu page. You have following options:");
        System.out.println("Add new menu - enter m01\nRemove menu - enter m02\n" +
                "Get menu by name - enter m03\nTo see all menus - enter m04\n" +
                "To remove dish from menu - enter m05\nTo add dish to menu - enter m06");
        System.out.println("To exit database - enter q");
        System.out.println("To start menu - enter 'start'");

        while (!"q".equals(selection)) {
            selection = sc.next();
            if ("m01".equals(selection)) {
                addNewMenue(sc);

            } else if ("m02".equals(selection)) {
                removeMenu(sc);

            } else if ("m03".equals(selection)) {
                getMenuByName(sc);

            } else if ("m04".equals(selection)) {
                showAllMenues();

            } else if ("m05".equals(selection)) {
                removeDishFromMenu(sc);

            } else if ("m06".equals(selection)) {
                addDishToMenu(sc);

            } else if ("start".equals(selection)) {
                break;
            } else if(selection.equals("q"))  {
                stopApp = true;
                break;
            } else {
                System.out.println("Wrong input!Try again!");
            }
            System.out.println("Menu page. You have following options:");
            System.out.println("Add new menu - enter m01\nRemove menu - enter m02\n" +
                    "Get menu by name - enter m03\nTo see all menus - enter m04\n" +
                    "To remove dish from menu - enter m05\nTo add dish to menu - enter m06");
            System.out.println("To exit database - enter q");
            System.out.println("To start menu - enter 'start'");
        }

    }

    /**
     * Start private methods for menu page**/
    private void addNewMenue(Scanner sc) {
        System.out.println("Enter name of new Menu");
        String nameMenu = sc.next();
        showAllDishNames();
        System.out.println("Enter dish name to add to menu. To finish enter - 'f'");
        List<Dish> dishList = new ArrayList<>();
        String dishName;
        while (true) {
            dishName = sc.next();
            if (dishName.equals("f")) break;
            try {
                Dish dish = dishController.getDishByName(dishName);
                dishList.add(dish);
            } catch (RuntimeException e) {
                LOGGER.error("Exception " + e);
            }
        }
        menuController.addNewMenu(nameMenu, dishList);
    }

    private void removeMenu(Scanner sc) {
        menuController.showAllMenus();
        System.out.println("Enter name of menu to delete");
        try {
            Menu menu = menuController.getMenuByName(sc.next());
            menuController.removeMenu(menu);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot get menu by this name " +ex);
        }
    }

    private void getMenuByName(Scanner sc) {
        System.out.println("Enter name of menu to see");
        try {
            Menu menu = menuController.getMenuByName(sc.next());
            System.out.println(menu);
        } catch (RuntimeException ex) {
            LOGGER.error("cannot get menu by this name " + ex);
        }
    }

    private void showAllMenues() {
        try {
            menuController.showAllMenus();
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot show list of menus " + ex);
        }
    }

    private void removeDishFromMenu(Scanner sc) {
        menuController.showAllMenus();
        System.out.println("Enter menu name you want to remove dish");
        try {
            Menu menu = menuController.getMenuByName(sc.next());
            showAllDishNames();
            System.out.println("Enter dish name to remove");
            Dish dish = dishController.getDishByName(sc.next());
            menuController.removeDishFromMenu(menu.getId(), dish);
        } catch (RuntimeException ex) {
            LOGGER.error("Error while removing dish from menu Main() method " +ex);
        }
    }

    private void addDishToMenu(Scanner sc) {
        menuController.showAllMenus();
        System.out.println("Enter menu name you want to add dish");
        try {
        Menu menu = menuController.getMenuByName(sc.next());
        showAllDishNames();
        Dish dish;

            dish = dishController.getDishByName(sc.next());
            menuController.addDishToMenu(menu.getId(), dish);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot gat dish or menu " + ex);
        }
    }
    /**
     * Stop private methods for menu page**/

     private void goToOrdersPage(Scanner sc, String selection) {
        System.out.println("Orders page. You have following options:");
        System.out.println("Create order - enter o01\nAdd dish to open order - enter o02\n" +
                "Delete order - enter o03\nTo change order status - enter o04\n" +
                "To get all open or closed orders - enter o05");
        System.out.println("To exit database - enter q");
        System.out.println("To start menu - enter 'start'");
        while (!"q".equals(selection)) {
            selection = sc.next();
            if ("o01".equals(selection)) {
                createOrder(sc);

            } else if ("o02".equals(selection)) {
                addDishToOpenOrder(sc);

            } else if ("o03".equals(selection)) {
               deleteOrder(sc);

            } else if ("o04".equals(selection)) {
               changeOrderStatus(sc);

            } else if ("o05".equals(selection)) {
               getAllOpenOrClosedOrders(sc);

            } else if ("start".equals(selection)) {
                break;

            } else if ("q".equals(selection)) {
                stopApp = true;
                break;

            } else {
                System.out.println("Wrong input!Try again!");
            }
            System.out.println("Orders page. You have following options:");
            System.out.println("Create order - enter o01\nAdd dish to open order - enter o02\n" +
                    "Delete order - enter o03\nTo change order status - enter o04\n" +
                    "To get all open or closed orders - enter o05");
            System.out.println("To exit database - enter q");
            System.out.println("To start menu - enter 'start'");
        }
    }
    /**
     * Start private methods for Order page**/
    private void addDishToOpenOrder(Scanner sc) {
        System.out.println("Enter dish name");
        showAllDishNames();
        Dish dish;
        try {
            dish = dishController.getDishByName(sc.next());
            System.out.println("Select number of order");

            for (Order order : orderController.getOpenOrClosedOrder("opened")) {
                System.out.println("Order number " + order.getOrderNumber() + " ");
            }

            orderController.addDishToOpenOrder(dish, sc.nextInt());
        } catch (RuntimeException ex) {
            LOGGER.error("Error wile add dish to open order." + ex);
        }
    }

    private void createOrder(Scanner sc) {
        Order order = new Order();
        order.setOrderNumber(orderNumber++);
        showAllEmplNames();
        System.out.println("Enter employee second name");
        String secondName = sc.next();
        System.out.println("Enter employee firstName");
        String firstName = sc.next();
        Employee employee;
        try {
            employee = employeeController.findEmployeeByName(firstName, secondName);
            order.setEmployeeId(employee.getId());
            System.out.println("Enter table number");
            order.setTableNumber(sc.nextInt());
            order.setOrderDate(new Date());
            order.setStatus("opened");
            showAllDishNames();
            System.out.println("Enter name of dish to add to order, to stop - enter twice 'f'");
            List<Dish> dishForOrder = new ArrayList<>();
            String next1;
            String next2;
            while (true) {
                next1 = sc.next();
                next2 = sc.next();
                if ("f".equals(next1)) break;
                String dishName= next1 + " " + next2;
                System.out.println("You entered " + dishName);
                dishForOrder.add(dishController.getDishByName(dishName));
            }
            order.setDishList(dishForOrder);
            orderController.createOrder(order);
        } catch (RuntimeException ex) {
            LOGGER.error("Exception " + ex);
            System.out.println("Cannot find employee or dish with such name");
        }
    }

    private void getAllOpenOrClosedOrders(Scanner sc) {
        System.out.println("Select order status to find, print: 'closed' or 'opened'");
        List<Order> orders;
        try {
            orders = orderController.getOpenOrClosedOrder(sc.next());
            orders.forEach(System.out::println);

        } catch (RuntimeException e) {
            LOGGER.error("No orders with such status!");
        }
    }

    private void changeOrderStatus(Scanner sc) {
        System.out.println("Enter number of order to change status to 'closed'");
        showOpenedOrdersNumb();
        try {
            orderController.changeOrderStatus(sc.nextInt());
        } catch (RuntimeException e) {
            LOGGER.error("Cannot find order by this order number");
        }
    }

    private void showOpenedOrdersNumb() {
        for (Order order : orderController.getOpenOrClosedOrder("opened")) {
            System.out.println("Order #" + order.getOrderNumber());
        }
    }

    private void deleteOrder(Scanner sc) {
        System.out.println("Enter number of order to delete");
        showOpenedOrdersNumb();
        try {
            orderController.deleteOrder(sc.nextInt());
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot find order by this order number " + ex);
        }
    }
    /**
     * End of private methods for Order page**/

    private void goToReadyMealPage(Scanner sc, String selection) {
        System.out.println("Ready meals page. You have following options:");
        System.out.println("Get all ready meals - enter rm01\nAdd new ready meal - enter rm02");
        System.out.println("To exit database - enter q");
        System.out.println("To start menu - enter 'start'");
        while (!"q".equals(selection)) {
            selection = sc.next();
            if ("rm01".equals(selection)) {
                getAllreadyMeals();

            } else if ("rm02".equals(selection)) {
                addNewReadyMeal(sc);

            } else if ("start".equals(selection)) {
                start();
            } else if ("q".equals(selection)) {
                stopApp = true;
                break;
            } else {
                System.out.println("Wrong input! try again!");
            }
            System.out.println("Ready meals page. You have following options:");
            System.out.println("Get all ready meals - enter rm01\nAdd new ready meal - enter rm02");
            System.out.println("To exit database - enter q");
            System.out.println("To start menu - enter 'start'");
        }
    }
    /**
     * Start private methods for ready meal page**/
    private void addNewReadyMeal(Scanner sc) {
        ReadyMeal readyMeal = new ReadyMeal();
        showAllDishNames();
        System.out.println("Enter dish name");
        int dishId;
        Employee employee = null;
        try {
            dishId = dishController.getDishByName(sc.next()).getId();
            readyMeal.setDishId(dishId);
            readyMeal.setDishNumber(dishId);
            showAllEmplNames();
            System.out.println("Enter employee second name");
            String secondName = sc.next();
            System.out.println("Enter employee first name");
            String firstName = sc.next();
            employee = employeeController.findEmployeeByName(firstName, secondName);
            readyMeal.setEmployeeId(employee.getId());
            List<Order> openOrders = orderController.getOpenOrClosedOrder("opened");

            for (Order openOrder : openOrders) {
                    System.out.println("order_id " + openOrder.getId() + " ");
            }

            System.out.println("Select order id");
            readyMeal.setOrderId(sc.nextInt());
            readyMeal.setMealDate(new Date());
            readyMealController.addMeal(readyMeal);

        } catch (RuntimeException ex) {
            LOGGER.error("Wrong input!!!Try again!");
        }
    }

    private void getAllreadyMeals() {
        try {
            List<ReadyMeal> readyMeals = readyMealController.getAllMeal();
            readyMeals.forEach(System.out::println);
        } catch (RuntimeException e) {
            LOGGER.error("Cannot get all ready meals " + e);
        }
    }
    /**Stop private methods for ready meal page**/

    private void goToWarehousePage(Scanner sc, String selection) {
        System.out.println("Warehouse page. You have following options:");
        System.out.println("Add ingredient to warehouse - enter w01\nRemove ingredient - enter w02\n" +
                "Change amount of ingredient - enter w03\nFind ingredient by name - enter w04\n " +
                "To see all ingredients - enter w05");
        System.out.println("To exit database - enter q");
        System.out.println("To start menu - enter 'start'");
        while (!"q".equals(selection)) {
            selection = sc.next();
            if (selection.equals("w01")) {
                addNewIngredient(sc);

            } else if (selection.equals("w02")) {
                removeIngredientByName(sc);

            } else if (selection.equals("w03")) {
                changeAmountOfIngredient(sc);

            } else if (selection.equals("w04")) {
                findIngredientByName(sc);

            } else if (selection.equals("w05")) {
                getAllIngredients();

            } else if (selection.equals("start")) {
                break;

            } else if ("q".equals(selection)) {
                stopApp = true;
                break;
            }
            System.out.println("Warehouse page. You have following options:");
            System.out.println("Add ingredient to warehouse - enter w01\nRemove ingredient - enter w02\n" +
                    "Change amount of ingredient - enter w03\nFind ingredient by name - enter w04\n " +
                    "To see ingredients in lack - enter w05");
            System.out.println("To exit database - enter q");
            System.out.println("To start menu - enter 'start'");

        }
    }

    /**
     * Start private methods for warehouse page**/
    private void getAllIngredients() {
        try {
            List<Warehouse> warehouseList = warehouseController.getAllWarehouseIngred();
            warehouseList.forEach(System.out::println);
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot get all ingredients " + ex);
        }
    }

    private void findIngredientByName(Scanner sc) {
        showAllIngredNames();
        System.out.println("Enter name of ingredient");
        try {
            Warehouse warehouse = warehouseController.findByName(sc.next());
            System.out.println(warehouse);
        } catch (RuntimeException e) {
            System.out.println("No Ingredient with such name on warehouse");
        }
    }

    private void showAllIngredNames() {
        for (Ingredient ingredient : ingredientController.getAllIngredients()) {
            System.out.println(ingredient.getName());
        }
    }

    private void changeAmountOfIngredient(Scanner sc) {
        showAllIngredNames();
        System.out.println("Enter name of ingredient to change it amount");
        Ingredient ingredient = null;
        try {
            ingredient = ingredientController.getIngredientByName(sc.next());
        } catch (RuntimeException ex) {
            LOGGER.error("Cannot get ingredient by name");
        }
        System.out.println("Enter amount");
        int amount = sc.nextInt();
        System.out.println("If you want to increase amount enter y. If to decrease - n");
        boolean increase = sc.next().equals("y");
        if (ingredient !=null ) {
            warehouseController.changeAmount(ingredient, amount, increase);
        } else {
            LOGGER.error("Ingredient was not selected in right way");
        }
    }

    private void removeIngredientByName(Scanner sc) {
        showAllIngredNames();
        System.out.println("Enter name of ingredient to remove");
        try {
            warehouseController.removeIngredient(sc.next());
        } catch (RuntimeException ex) {
            LOGGER.error("cannot remove ingredient by this name!");
        }
    }

    private void addNewIngredient(Scanner sc) {
        System.out.println("Enter name of ingredient");
        ingredientController.getAllIngredients().forEach(System.out::println);
        String ingredientName = sc.next();
        System.out.println("This ingredient is new in Ingredient department: 'y'/'n'");
        boolean newIngred = sc.next().equals("y");
        Ingredient ingredient = null;
        if (!newIngred) {
            try {
                ingredient = ingredientController.getIngredientByName(ingredientName);
            } catch (RuntimeException ex) {
                LOGGER.error("Cannot get ingredient by this name");
            }
        } else {
            ingredient = new Ingredient();
            ingredient.setName(ingredientName);
        }
        System.out.println("Enter amount of ingredient");
        int amount = sc.nextInt();
        if (ingredient != null) {
            warehouseController.addIngredient(ingredient, amount);
        }
    }
    /**
     * Stop private methods for warehouse page**/


    private void startApplication() {
        System.out.println("Hi! You entered to restaurant database");
        System.out.println("Select you next step");
        System.out.println("d - work with dish information\ne - work with info about employee");
        System.out.println("m - work with menu information\no - work with info about orders");
        System.out.println("rm - work with ready meals information\nw - work with info about ingredients in warehouse");
        System.out.println("q - to leave application");
    }

   /* private boolean isValid(String s) {
       return (s.equals("d") || s.equals("e") || s.equals("m") ||
               s.equals("o") || s.equals("rm") || s.equals("w"));
    }*/

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

    public void setEmployeeController(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    public void setStopApp(boolean stopApp) {
        this.stopApp = stopApp;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
