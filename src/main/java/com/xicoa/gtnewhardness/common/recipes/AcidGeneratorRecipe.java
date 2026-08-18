package com.xicoa.gtnewhardness.common.recipes;

import static gregtech.api.util.GTRecipeConstants.FUEL_VALUE;

import com.xicoa.gtnewhardness.common.enums.Materials;

import bartworks.API.recipe.BartWorksRecipeMaps;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTOreDictUnificator;

public class AcidGeneratorRecipe {

    public static void init() {
        hydrogenPlasmaRecipe();
        impureExcitedHydrogenPlasmaRecipe();
        excitedHydrogenPlasmaRecipe();
        mixedAcidicPlasmaRecipe();
    }

    private static void hydrogenPlasmaRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.cellPlasma, gregtech.api.enums.Materials.Hydrogen, 1L))
            .itemOutputs(gregtech.api.enums.Materials.Empty.getCells(1))
            .metadata(FUEL_VALUE, 10000)
            .addTo(BartWorksRecipeMaps.acidGenFuels);
    }

    private static void impureExcitedHydrogenPlasmaRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.cellPlasma, Materials.impureExcitedHydrogenPlasma, 1L))
            .itemOutputs(gregtech.api.enums.Materials.Empty.getCells(1))
            .metadata(FUEL_VALUE, 100000)
            .addTo(BartWorksRecipeMaps.acidGenFuels);
    }

    private static void excitedHydrogenPlasmaRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.cellPlasma, Materials.excitedHydrogenPlasma, 1L))
            .itemOutputs(gregtech.api.enums.Materials.Empty.getCells(1))
            .metadata(FUEL_VALUE, 1000000)
            .addTo(BartWorksRecipeMaps.acidGenFuels);
    }

    private static void mixedAcidicPlasmaRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.cellPlasma, Materials.mixedAcidicPlasma, 1L))
            .itemOutputs(gregtech.api.enums.Materials.Empty.getCells(1))
            .metadata(FUEL_VALUE, 200000)
            .addTo(BartWorksRecipeMaps.acidGenFuels);
    }
}
