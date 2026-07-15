package com.xicoa.gtnewhardness.loaders;

import com.xicoa.gtnewhardness.common.recipes.AcidGeneratorRecipe;
import com.xicoa.gtnewhardness.common.recipes.MaterialRecipe;

public class RecipeLoader {

    public static void init() {
        MaterialRecipe.init();
        AcidGeneratorRecipe.init();
    }
}
