package com.xicoa.gtnewhardness.common.recipes;

import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.KekzTech;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import net.minecraft.item.ItemStack;

import com.carpentersblocks.util.registry.BlockRegistry;
import com.dreammaster.block.BlockList;
import com.xicoa.gtnewhardness.common.enums.ItemList;

import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gtPlusPlus.core.material.MaterialsAlloy;

public class BlockRecipe {

    public static void init() {
        transcendentStableCasingRecipe();
        shockwaveReflectingCasingRecipe();
        obliqueShockwaveReflectingCasingRecipe();
        shockwaveReflectingCasingWithCoilRecipe();
        magneticallyLevitatedHighDensityExplosiveUnfilledCasingRecipe();
        magneticallyLevitatedHighDensityExplosiveFilledCasingRecipe();
        plasmaContainmentCasingRecipe();
    }

    private static void transcendentStableCasingRecipe() {
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
            .duration(20 * SECONDS)
            .addTo(assemblerRecipes);
    }

    private static void shockwaveReflectingCasingRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                gregtech.api.enums.ItemList.Neutron_Reflector.get(4L),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.NaquadahEnriched, 2),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Netherite, 2))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(288))
            .itemOutputs(ItemList.MACHINE_CASING_SHOCKWAVE_REFLECTING.get(16))
            .circuit(1)
            .eut(TierEU.RECIPE_ZPM)
            .duration(10 * SECONDS)
            .addTo(assemblerRecipes);
    }

    private static void obliqueShockwaveReflectingCasingRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                new ItemStack(BlockRegistry.blockCarpentersSlope, 16, 0),
                gregtech.api.enums.ItemList.Neutron_Reflector.get(4L),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.NaquadahEnriched, 2),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Netherite, 2))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(288))
            .itemOutputs(ItemList.MACHINE_CASING_OBLIQUE_SHOCKWAVE_REFLECTING.get(16))
            .circuit(2)
            .eut(TierEU.RECIPE_ZPM)
            .duration(10 * SECONDS)
            .addTo(assemblerRecipes);
    }

    private static void shockwaveReflectingCasingWithCoilRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                gregtech.api.enums.ItemList.Superconducting_Magnet_Solenoid_ZPM.get(2L),
                gregtech.api.enums.ItemList.Neutron_Reflector.get(4L),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.NaquadahEnriched, 2),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Netherite, 2))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(288))
            .itemOutputs(ItemList.MACHINE_CASING_WITH_COIL_SHOCKWAVE_REFLECTING.get(16))
            .circuit(3)
            .eut(TierEU.RECIPE_ZPM)
            .duration(10 * SECONDS)
            .addTo(assemblerRecipes);
    }

    private static void magneticallyLevitatedHighDensityExplosiveUnfilledCasingRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                gregtech.api.enums.ItemList.Superconducting_Magnet_Solenoid_IV.get(1L),
                gregtech.api.enums.ItemList.Super_Tank_LV.get(1L),
                BlockList.StainlessSteelBars.get(32))
            .fluidInputs(Materials.ElectricalSteel.getMolten(144L))
            .itemOutputs(ItemList.MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_UNFILLED.get(4))
            .circuit(1)
            .eut(TierEU.IV)
            .duration(10 * SECONDS)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                gregtech.api.enums.ItemList.Superconducting_Magnet_Solenoid_LuV.get(1L),
                gregtech.api.enums.ItemList.Super_Tank_MV.get(1L),
                BlockList.TitaniumBars.get(32))
            .fluidInputs(Materials.Redstone.getMolten(144L))
            .itemOutputs(ItemList.MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_UNFILLED.get(16))
            .circuit(1)
            .eut(TierEU.LuV)
            .duration(10 * SECONDS)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                gregtech.api.enums.ItemList.Superconducting_Magnet_Solenoid_ZPM.get(1L),
                gregtech.api.enums.ItemList.Super_Tank_HV.get(1L),
                BlockList.TungstenSteelBars.get(32))
            .fluidInputs(Materials.EnergeticAlloy.getMolten(144L))
            .itemOutputs(ItemList.MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_UNFILLED.get(128))
            .circuit(1)
            .eut(TierEU.ZPM)
            .duration(10 * SECONDS)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                gregtech.api.enums.ItemList.Superconducting_Magnet_Solenoid_UV.get(1L),
                gregtech.api.enums.ItemList.Super_Tank_EV.get(1L),
                BlockList.IridiumBars.get(32))
            .fluidInputs(Materials.VibrantAlloy.getMolten(144L))
            .itemOutputs(ItemList.MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_UNFILLED.get(512))
            .circuit(1)
            .eut(TierEU.UV)
            .duration(10 * SECONDS)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                gregtech.api.enums.ItemList.Superconducting_Magnet_Solenoid_UHV.get(1L),
                gregtech.api.enums.ItemList.Super_Tank_IV.get(1L),
                BlockList.OsmiumBars.get(32))
            .fluidInputs(Materials.EndSteel.getMolten(144L))
            .itemOutputs(ItemList.MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_UNFILLED.get(4096))
            .circuit(1)
            .eut(TierEU.UHV)
            .duration(10 * SECONDS)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                gregtech.api.enums.ItemList.Superconducting_Magnet_Solenoid_UMV.get(1L),
                GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemExtremeStorageCell.Universe", 1L, 0),
                BlockList.NeutroniumBars.get(32))
            .fluidInputs(Materials.Eternity.getMolten(144L))
            .itemOutputs(
                ItemList.MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_UNFILLED.get(2_147_483_647))
            .circuit(1)
            .eut(TierEU.UMV)
            .duration(10 * SECONDS)
            .addTo(assemblerRecipes);
    }

    private static void magneticallyLevitatedHighDensityExplosiveFilledCasingRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTModHandler.getIC2Item("industrialTnt", 64),
                ItemList.MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_UNFILLED.get(64))
            .itemOutputs(ItemList.MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_FILLED.get(64))
            .circuit(1)
            .eut(TierEU.LuV)
            .duration(10 * TICKS)
            .addTo(assemblerRecipes);
    }

    private static void plasmaContainmentCasingRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTModHandler.getModItem(KekzTech.ID, "kekztech_tfftstoragefield_block", 1, 4), // T4
                GGMaterial.lumiium.get(OrePrefixes.plate, 16))
            .fluidInputs(Materials.Hydrogen.getPlasma(8_000L))
            .itemOutputs(ItemList.MACHINE_CASING_PLASMA_CONTAINMENT.get(16))
            .circuit(1)
            .eut(TierEU.LuV)
            .duration(10 * SECONDS)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTModHandler.getModItem(KekzTech.ID, "kekztech_tfftstoragefield_block", 1, 7), // T7
                GGMaterial.enrichedNaquadahAlloy.get(OrePrefixes.plate, 16))
            .fluidInputs(Materials.Hydrogen.getPlasma(80_000L))
            .itemOutputs(ItemList.MACHINE_CASING_PLASMA_CONTAINMENT.get(160))
            .circuit(1)
            .eut(TierEU.UV)
            .duration(10 * SECONDS)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTModHandler.getModItem(KekzTech.ID, "kekztech_tfftstoragefield_block", 1, 10), // T10
                GGMaterial.atomicSeparationCatalyst.get(OrePrefixes.plate, 16))
            .fluidInputs(Materials.Hydrogen.getPlasma(32_000_000L))
            .itemOutputs(ItemList.MACHINE_CASING_PLASMA_CONTAINMENT.get(64_000))
            .circuit(1)
            .eut(TierEU.UMV)
            .duration(10 * SECONDS)
            .addTo(assemblerRecipes);
    }
}
