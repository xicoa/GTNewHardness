package com.xicoa.gtnewhardness.mixins.gregtech.machine;

import static com.gtnewhorizon.gtnhlib.util.numberformatting.NumberFormatUtil.formatNumber;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import com.xicoa.gtnewhardness.common.blocks.BlockCasings;
import com.xicoa.gtnewhardness.common.enums.Blocks;
import com.xicoa.gtnewhardness.common.enums.Materials;
import com.xicoa.gtnewhardness.common.interfaces.ILongGenerator;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.tileentities.machines.multi.MTELargeNeutralizationEngine;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MTELargeNeutralizationEngine.class, remap = false)
public abstract class MixinMTELargeNeutralizationEngine implements ILongGenerator {

    private final static FluidStack electronPlasma = Materials.electron.getPlasma(1000);

    @Shadow(remap = false)
    private float boosterEUBoost;

    @Shadow(remap = false)
    private int boosterBoostTicks;

    @Shadow(remap = false)
    private int fuelConsumption;

    @Shadow(remap = false)
    private int fuelValue;

    @Shadow(remap = false)
    public int residueCapacity;

    @Shadow(remap = false)
    private int structureTier;

    @Shadow(remap = false)
    public int toxicResidue;

    @Unique
    private long gtnewhardness$trueOutput;

    @Inject(method = "getStructureCasingTier", at = @At("HEAD"), cancellable = true, remap = false)
    private static void gtnewhardness$addExtraCasingTier(Block block, int meta, CallbackInfoReturnable<Integer> cir) {
        if (block == Blocks.TranscendentStableCasing && meta == 0) {
            cir.setReturnValue(4);
        }
    }

    @ModifyArg(method = "getStructureDefinition", at = @At(value = "INVOKE", target = "Lcom/gtnewhorizon/structurelib/structure/StructureUtility;"
            + "ofBlocksTiered("
            + "Lcom/gtnewhorizon/structurelib/structure/ITierConverter;"
            + "Ljava/util/List;"
            + "Ljava/lang/Object;"
            + "Ljava/util/function/BiConsumer;"
            + "Ljava/util/function/Function;"
            + ")Lcom/gtnewhorizon/structurelib/structure/IStructureElement;", remap = false), index = 1, remap = false)
    private List<Pair<Block, Integer>> gtnewhardness$addExtraCasingHint(List<Pair<Block, Integer>> original) {
        List<Pair<Block, Integer>> result = new ArrayList<>(original);
        result.add(Pair.of(Blocks.TranscendentStableCasing, 0));
        return result;
    }

    @Inject(method = "getCasingTextureId", at = @At("HEAD"), cancellable = true, remap = false)
    private void gtnewhardness$getTier4Texture(CallbackInfoReturnable<Integer> cir) {
        if (this.structureTier == 4) {
            cir.setReturnValue(BlockCasings.MACHINE_CASING_TRANSCENDENT_STABLE_TEXTURE_ID);
        }
    }

    @Inject(method = "getBaseResidueDecay", at = @At("HEAD"), cancellable = true, remap = false)
    private void gtnewhardness$addExtraCasingBaseResidueDecay(CallbackInfoReturnable<Integer> cir) {
        if (structureTier == 4) {
            cir.setReturnValue(1000);
        }
    }

    @Inject(method = "updateResidueCapacity", at = @At("HEAD"), cancellable = true, remap = false)
    public void gtnewhardness$addExtraCasingResidueCapacity(CallbackInfo ci) {
        if (structureTier == 4) {
            this.residueCapacity = Integer.MAX_VALUE;
            ci.cancel();
        }
    }

    @Inject(method = "useBooster", at = @At("HEAD"), cancellable = true, remap = false)
    private void gtnewhardness$useElectronPlasmaBooster(CallbackInfo ci) {
        MTELargeNeutralizationEngine engine = (MTELargeNeutralizationEngine) (Object) this;
        if (engine.depleteInput(electronPlasma)) {
            this.boosterEUBoost = 8.0F;
            this.boosterBoostTicks = SECONDS * 30;
            ci.cancel();
        }
    }

    @Unique
    private double gtnewhardness$getT4ResidueFactorWithResidue() {
        if ((float) toxicResidue / residueCapacity <= 0.15F) {
            return 1.0;
        } else {
            return Math.max(0, 1 + (0.15 * Integer.MAX_VALUE - (double) toxicResidue) / 3e9);
        }
    }

    @Inject(method = "getResidueRate", at = @At("HEAD"), cancellable = true, remap = false)
    private void gtnewhardness$getT4ResidueRateWithResidue(CallbackInfoReturnable<Float> cir) {
        if (this.structureTier != 4) {
            return;
        }
        cir.setReturnValue((float) (Math.pow(fuelValue, 0.6F) * 0.05F));
    }

    @Shadow
    protected abstract float getResidueRate();

    @Shadow
    protected abstract float getRandomIncreaseMultiplier();

