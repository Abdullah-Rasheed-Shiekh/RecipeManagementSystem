package RecipeManagementSystem;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;



public class TestClass extends Application implements Serializable{
    private static List<User>userList=new ArrayList<>();
    private static User currentUser=null;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Cookbook cookbook = new Cookbook();
        ShoppingList list = new ShoppingList();
        boolean exitLoop=false;
        loadUsersFromFile();
        launch(args);

        while (!exitLoop) {
            if (currentUser == null) {
                System.out.println("________________Welcome To The Recipe Management System__________________");
                System.out.println("1->Create an account!");
                System.out.println("2->Login (Already have an account)");
                System.out.println("3->Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        signUp(scanner);
                        break;
                    case 2:
                        login(scanner);
                        break;
                    case 3:
                        exitLoop = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
            else {
                cookbook.loadCookbookFromFile();
                while (true) {
                    System.out.println("Choose an option:");
                    System.out.println("1. Add Recipe in Cookbook");
                    System.out.println("2. Search Recipe in Cookbook");
                    System.out.println("3. Display All Recipes in Cookbook");
                    System.out.println("4. Generate Shopping List");
                    System.out.println("0. Exit");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1 -> {
                            System.out.print("Enter recipe name: ");
                            String recipeNameToAdd = scanner.nextLine();
                            System.out.println("Enter ingredients (use commas to separate ingredients):");
                            String ingredientListToAdd = scanner.nextLine();
                            String[] ingredientsArrayToAdd = ingredientListToAdd.split(",");
                            List<Ingredient> ingredientsToAdd = new ArrayList<>();
                            for (String ingredientString : ingredientsArrayToAdd) {
                                String[] ingredientParts = ingredientString.split(" ");
                                Ingredient ingredient = new Ingredient(ingredientParts[0], ingredientParts[1]);
                                ingredientsToAdd.add(ingredient);
                            }
                            System.out.println("Enter instructions (enter 'done' when finished):");
                            List<String> instructionsToAdd = new ArrayList<>();
                            String instructionToAdd = scanner.nextLine();
                            while (!instructionToAdd.equals("done")) {
                                instructionsToAdd.add(instructionToAdd);
                                instructionToAdd = scanner.nextLine();
                            }
                            Recipe recipeToAdd = new Recipe(recipeNameToAdd, instructionsToAdd.toArray(new String[0]));
                            for (Ingredient ingredient : ingredientsToAdd) {
                                recipeToAdd.addIngredient(ingredient);
                            }
                            cookbook.addRecipe(recipeToAdd);
                            System.out.println("Congratulations!! Recipe added to cookbook.");
                            recipeToAdd.displayRecipe();

                        }
                        case 2 -> {
                            System.out.println("Enter keyword to search for:");
                            String keyword = scanner.nextLine();
                            List<Recipe> results = cookbook.searchRecipes(keyword);
                            if (results.isEmpty()) {
                                System.out.println("No recipes found.");
                            } else {
                                System.out.println("Recipes found:");
                                for (Recipe result : results) {
                                    result.displayRecipe();
                                    System.out.println();
                                }
                            }
                        }
                        case 3 -> cookbook.displayCookbook();
                        case 4 -> {
                            System.out.print("Enter recipe name to generate shopping list: ");
                            String recipeName = scanner.nextLine();
                            Recipe recipe = cookbook.getRecipe(recipeName);
                            if (recipe == null) {
                                System.out.println("Recipe not found in cookbook.");
                            } else {
                                for (Ingredient ingredient : recipe.getIngredients()) {
                                    list.addIngredient(ingredient);
                                }
                                System.out.println("-------------------" + recipe.getRecipeName() + "-------------------");
                                System.out.println("------SHOPPING LIST------");
                                list.displayShoppingList();
                            }
                        }
                        case 0 -> {
                            System.out.println("Successfully Exited The Program");
                            cookbook.saveCookbookToFile();
                            System.exit(0);
                        }
                        default -> System.out.println("Invalid choice.");
                    }
                }
            }
        }
    }
    public static void login(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();
        if (password.length() < 8) {
            System.out.println("Invalid Password");
            System.exit(0);
        }
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println();
                System.out.println("_______________________________________");
                System.out.println("Login successful! Welcome, " + user.getUsername() + ".");
                System.out.println("---------------------------------------");
                System.out.println();
                saveUsersToFile();
                return;
            }
        }

        System.out.println("Invalid username or password. \nPlease try again.");
        System.out.println();
    }

    public static void signUp(Scanner scanner) {
        System.out.print("Enter a username: ");
        String username = scanner.next();
        System.out.print("Enter a password: ");
        String password = scanner.next();
        if (password.length() < 8) {
            System.out.println("Invalid Password Format");
            System.exit(0);
        }

        User newUser = new User(username, password);
        userList.add(newUser);

        System.out.println("Sign up successful! \nPlease press log in BUTTON.");
        System.out.println();
    }
    public static void loadUsersFromFile() {
        try {
            createFileIfNotExists();

            File file = new File("users.txt");
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String userData = scanner.nextLine();
                    String name = extractName(userData);
                    String password = extractPassword(userData);
                    userList.add(new User(name, password));
                }
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String extractName(String userData) {
        int startIndex = userData.indexOf("User Name: ") + 11;
        int endIndex = userData.indexOf(", Password:");
        return userData.substring(startIndex, endIndex).trim();
    }

    public static String extractPassword(String userData) {
        int startIndex = userData.indexOf("Password: ") + 10;
        return userData.substring(startIndex).trim();
    }

    public static void saveUsersToFile() {
        try {
            File file = new File("users.txt");
            FileWriter writer = new FileWriter(file);

            for (User user : userList) {
                String userData = "User Name: " + user.getUsername() + ", Password: " + user.getPassword();
                writer.write(userData);
                writer.write(System.lineSeparator());
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createFileIfNotExists() {
        try {
            File file = new File("users.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        Label heading = new Label("  WELCOME TO RECIPEE \nMANAGEMENT SYSTEM ");
        heading.setBackground(Background.fill(Color.DARKBLUE));
        heading.setTextFill(Color.YELLOW);
        heading.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
        VBox head = new VBox(20,heading);
        head.setAlignment(Pos.CENTER);

        Button createAccountButton = new Button("Create an account!");
        Button loginButton = new Button("Login");
        Button exitButton = new Button("Exit");
        VBox buttons = new VBox(20, createAccountButton, loginButton, exitButton);
        buttons.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setTop(head);
        pane.setCenter(buttons);

        pane.setStyle("-fx-background-color: aqua");
        pane.setBackground(Background.fill(Color.YELLOW));


        Scene scene = new Scene(pane, 600, 400);
        stage.setTitle("RECIPEE MANAGEMENT SYSTEM");
        scene.setFill(Color.YELLOW);

        createAccountButton.setOnAction(e->{
            signUp(new Scanner(System.in));
        });
        loginButton.setOnAction(e->{
            login(new Scanner(System.in));
            stage.close();

        });
        exitButton.setOnAction(e->{
            System.exit(0);
        });
        stage.setScene(scene);
        stage.show();
    }

}
