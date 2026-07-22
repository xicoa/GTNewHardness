package com.xicoa.gtnewhardness.loaders;

import com.xicoa.gtnewhardness.common.recipes.AcidGeneratorRecipe;
import com.xicoa.gtnewhardness.common.recipes.BlockRecipe;
import com.xicoa.gtnewhardness.common.recipes.MaterialRecipe;

public class RecipeLoader {

    public static void load() {
        MaterialRecipe.init();
        AcidGeneratorRecipe.init();
        BlockRecipe.init();
    }
}
