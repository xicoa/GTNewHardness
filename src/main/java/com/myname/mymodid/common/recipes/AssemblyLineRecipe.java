package com.myname.mymodid.common.recipes;

import static gregtech.api.util.GTRecipeConstants.AssemblyLine;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTRecipeBuilder;

public class AssemblyLineRecipe {

    public void run() {
        GTRecipeBuilder builder = GTRecipeBuilder.builder();
        builder.itemInputs(new ItemStack(Items.apple, 2), new ItemStack(Items.cake, 2))
            .itemOutputs(new ItemStack(Items.book, 64))
            .eut(40)
            .duration(40)
            .addTo(AssemblyLine);
        builder.addTo(RecipeMaps.assemblylineVisualRecipes);
        builder.addTo(RecipeMaps.assemblerRecipes);
    }
}
