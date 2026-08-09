package com.xicoa.gtnewhardness.common.recipeMaps;

import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMapBuilder;

public final class NHRecipeMaps {

    public static final RecipeMap<RecipeMapBackend> SHOCKWAVE_ENERGY_ENHANCER_RECIPES = RecipeMapBuilder
        .of("gtnewhardness.recipe.shockwave_energy_enhancer")
        .maxIO(2, 0, 1, 1)
        .minInputs(0, 0)
        .frontend(ShockwaveEnergyEnhancerFrontend::new)
        .build();

    private NHRecipeMaps() {}
}
