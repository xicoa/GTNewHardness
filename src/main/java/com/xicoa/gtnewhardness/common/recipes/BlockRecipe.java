package com.xicoa.gtnewhardness.common.recipes;

import static gregtech.api.recipe.RecipeMaps.BEAMCRAFTER_METADATA;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.beamcrafterRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import com.xicoa.gtnewhardness.common.enums.ItemList;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.loaders.postload.recipes.beamcrafter.BeamCrafterMetadata;
import gtnhlanth.common.beamline.Particle;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;

public class BlockRecipe {

    public static void init() {
        transcendentStableCasingRecipe();
    }

    public static void transcendentStableCasingRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Quantium, 6),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Tritanium, 2),
                gregtech.api.enums.ItemList.Casing_Chemically_Inert.get(1L),
                gregtech.api.enums.ItemList.Field_Generator_IV.get(2L))
            .fluidInputs(Materials.Neutronium.getMolten(2592L))
            .itemOutputs(ItemList.MACHINE_CASING_TRANSCENDENT_STABLE.get(1))
            .circuit(1)
            .eut(TierEU.RECIPE_ZPM)
            .duration(50 * TICKS)
            .addTo(assemblerRecipes);
    }
}
