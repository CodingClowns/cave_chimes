package com.fincode.cavechimes.common.recipe;

import com.fincode.cavechimes.Config;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

// Luckily, this will be deprecated in newer versions, since recipe type customization will soon be a thing.
public class CaveChimesRecipe extends ShapedRecipes {
    public CaveChimesRecipe(String group, int width, int height, ItemStack result) {
        super(group, width, height, caveChimesIngredients(), result);
    }
    // Ew.
    private static NonNullList<Ingredient> caveChimesIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();

        ingredients.add(Ingredient.fromItem(Items.STRING));
        ingredients.add(Ingredient.fromItem(Items.RECORD_13));
        ingredients.add(Ingredient.fromItem(ItemBlock.getItemFromBlock(Blocks.GOLD_BLOCK)));

        return ingredients;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return super.matches(inv, worldIn) && Config.obtaining.canCraft;
    }
}
