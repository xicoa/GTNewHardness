package com.myname.mymodid.common.recipes;

import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import com.myname.mymodid.common.enums.Fluids;

import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;

public class MaterialRecipe {

    public static void init() {
        fluidRecipe();
    }

    public static void fluidRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(Materials.Diamond.getDust(4))
            .fluidInputs(
                Materials.SulfuricAcid.getFluid(1_000),
                Materials.NitricAcid.getFluid(1_000),
                Materials.HydrochloricAcid.getFluid(1_000),
                GGMaterial.fluoroantimonicAcid.getFluidOrGas(1_000))
            .fluidOutputs(Fluids.mixedAcid.getFluid(1_000))
            .eut(TierEU.RECIPE_EV)
            .duration(20 * SECONDS)
            .addTo(GTPPRecipeMaps.mixerNonCellRecipes);

        // GTValues.RA.stdBuilder()
        //     .itemInputs(
        //         GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.NetherStar, 1)))
        //     .fluidInputs(
        //         Materials.mixedAcid.getFluidOrGas(1_000))
        //     .fluidOutputs(
        //         Materials.electronPlasma.getFluidOrGas(800),
        //         Materials.Hydrogen.getPlasma(800))
        //     .eut(TierEU.RECIPE_LuV)
        //     .duration(20 * SECONDS)
        //     .addTo(RecipeMaps.laserEngraverRecipes);
    }
}
