package com.xicoa.gtnewhardness.common.recipes;

import static gregtech.api.util.GTRecipeConstants.FUEL_VALUE;

import bartworks.API.recipe.BartWorksRecipeMaps;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTOreDictUnificator;

public class AcidGeneratorRecipe {

    public static void init() {
        acidGeneratorRecipe();
    }

    public static void acidGeneratorRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.cellPlasma, gregtech.api.enums.Materials.Hydrogen, 1L))
            .itemOutputs(gregtech.api.enums.Materials.Empty.getCells(1))
            .metadata(FUEL_VALUE, 20000)
            .addTo(BartWorksRecipeMaps.acidGenFuels);
    }
}
