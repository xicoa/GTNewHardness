package com.xicoa.gtnewhardness.common.recipes;

import static gregtech.api.recipe.RecipeMaps.BEAMCRAFTER_METADATA;
import static gregtech.api.recipe.RecipeMaps.beamcrafterRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import com.xicoa.gtnewhardness.common.enums.Materials;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTUtility;
import gregtech.loaders.postload.recipes.beamcrafter.BeamCrafterMetadata;
import gtnhlanth.common.beamline.Particle;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;

public class MaterialRecipe {

    public static void init() {
        mixedAcidRecipe();
        hydrogenPlasmaRecipe();
        electronPlasmaRecipe();
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

    public static void hydrogenPlasmaRecipe() {
        GTValues.RA.stdBuilder()
            .fluidInputs(gregtech.api.enums.Materials.Hydrogen.getGas(1000))
            .fluidOutputs(gregtech.api.enums.Materials.Hydrogen.getPlasma(100))
            .metadata(
                BEAMCRAFTER_METADATA,
                BeamCrafterMetadata.builder()
                    .particleID_A(Particle.PROTON.getId())
                    .particleID_B(Particle.PROTON.getId())
                    .amount_A(16)
                    .amount_B(16)
                    .build())
            .duration(5 * TICKS)
            .eut(TierEU.RECIPE_ZPM)
            .addTo(beamcrafterRecipes);
    }

    public static void electronPlasmaRecipe() {
        ItemStack filledSmallSunnariumBattery = ItemList.BatteryHull_EV_Full.get(1L);
        NBTTagCompound euNBT_small = filledSmallSunnariumBattery.getTagCompound();
        if (euNBT_small != null) {
            euNBT_small.setLong("GT.ItemCharge", 6_400_000L);
        } else {
            euNBT_small = new NBTTagCompound();
            euNBT_small.setLong("GT.ItemCharge", 6_400_000L);
            filledSmallSunnariumBattery.setTagCompound(euNBT_small);
        }

        ItemStack filledMediumSunnariumBattery = ItemList.BatteryHull_IV_Full.get(1L);
        NBTTagCompound euNBT_medium = filledMediumSunnariumBattery.getTagCompound();
        if (euNBT_medium != null) {
            euNBT_medium.setLong("GT.ItemCharge", 25_600_000L);
        } else {
            euNBT_medium = new NBTTagCompound();
            euNBT_medium.setLong("GT.ItemCharge", 25_600_000L);
            filledMediumSunnariumBattery.setTagCompound(euNBT_medium);
        }

        ItemStack filledLargeSunnariumBattery = ItemList.BatteryHull_LuV_Full.get(1L);
        NBTTagCompound euNBT_large = filledLargeSunnariumBattery.getTagCompound();
        if (euNBT_large != null) {
            euNBT_large.setLong("GT.ItemCharge", 102_400_000L);
        } else {
            euNBT_large = new NBTTagCompound();
            euNBT_large.setLong("GT.ItemCharge", 102_400_000L);
            filledLargeSunnariumBattery.setTagCompound(euNBT_large);
        }


        GTValues.RA.stdBuilder()
            .itemInputs(filledSmallSunnariumBattery)
            .fluidOutputs(Materials.electron.getPlasma(640))
            .metadata(
                BEAMCRAFTER_METADATA,
                BeamCrafterMetadata.builder()
                    .particleID_A(Particle.ELECTRON.getId())
                    .particleID_B(Particle.ELECTRON.getId())
                    .amount_A(16)
                    .amount_B(16)
                    .build())
            .duration(5 * TICKS)
            .eut(TierEU.RECIPE_UV)
            .addTo(beamcrafterRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(filledMediumSunnariumBattery)
            .fluidOutputs(Materials.electron.getPlasma(2560))
            .metadata(
                    BEAMCRAFTER_METADATA,
                    BeamCrafterMetadata.builder()
                            .particleID_A(Particle.ELECTRON.getId())
                            .particleID_B(Particle.ELECTRON.getId())
                            .amount_A(32)
                            .amount_B(32)
                            .build())
            .duration(5 * TICKS)
            .eut(TierEU.RECIPE_UV)
            .addTo(beamcrafterRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(filledLargeSunnariumBattery)
            .fluidOutputs(Materials.electron.getPlasma(10240))
            .metadata(
                    BEAMCRAFTER_METADATA,
                    BeamCrafterMetadata.builder()
                            .particleID_A(Particle.ELECTRON.getId())
                            .particleID_B(Particle.ELECTRON.getId())
                            .amount_A(64)
                            .amount_B(64)
                            .build())
            .duration(5 * TICKS)
            .eut(TierEU.RECIPE_UV)
            .addTo(beamcrafterRecipes);
    }
}
