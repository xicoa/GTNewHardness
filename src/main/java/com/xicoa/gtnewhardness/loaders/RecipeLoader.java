package com.xicoa.gtnewhardness.loaders;

import com.xicoa.gtnewhardness.common.recipes.AcidGeneratorRecipe;
import com.xicoa.gtnewhardness.common.recipes.BlockRecipe;
import com.xicoa.gtnewhardness.common.recipes.ChemicalReactorRecipe;
import com.xicoa.gtnewhardness.common.recipes.MachineRecipe;
import com.xicoa.gtnewhardness.common.recipes.MaterialRecipe;
import com.xicoa.gtnewhardness.common.recipes.ShockwaveEnergyEnhancerRecipe;

public class RecipeLoader {

    public static void load() {
        MaterialRecipe.init();
        AcidGeneratorRecipe.init();
        BlockRecipe.init();
        MachineRecipe.init();
        ShockwaveEnergyEnhancerRecipe.init();
        ChemicalReactorRecipe.init();
    }
}
