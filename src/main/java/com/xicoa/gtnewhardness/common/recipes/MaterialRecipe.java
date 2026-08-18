package com.xicoa.gtnewhardness.common.recipes;

import static gregtech.api.recipe.RecipeMaps.BEAMCRAFTER_METADATA;
import static gregtech.api.recipe.RecipeMaps.beamcrafterRecipes;
import static gregtech.api.recipe.RecipeMaps.distillationTowerRecipes;
import static gregtech.api.recipe.RecipeMaps.distilleryRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.xicoa.gtnewhardness.common.enums.Materials;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.TierEU;
import gregtech.loaders.postload.recipes.beamcrafter.BeamCrafterMetadata;
import gtnhlanth.common.beamline.Particle;

public class MaterialRecipe {

    public static void init() {
        hydrogenPlasmaRecipe();
        electronPlasmaRecipe();
        impureExcitedHydrogenPlasmaRelatedRecipe();
    }

    private static void hydrogenPlasmaRecipe() {
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

    private static void electronPlasmaRecipe() {
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
            .fluidOutputs(Materials.electronPlasma.getPlasma(640))
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
            .fluidOutputs(Materials.electronPlasma.getPlasma(2560))
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
            .fluidOutputs(Materials.electronPlasma.getPlasma(10240))
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

    private static void impureExcitedHydrogenPlasmaRelatedRecipe() {
        GTValues.RA.stdBuilder()
            .circuit(1)
            .fluidInputs(Materials.impureExcitedHydrogenPlasma.getPlasma(100))
            .fluidOutputs(Materials.excitedHydrogenPlasma.getPlasma(60))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(distilleryRecipes);

        GTValues.RA.stdBuilder()
            .circuit(2)
            .fluidInputs(Materials.impureExcitedHydrogenPlasma.getPlasma(100))
            .fluidOutputs(Materials.mixedAcidicPlasma.getPlasma(40))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(distilleryRecipes);

        GTValues.RA.stdBuilder()
            .fluidInputs(Materials.impureExcitedHydrogenPlasma.getPlasma(100))
            .fluidOutputs(Materials.excitedHydrogenPlasma.getPlasma(70), Materials.mixedAcidicPlasma.getPlasma(50))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(distillationTowerRecipes);
    }
}
