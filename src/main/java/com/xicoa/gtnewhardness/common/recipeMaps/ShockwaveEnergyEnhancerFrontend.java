package com.xicoa.gtnewhardness.common.recipeMaps;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.minecraft.util.StatCollector;

import com.gtnewhorizons.modularui.api.math.Pos2d;

import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.recipe.RecipeMapFrontend;
import gregtech.common.gui.modularui.UIHelper;
import gregtech.nei.GTNEIDefaultHandler.FixedPositionedStack;

public class ShockwaveEnergyEnhancerFrontend extends RecipeMapFrontend {

    private static final String RECIPE_NOTE = "gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.recipe_note";

    public ShockwaveEnergyEnhancerFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder,
        NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(uiPropertiesBuilder, neiPropertiesBuilder);
    }

    @Override
    public List<Pos2d> getItemInputPositions(int itemInputCount) {
        return UIHelper.getGridPositions(itemInputCount, 34, 24, 2, 1);
    }

    @Override
    public List<Pos2d> getItemOutputPositions(int itemOutputCount) {
        return UIHelper.getGridPositions(itemOutputCount, 106, 24, 1, 1);
    }

    @Override
    public List<Pos2d> getFluidInputPositions(int fluidInputCount) {
        return UIHelper.getGridPositions(fluidInputCount, 34, 62, 1, 1);
    }

    @Override
    public List<Pos2d> getFluidOutputPositions(int fluidOutputCount) {
        return UIHelper.getGridPositions(fluidOutputCount, 106, 62, 1, 1);
    }

    @Nonnull
    @Override
    protected List<String> handleNEIItemInputTooltip(@Nonnull List<String> currentTip,
        @Nonnull FixedPositionedStack positionedStack) {
        currentTip = Objects.requireNonNull(super.handleNEIItemInputTooltip(currentTip, positionedStack));
        addRecipeNote(currentTip);
        return currentTip;
    }

    @Nonnull
    @Override
    protected List<String> handleNEIItemOutputTooltip(@Nonnull List<String> currentTip,
        @Nonnull FixedPositionedStack positionedStack) {
        currentTip = Objects.requireNonNull(super.handleNEIItemOutputTooltip(currentTip, positionedStack));
        addRecipeNote(currentTip);
        return currentTip;
    }

    private void addRecipeNote(@Nonnull List<String> currentTip) {
        String recipeNote = Objects.requireNonNull(StatCollector.translateToLocal(RECIPE_NOTE));
        if (!currentTip.contains(recipeNote)) currentTip.add(recipeNote);
    }
}
