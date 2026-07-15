package com.xicoa.gtnewhardness.common.recipes;

import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import com.xicoa.gtnewhardness.common.enums.Materials;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTUtility;
import gtnhlanth.common.beamline.Particle;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;

public class MaterialRecipe {

    public static void init() {
        mixedAcidRecipe();

    }

    public static void mixedAcidRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(gregtech.api.enums.Materials.Diamond.getDust(4), GTUtility.getIntegratedCircuit(23))
            .fluidInputs(
                gregtech.api.enums.Materials.SulfuricAcid.getFluid(1_000),
                gregtech.api.enums.Materials.NitricAcid.getFluid(1_000),
                gregtech.api.enums.Materials.HydrochloricAcid.getFluid(1_000)
            // gregtech.api.enums.Materials.fluoroantimonicAcid.getFluidOrGas(1_000)
            )
            .fluidOutputs(Materials.mixedAcid.getFluid(1_000))
            .eut(TierEU.RECIPE_EV)
            .duration(20 * SECONDS)
            .addTo(GTPPRecipeMaps.mixerNonCellRecipes);
    }

    // public static void hydrogenPlasmaRecipe() {
    //     GTValues.RA.stdBuilder()
    //         .itemInputs(Particle. Particle.PROTON.)
    // }
}
