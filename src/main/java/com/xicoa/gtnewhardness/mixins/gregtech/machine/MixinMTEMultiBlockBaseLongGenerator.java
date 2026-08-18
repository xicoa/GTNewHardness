package com.xicoa.gtnewhardness.mixins.gregtech.machine;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.xicoa.gtnewhardness.common.interfaces.ILongGenerator;

import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;

@Mixin(value = MTEMultiBlockBase.class, remap = false)
public abstract class MixinMTEMultiBlockBaseLongGenerator {

    @Shadow(remap = false)
    public int mEfficiency;

    @Shadow(remap = false)
    public abstract boolean addEnergyOutput(long aEU);

    @Inject(method = "onRunningTick", at = @At("HEAD"), cancellable = true, remap = false)
    private void gtnewhardness$runLongGenerator(ItemStack controllerStack, CallbackInfoReturnable<Boolean> cir) {
        Object machine = this;
        if (!(machine instanceof ILongGenerator)) {
            return;
        }
        ILongGenerator generator = (ILongGenerator) machine;
        if (!generator.gtnewhardness$shouldBypassNormalGeneration()) {
            return;
        }
        long rawOutput = generator.gtnewhardness$getLongEUOutput();
        long effectiveOutput = gtnewhardness$applyEfficiency(rawOutput, this.mEfficiency);
        this.addEnergyOutput(effectiveOutput);
        cir.setReturnValue(true);
    }

    @Unique
    private static long gtnewhardness$applyEfficiency(long output, int efficiency) {
        return output / 10_000L * efficiency + output % 10_000L * efficiency / 10_000L;
    }
}
