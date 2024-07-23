package RecipeManagementSystem;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Cookbook implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Recipe> recipes;

    public Cookbook() {
        recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public List<Recipe> searchRecipes(String keyword) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getRecipeName().contains(keyword)) {
                result.add(recipe);
            }
        }
        return result;
    }

    public Recipe getRecipe(String recipeName) {
        for (Recipe recipe : recipes) {
            if (recipe.getRecipeName().equalsIgnoreCase(recipeName)) {
                return recipe;
            }
        }
        return null;
    }

    public void displayCookbook() {
        for (Recipe recipe : recipes) {
            recipe.displayRecipe();
            System.out.println();
        }
    }

    public void saveCookbookToFile() {
        try{
            FileOutputStream fos=new FileOutputStream("Database.txt",true);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(recipes);
            System.out.println("Cookbook saved to file");

        } catch (IOException e) {
            System.out.println("Error saving cookbook to file: " + e.getMessage());
        }
    }


    public void loadCookbookFromFile() {
        try
        {
            FileInputStream fis=new FileInputStream("Database.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            recipes = (List<Recipe>) ois.readObject();
            System.out.println("Cookbook loaded from file: Database");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading cookbook from file: " + e.getMessage());
        }
    }
}