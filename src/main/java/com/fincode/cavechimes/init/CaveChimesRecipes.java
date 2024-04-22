package com.fincode.cavechimes.init;

import com.fincode.cavechimes.common.recipe.CaveChimesRecipe;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class CaveChimesRecipes {
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        registry.register(new CaveChimesRecipe("cave_chimes", 1, 3, new ItemStack(CaveChimesItems.CAVE_CHIMES, 1)).setRegistryName("cave_chimes"));
    }
}
