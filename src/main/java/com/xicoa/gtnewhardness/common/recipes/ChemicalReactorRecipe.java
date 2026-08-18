package com.xicoa.gtnewhardness.common.recipes;

import static gregtech.api.recipe.RecipeMaps.chemicalReactorRecipes;
import static gregtech.api.recipe.RecipeMaps.multiblockChemicalReactorRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import com.xicoa.gtnewhardness.common.enums.Materials;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;

public class ChemicalReactorRecipe {

    public static void init() {
        mixedAcidicPlamsaRelatedRecipe();
    }

    private static void mixedAcidicPlamsaRelatedRecipe() {

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get("shardPrismarine", 1L),
                GTOreDictUnificator.get(OrePrefixes.cell, gregtech.api.enums.Materials.Grade1PurifiedWater, 1L))
            .itemOutputs(gregtech.api.enums.Materials.Empty.getCells(1))
            .fluidInputs(Materials.mixedAcidicPlasma.getPlasma(100))
            .fluidOutputs(gregtech.api.enums.Materials.PrismaticGas.getFluid(1_000))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(chemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get("shardPrismarine", 1L))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, gregtech.api.enums.Materials.Ash, 1))
            .fluidInputs(
                Materials.mixedAcidicPlasma.getPlasma(100),
                gregtech.api.enums.Materials.Grade1PurifiedWater.getFluid(1_000))
            .fluidOutputs(gregtech.api.enums.Materials.PrismaticGas.getFluid(1_000))
            .circuit(1)
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get("shardPrismarine", 10L))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, gregtech.api.enums.Materials.Ash, 1))
            .fluidInputs(
                Materials.mixedAcidicPlasma.getPlasma(200),
                gregtech.api.enums.Materials.Grade4PurifiedWater.getFluid(1_000),
                gregtech.api.enums.Materials.Grade1PurifiedWater.getFluid(9_000))
            .fluidOutputs(gregtech.api.enums.Materials.PrismaticGas.getFluid(10_000))
            .circuit(1)
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(multiblockChemicalReactorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get("shardPrismarine", 100L))
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, gregtech.api.enums.Materials.Ash, 1))
            .fluidInputs(
                Materials.mixedAcidicPlasma.getPlasma(400),
                gregtech.api.enums.Materials.Grade8PurifiedWater.getFluid(1_000),
                gregtech.api.enums.Materials.Grade1PurifiedWater.getFluid(99_000))
            .fluidOutputs(gregtech.api.enums.Materials.PrismaticGas.getFluid(100_000))
            .circuit(1)
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_UEV)
            .addTo(multiblockChemicalReactorRecipes);
    }
}
