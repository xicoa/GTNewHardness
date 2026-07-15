package com.xicoa.gtnewhardness.mixins.gregtech.machine;

import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import com.xicoa.gtnewhardness.common.enums.Materials;

import net.minecraft.util.StatCollector;

import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.tileentities.machines.multi.MTELargeNeutralizationEngine;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MTELargeNeutralizationEngine.class, remap = false)
public abstract class MixinMTELargeNeutralizationEngine {

    @Shadow(remap = false)
    private float boosterEUBoost;

    @Shadow(remap = false)
    private int boosterBoostTicks;

    @Inject(method = "useBooster", at = @At("HEAD"), cancellable = true, remap = false)
    private void gtnewhardness$useElectronPlasmaBooster(CallbackInfo ci) {
        MTELargeNeutralizationEngine engine = (MTELargeNeutralizationEngine) (Object) this;
        if (engine.depleteInput(Materials.electron.getPlasma(1000))) {
            this.boosterEUBoost = 10.0F;
            this.boosterBoostTicks = SECONDS * 20;
            ci.cancel();
        }
    }

    @Redirect(method = "createTooltip", at = @At(value = "INVOKE", target = "Lgregtech/api/util/MultiblockTooltipBuilder;addSeparator()Lgregtech/api/util/MultiblockTooltipBuilder;", ordinal = 1, remap = false), remap = false)
    protected MultiblockTooltipBuilder gtnewhardness$insertElectronTooltip(MultiblockTooltipBuilder tt) {
        return tt.addInfo(StatCollector.translateToLocalFormatted(
                "gt.multiblock.NeutralizationEngine.alkali_text",
                "Electron Plasma",
                1000,
                "3000L")).addSeparator();
    }
}

