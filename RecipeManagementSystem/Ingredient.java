package RecipeManagementSystem;

import java.io.Serializable;

public class Ingredient implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String ingredientName;
    private String amount;

    public Ingredient(String ingredientName, String amount)
    {
        this.ingredientName = ingredientName;
        this.amount = amount;
    }

    public void updateAmount(String amount)
    {
        this.amount = amount;
    }

    public String getIngredientName()
    {
        return ingredientName;
    }

    public String getAmount()
    {
        return amount;
    }
}

