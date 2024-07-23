package RecipeManagementSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String recipeName;
    private List<Ingredient> ingredients;
    private String[] instructions;

    public Recipe(String recipeName, String[] instructions)
    {
        this.recipeName = recipeName;
        this.instructions = instructions;
        this.ingredients = new ArrayList<>();
    }

    public String getRecipeName()
    {
        return recipeName;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ingredient)
    {
        ingredients.add(ingredient);
    }

    public void displayRecipe()
    {
        System.out.println("-------------------------- " + recipeName + "-------------------------- ");
        System.out.println("Ingredients:");
        for (Ingredient ingredient : ingredients)
        {
            System.out.println("-:> " + ingredient.getAmount() + " " + ingredient.getIngredientName());
        }
        System.out.println("Instructions:");
        for (String instruction : instructions)
        {
            System.out.println("-:> " + instruction);
        }
    }
}
