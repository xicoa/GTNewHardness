package com.xicoa.gtnewhardness.common.recipes;

import static com.xicoa.gtnewhardness.common.recipeMaps.NHRecipeMaps.SHOCKWAVE_ENERGY_ENHANCER_RECIPES;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import com.xicoa.gtnewhardness.common.enums.ItemList;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;

public final class ShockwaveEnergyEnhancerRecipe {

    public static void init() {
        GTValues.RA.stdBuilder()
            .fluidInputs(Materials.Toluene.getFluid(1_000))
            .fluidOutputs(Materials.Toluene.getFluid(1))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_ZPM)
            .fake()
            .addTo(SHOCKWAVE_ENERGY_ENHANCER_RECIPES);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_FILLED.get(1),
                ItemList.MACHINE_CASING_PLASMA_CONTAINMENT.get(1))
            .fluidOutputs(Materials.Hydrogen.getPlasma(1_000))
            .duration(MINUTES)
            .eut(TierEU.RECIPE_ZPM)
            .fake()
            .addTo(SHOCKWAVE_ENERGY_ENHANCER_RECIPES);
    }

    private ShockwaveEnergyEnhancerRecipe() {}
}
