package RecipeManagementSystem;
import java.util.ArrayList;
import java.util.List;

public class ShoppingList
{
    private List<Ingredient> ingredients;

    public ShoppingList()
    {
        ingredients = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient)
    {
        ingredients.add(ingredient);
    }

    public void displayShoppingList()
    {
        System.out.println("Shopping List:");
        for (Ingredient ingredient : ingredients)
        {
            System.out.println("-> " + ingredient.getAmount() + " " + ingredient.getIngredientName());
        }
    }
}