    @Inject(method = "getResidueIncrease", at = @At("HEAD"), cancellable = true, remap = false)
    private void gtnewhardness$getT4ResidueIncreaseWithResidue(CallbackInfoReturnable<Integer> cir) {
        if (this.structureTier != 4) {
            return;
        }
        cir.setReturnValue((int) (getResidueRate() * fuelConsumption * gtnewhardness$getT4ResidueFactorWithResidue() * getRandomIncreaseMultiplier()));
    }

    @Unique
    private double gtnewhardness$getT4EUOutputFactorWithResidue() {
        if ((float) toxicResidue / residueCapacity <= 0.3F) {
            return 1.0;
        } else {
            return Math.max(0, 1 + (Math.log(0.3 * Integer.MAX_VALUE) - Math.log(toxicResidue)) / Math.log(5));
        }
    }

    @Unique
    private long gtnewhardness$getFuelEUOutputLong(int fluidAmount) {
        return (long) this.fuelValue * fluidAmount;
    }

    @Shadow(remap = false)
    public abstract long getMaximumEUOutput();

    @Unique
    private long gtnewhardness$getEUOutputLong(int fluidAmount) {
        return Math.min((long) (gtnewhardness$getFuelEUOutputLong(fluidAmount) * this.boosterEUBoost * gtnewhardness$getT4EUOutputFactorWithResidue()), this.getMaximumEUOutput());
    }

    @Override
    public boolean gtnewhardness$shouldBypassNormalGeneration() {
        return this.structureTier == 4;
    }

    @Override
    public long gtnewhardness$getLongEUOutput() {
        return this.gtnewhardness$trueOutput;
    }

    @Inject(method = "getEUOutput", at = @At("RETURN"), cancellable = true, remap = false)
    private void gtnewhardness$captureT4LongOutput(int fluidAmount, CallbackInfoReturnable<Integer> cir) {
        if (this.structureTier != 4) {
            return;
        }
        this.gtnewhardness$trueOutput = gtnewhardness$getEUOutputLong(fluidAmount);
        cir.setReturnValue((int) Math.min(Integer.MAX_VALUE, this.gtnewhardness$trueOutput));
    }

    @Redirect(method = "createTooltip", at = @At(value = "INVOKE", target = "Lgregtech/api/util/MultiblockTooltipBuilder;addSeparator()Lgregtech/api/util/MultiblockTooltipBuilder;", ordinal = 1, remap = false), remap = false)
    protected MultiblockTooltipBuilder gtnewhardness$insertElectronTooltip(MultiblockTooltipBuilder tt) {
        return tt.addInfo(StatCollector.translateToLocalFormatted(
                "gt.multiblock.NeutralizationEngine.alkali_text",
                "Electron Plasma",
                800,
                "2000L")).addSeparator();
    }

    @Redirect(method = "createTooltip", at = @At(value = "INVOKE", target = "Lgregtech/api/util/MultiblockTooltipBuilder;addSeparator()Lgregtech/api/util/MultiblockTooltipBuilder;", ordinal = 2, remap = false), remap = false)
    protected MultiblockTooltipBuilder gtnewhardness$insertT4MechanismChange(MultiblockTooltipBuilder tt) {
        return tt.addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.NeutralizationEngine.mechanism.t4info")).addSeparator();
    }

    @ModifyArg(method = "createTooltip", at = @At(value = "INVOKE", target = "Lgregtech/api/util/MultiblockTooltipBuilder;"
            + "addInfo(Ljava/lang/String;)"
            + "Lgregtech/api/util/MultiblockTooltipBuilder;", ordinal = 16, remap = false), index = 0, require = 1, remap = false)
    protected String gtnewhardness$changeStructureTiers(String originalText) {
        return StatCollector.translateToLocal("gtnewhardness.multiblock.NeutralizationEngine.structure_tiers");
    }

    @Redirect(method = "createTooltip", at = @At(value = "INVOKE", target = "Lgregtech/api/util/MultiblockTooltipBuilder;addSupportAny()Lgregtech/api/util/MultiblockTooltipBuilder;", ordinal = 0, remap = false), remap = false)
    protected MultiblockTooltipBuilder gtnewhardness$insertT4Tooltip(MultiblockTooltipBuilder tt) {
        return tt.addInfo(
                StatCollector.translateToLocalFormatted(
                    "gt.multiblock.NeutralizationEngine.tier_info",
                    4,
                    "Transcendent Stable Machine Casing",
                    1000,
                    formatNumber(Integer.MAX_VALUE)))

                .addSupportAny()
                .addSeparator()
                .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.NeutralizationEngine.t4detail.1"))
                .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.NeutralizationEngine.t4detail.2"))
                .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.NeutralizationEngine.t4detail.3"))
                .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.NeutralizationEngine.t4detail.4"))
                .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.NeutralizationEngine.t4detail.5"))
                .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.NeutralizationEngine.t4detail.6"));
    }
}

