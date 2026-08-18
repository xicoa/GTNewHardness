package com.xicoa.gtnewhardness.common.recipes;

import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.AssemblyLine;
import static gregtech.api.util.GTRecipeConstants.RESEARCH_ITEM;
import static gregtech.api.util.GTRecipeConstants.SCANNING;

import com.dreammaster.item.NHItemList;
import com.xicoa.gtnewhardness.common.enums.ItemList;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;
import gregtech.api.util.recipe.Scanning;
import gtPlusPlus.core.material.MaterialsAlloy;

public class MachineRecipe {

    public static void init() {
        ShockwaveEnergyEnhancerControllerRecipe();
    }

    private static void ShockwaveEnergyEnhancerControllerRecipe() {
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, ItemList.MACHINE_CASING_TRANSCENDENT_STABLE.get(1))
            .metadata(SCANNING, new Scanning(20 * MINUTES, TierEU.IV))
            .itemInputs(
                ItemList.MACHINE_CASING_WITH_COIL_SHOCKWAVE_REFLECTING.get(4),
                gregtech.api.enums.ItemList.Field_Generator_LuV.get(1L),
                gregtech.api.enums.ItemList.Robot_Arm_LuV.get(4L),
                gregtech.api.enums.ItemList.Emitter_LuV.get(4L),
                NHItemList.CircuitZPM.get(1),
                NHItemList.CircuitZPM.get(1),
                NHItemList.CircuitZPM.get(1),
                NHItemList.CircuitZPM.get(1),
                gregtech.api.enums.ItemList.Neutron_Reflector.get(4L))
            .fluidInputs(
                MaterialsAlloy.INDALLOY_140.getFluidStack(8 * 144),
                Materials.HSSS.getMolten(4 * 144),
                Materials.Grade2PurifiedWater.getFluid(1_000))
            .itemOutputs(ItemList.MACHINE_MULTI_SHOCKWAVE_ENERGY_ENHANCER.get(1))
            .eut(TierEU.RECIPE_ZPM)
            .duration(30 * SECONDS)
            .addTo(AssemblyLine);
    }
}
