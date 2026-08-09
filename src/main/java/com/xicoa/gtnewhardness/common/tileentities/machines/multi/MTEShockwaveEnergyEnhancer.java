package com.xicoa.gtnewhardness.common.tileentities.machines.multi;

import static com.xicoa.gtnewhardness.client.iconContainers.blocks.NHBlockIconContainer.OVERLAY_FRONT_SEE;
import static com.xicoa.gtnewhardness.client.iconContainers.blocks.NHBlockIconContainer.OVERLAY_FRONT_SEE_ACTIVE;
import static com.xicoa.gtnewhardness.client.iconContainers.blocks.NHBlockIconContainer.OVERLAY_FRONT_SEE_GLOW;
import static com.xicoa.gtnewhardness.client.iconContainers.blocks.NHBlockIconContainer.OVERLAY_FRONT_SEE_ACTIVE_GLOW;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.HatchElement.Energy;
import static gregtech.api.enums.HatchElement.ExoticEnergy;
import static gregtech.api.enums.HatchElement.InputBus;
import static gregtech.api.enums.HatchElement.InputHatch;
import static gregtech.api.enums.HatchElement.Maintenance;
import static gregtech.api.enums.HatchElement.Muffler;
import static gregtech.api.enums.HatchElement.OutputBus;
import static gregtech.api.enums.HatchElement.OutputHatch;
import static gregtech.api.enums.Textures.BlockIcons.getCasingTextureForId;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;

import com.xicoa.gtnewhardness.common.blocks.BlockCasings;
import com.xicoa.gtnewhardness.common.enums.Blocks;
import com.xicoa.gtnewhardness.common.enums.ItemList;
import com.xicoa.gtnewhardness.common.recipeMaps.NHRecipeMaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.apache.commons.math3.distribution.PoissonDistribution;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.GregTechAPI;
import gregtech.api.casing.Casings;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.enums.TierEU;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.ICasingTextureProvider;
import gregtech.api.interfaces.tileentity.IGregTechDeviceInformation;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.GregTechTileClientEvents;
import gregtech.api.metatileentity.implementations.MTEExtendedPowerMultiBlockBase;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchDynamo;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.structure.error.StructureError;
import gregtech.api.structure.error.StructureErrorRegistry;
import gregtech.api.util.GTUtility;
import gregtech.api.util.IGTHatchAdder;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.gui.modularui.multiblock.MTELargeNeutralizationEngineGui;
import gregtech.common.gui.modularui.multiblock.base.MTEMultiBlockBaseGui;
import gregtech.common.tileentities.machines.IDualInputHatch;

public class MTEShockwaveEnergyEnhancer extends MTEExtendedPowerMultiBlockBase<MTEShockwaveEnergyEnhancer>
    implements ISurvivalConstructable, ICasingTextureProvider {

    private static final String STRUCTURE_PIECE_MAIN = "main";

    private static final String NBT_LAMBDA = "shockwaveLambda";
    private static final String NBT_MEASUREMENT_COUNT = "shockwaveMeasurementCount";
    private static final String NBT_PRODUCTION_RUNNING = "shockwaveProductionRunning";

    private static final int MEASUREMENT_TOLUENE_AMOUNT = 1000;
    private static final int PRODUCTION_DURATION = 30 * SECONDS;
    private static final int POLLUTION_PER_SECOND = 10_000;
    private static final double LAMBDA_MIN = 20;
    private static final double LAMBDA_MAX = 200;

    private static final ItemStack explosiveCasing = ItemList.MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_FILLED.get(1);
    private static final ItemStack plasmaCasing = ItemList.MACHINE_CASING_PLASMA_CONTAINMENT.get(1);
    private static final String outputPlasmaName = Materials.Hydrogen.getPlasma(1).getLocalizedName();

    private int mCasing;
    private double lambda;
    private int measurementCount;
    private boolean productionRunning;

    private static final int HORIZONTAL_OFF_SET = 7;
    private static final int VERTICAL_OFF_SET = 7;
    private static final int DEPTH_OFF_SET = 0;

    public MTEShockwaveEnergyEnhancer(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTEShockwaveEnergyEnhancer(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTEShockwaveEnergyEnhancer(this.mName);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setDouble(NBT_LAMBDA, lambda);
        aNBT.setInteger(NBT_MEASUREMENT_COUNT, measurementCount);
        aNBT.setBoolean(NBT_PRODUCTION_RUNNING, productionRunning);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        lambda = aNBT.getDouble(NBT_LAMBDA);
        measurementCount = Math.max(0, aNBT.getInteger(NBT_MEASUREMENT_COUNT));
        productionRunning = aNBT.getBoolean(NBT_PRODUCTION_RUNNING);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return NHRecipeMaps.SHOCKWAVE_ENERGY_ENHANCER_RECIPES;
    }

    @Nonnull
    @Override
    public CheckRecipeResult checkProcessing() {
        FluidStack requiredToluene = Materials.Toluene.getFluid(MEASUREMENT_TOLUENE_AMOUNT);

        if (depleteInput(requiredToluene, true)) {
            return startMeasurement(requiredToluene);
        }

        return startProduction(getStoredInputs());
    }

    @Nonnull
    private CheckRecipeResult startMeasurement(FluidStack requiredToluene) {
        ensureLambda();

        int measurementValue = samplePoisson(lambda);
        int durationTicks = samplePoisson(lambda);
        int returnedToluene = MEASUREMENT_TOLUENE_AMOUNT - measurementValue;
        FluidStack[] outputs = new FluidStack[] { Materials.Toluene.getFluid(returnedToluene) };

        if (!canOutputAll(new ItemStack[0], outputs)) {
            return Objects.requireNonNull(CheckRecipeResultRegistry.FLUID_OUTPUT_FULL);
        }

        if (!depleteInput(requiredToluene)) return CheckRecipeResultRegistry.NO_RECIPE;
        measurementCount = measurementCount + 1;
        setupRecipe(TierEU.RECIPE_ZPM, durationTicks, new ItemStack[0], outputs, false);
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    @Nonnull
    private CheckRecipeResult startProduction(List<ItemStack> inputs) {
        long explosiveAmount = countMatchingItems(inputs, explosiveCasing);
        long plasmaBlockAmount = countMatchingItems(inputs, plasmaCasing);

        if (explosiveAmount <= 0 || plasmaBlockAmount <= 0 || measurementCount <= 0) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        ensureLambda();
        long outputAmount = Math.max(0, calculateProductionOutput(explosiveAmount, plasmaBlockAmount));
        FluidStack[] outputs = splitPlasmaOutputs(outputAmount);

        if (!canOutputAll(new ItemStack[0], outputs)) {
            return Objects.requireNonNull(CheckRecipeResultRegistry.FLUID_OUTPUT_FULL);
        }

        consumeMatchingItems(inputs, explosiveCasing, explosiveAmount);
        consumeMatchingItems(inputs, plasmaCasing, plasmaBlockAmount);
        double timeFactor = Math.log(plasmaBlockAmount) / 2 + 1;
        setupRecipe((int) Math.ceil(plasmaBlockAmount * TierEU.RECIPE_ZPM / timeFactor), (int) Math.ceil(PRODUCTION_DURATION * timeFactor), new ItemStack[0], outputs, true);
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    protected long calculateProductionOutput(long explosiveAmount, long plasmaBlockAmount) {
        double bias = Math.abs((double) explosiveAmount / plasmaBlockAmount - lambda);
        double ratio = 1 / (1 + 15 * Math.pow(bias, 2) / Math.pow(lambda, 0.4) / Math.pow(measurementCount, 0.8));
        return Math.round(plasmaBlockAmount * ratio);
    }

    private void setupRecipe(long eut, int duration, ItemStack[] outputs, FluidStack[] fluids, boolean isProduction) {
        lEUt = -Math.max(0, eut);
        mMaxProgresstime = duration;
        mEfficiency = 10_000;
        mEfficiencyIncrease = 10_000;
        mOutputItems = outputs;
        mOutputFluids = fluids;
        productionRunning = isProduction;
        updateSlots();
    }

    public long getLongEnergyUsage() {
        return lEUt < 0 ? getActualEnergyUsage() : 0;
    }

    @Override
    public int getPollutionPerSecond(ItemStack stack) {
        return POLLUTION_PER_SECOND;
    }

    @Override
    protected void outputAfterRecipe() {
        super.outputAfterRecipe();
        if (!productionRunning) return;

        productionRunning = false;
        measurementCount = 0;
        generateLambda();
    }

    private void ensureLambda() {
        if (lambda > LAMBDA_MIN && lambda < LAMBDA_MAX) return;
        generateLambda();
    }

    private void generateLambda() {
        do {
            lambda = LAMBDA_MIN + ThreadLocalRandom.current().nextDouble(LAMBDA_MAX - LAMBDA_MIN);
        } while (lambda <= LAMBDA_MIN || lambda >= LAMBDA_MAX);
    }

    private int samplePoisson(double mean) {
        int sample = new PoissonDistribution(mean).sample();
        if (sample == 0) {
            sample = 1;
        } else if (sample >= MEASUREMENT_TOLUENE_AMOUNT) {
            sample = MEASUREMENT_TOLUENE_AMOUNT - 1;
        }
        return sample;
    }

    private long countMatchingItems(List<ItemStack> inputs, ItemStack template) {
        long amount = 0;
        for (ItemStack input : inputs) {
            if (GTUtility.areStacksEqual(input, template)) amount += input.stackSize;
        }
        return amount;
    }

    private void consumeMatchingItems(List<ItemStack> inputs, ItemStack template, long amount) {
        long remaining = amount;
        for (ItemStack input : inputs) {
            if (remaining <= 0) break;
            if (!GTUtility.areStacksEqual(input, template)) continue;

            int consumed = (int) Math.min(remaining, input.stackSize);
            input.stackSize -= consumed;
            remaining -= consumed;
        }
    }

    private FluidStack[] splitPlasmaOutputs(long amount) {
        if (amount <= 0) return new FluidStack[0];

        long requiredStacks = (amount - 1) / Integer.MAX_VALUE + 1;
        if (requiredStacks > Integer.MAX_VALUE) throw new IllegalArgumentException("Too many output stacks");

        FluidStack[] outputs = new FluidStack[(int) requiredStacks];
        long remaining = amount;
        for (int i = 0; i < outputs.length; i++) {
            int fluidAmount = (int) Math.min(Integer.MAX_VALUE, remaining);
            outputs[i] = Materials.Hydrogen.getPlasma(fluidAmount);
            remaining -= fluidAmount;
        }
        return outputs;
    }

    private static IStructureDefinition<MTEShockwaveEnergyEnhancer> STRUCTURE_DEFINITION = null;

    @Override
    public IStructureDefinition<MTEShockwaveEnergyEnhancer> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<MTEShockwaveEnergyEnhancer>builder()
                .addShape(
                    STRUCTURE_PIECE_MAIN,
                    transpose(
                        new String[][] { { "               ", "               ", "               ", "               ", "               ", "      CCC      ", "     CCDCC     ", "     CDDDC     ", "     CCDCC     ", "      CCC      ",  "               ", "               ", "               ", "               ", "               " },
                            { "               ", "               ", "               ", "     CCCCC     ", "    CCCCCCC    ", "   CCC   CCC   ", "   CC     CC   ", "   CC     CC   ", "   CC     CC   ", "   CCC   CCC   ",  "    CCCCCCC    ", "     CCCCC     ", "               ", "               ", "               " },
                            { "               ", "               ", "     EEEEE     ", "    E     E    ", "   E       E   ", "  E         E  ", "  E         E  ", "  E         E  ", "  E         E  ", "  E         E  ",  "   E       E   ", "    E     E    ", "     EEEEE     ", "               ", "               " },
                            { "               ", "     CCCCC     ", "    E     E    ", "   E       E   ", "  E         E  ", " C           C ", " C           C ", " C           C ", " C           C ", " C           C ",  "  E         E  ", "   E       E   ", "    E     E    ", "     CCCCC     ", "               " },
                            { "               ", "    CCCCCCC    ", "   E       E   ", "  E         E  ", " C           C ", " C           C ", " C           C ", " C           C ", " C           C ", " C           C ",  " C           C ", "  E         E  ", "   E       E   ", "    CCCCCCC    ", "               " },
                            { "      CCC      ", "   CCC   CCC   ", "  E         E  ", " C           C ", " C           C ", " C           C ", "C             C", "C             C", "C             C", " C           C ",  " C           C ", " C           C ", "  E         E  ", "   CCC   CCC   ", "      CCC      " },
                            { "     CCDCC     ", "   CC     CC   ", "  E         E  ", " C           C ", " C           C ", "C             C", "C      F      C", "D     FFF     D", "C      F      C", "C             C",  " C           C ", " C           C ", "  E         E  ", "   CC     CC   ", "     CCDCC     " },
                            { "     CD~DC     ", "   CC     CC   ", "  E         E  ", " C           C ", " C           C ", "C             C", "D     FFF     D", "D     FGF     D", "D     FFF     D", "C             C",  " C           C ", " C           C ", "  E         E  ", "   CC     CC   ", "     CDDDC     " },
                            { "     CCDCC     ", "   CC     CC   ", "  E         E  ", " C           C ", " C           C ", "C             C", "C      F      C", "D     FFF     D", "C      F      C", "C             C",  " C           C ", " C           C ", "  E         E  ", "   CC     CC   ", "     CCDCC     " },
                            { "      CCC      ", "   CCC   CCC   ", "  E         E  ", " C           C ", " C           C ", " C           C ", "C             C", "C             C", "C             C", " C           C ",  " C           C ", " C           C ", "  E         E  ", "   CCC   CCC   ", "      CCC      " },
                            { "               ", "    CCCCCCC    ", "   E       E   ", "  E         E  ", " C           C ", " C           C ", " C           C ", " C           C ", " C           C ", " C           C ",  " C           C ", "  E         E  ", "   E       E   ", "    CCCCCCC    ", "               " },
                            { "               ", "     CCCCC     ", "    E     E    ", "   E       E   ", "  E         E  ", " C           C ", " C           C ", " C           C ", " C           C ", " C           C ",  "  E         E  ", "   E       E   ", "    E     E    ", "     CCCCC     ", "               " },
                            { "               ", "               ", "     EEEEE     ", "    E     E    ", "   E       E   ", "  E         E  ", "  E         E  ", "  E         E  ", "  E         E  ", "  E         E  ",  "   E       E   ", "    E     E    ", "     EEEEE     ", "               ", "               " },
                            { "               ", "               ", "               ", "     CCCCC     ", "    CCCCCCC    ", "   CCC   CCC   ", "   CC     CC   ", "   CC     CC   ", "   CC     CC   ", "   CCC   CCC   ",  "    CCCCCCC    ", "     CCCCC     ", "               ", "               ", "               " },
                            { "               ", "               ", "               ", "               ", "               ", "      CCC      ", "     CCDCC     ", "     CDDDC     ", "     CCDCC     ", "      CCC      ",  "               ", "               ", "               ", "               ", "               " } }))
                .addElement(
                    'C',
                    ofChain(
                        buildHatchAdder(MTEShockwaveEnergyEnhancer.class)
                            .atLeast(
                                Energy.or(ExoticEnergy),
                                Maintenance,
                                InputBus,
                                InputHatch,
                                OutputBus,
                                OutputHatch,
                                Muffler)
                            .casingIndex(getHatchTextureId())
                            .hint(1)
                            .build(),
                        onElementPass(
                            m -> m.mCasing++,
                            ofBlock(Blocks.BlockCasings, 1)
                        )))
                .addElement('D', ofBlock(Blocks.BlockCasings, 2))
                .addElement('E', ofBlock(Blocks.BlockCasings, 3))
                .addElement('F', ofBlock(Blocks.BlockCasings, 5))
                .addElement('G', ofBlock(Blocks.BlockCasings, 6))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("Shockwave Energy Enhancer, SEE")
            .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.description"))
            .addSeparator()
            .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.automation1"))
            .addInfo(
                StatCollector.translateToLocalFormatted(
                    "gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.random_mechanism1",
                    LAMBDA_MIN,
                    LAMBDA_MAX))
            .addInfo(
                StatCollector.translateToLocalFormatted(
                    "gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.measurement1",
                    (int) MEASUREMENT_TOLUENE_AMOUNT))
            .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.measurement2"))
            .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.measurement3"))
            .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.measurement4"))
            .addInfo(
                StatCollector.translateToLocalFormatted(
                    "gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.production1",
                    explosiveCasing.getUnlocalizedName(),
                    plasmaCasing.getUnlocalizedName()))
            .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.production2"))
            .addInfo(StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.production3"))
            .addInfo(
                StatCollector.translateToLocalFormatted(
                    "gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.production4",
                    TierEU.RECIPE_ZPM))
            .addInfo(
                StatCollector.translateToLocalFormatted(
                    "gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.production5",
                    outputPlasmaName))
            .addInfo(
                StatCollector.translateToLocalFormatted(
                    "gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.poission1",
                    (int) MEASUREMENT_TOLUENE_AMOUNT))
            .addPollutionAmount(getPollutionPerSecond(null))
            .addSupportAny()
            .beginStructureBlock(15, 15, 15, false)
            .addController(
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.controller"))
            .addCasing(
                "300+",
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.casing.shockwave"),
                false)
            .addCasing(
                "29",
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.casing.coil"),
                false)
            .addCasing(
                "116",
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.casing.oblique"),
                false)
            .addCasing(
                "18",
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.casing.explosive"),
                false)
            .addCasing(
                "1",
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.casing.plasma"),
                false)
            .addEnergyHatch(
                "1+",
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.hatch_position"),
                1)
            .addMaintenanceHatch(
                "1+",
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.hatch_position"),
                1)
            .addInputBus(
                "1+",
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.hatch_position"),
                1)
            .addInputHatch(
                "1+",
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.hatch_position"),
                1)
            .addOutputBus(
                "1+",
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.hatch_position"),
                1)
            .addOutputHatch(
                "1+",
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.hatch_position"),
                1)
            .addMufflerHatch(
                "1+",
                StatCollector.translateToLocal("gtnewhardness.multiblock.MTEShockwaveEnergyEnhancer.structure.hatch_position"),
                1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public void checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack, List<StructureError> errors) {
        mCasing = 0;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET, errors))
            return;

        checkCasingMin(errors, mCasing, 300);
        checkHasAnyEnergy(errors);
        checkHasMaintenanceHatch(errors);
        checkHasInputHatch(errors);
        checkHasInputBus(errors);
        checkHasOutputHatch(errors);
        checkHasOutputBus(errors);
        checkHasMufflerHatch(errors);

        getBaseMetaTileEntity().sendBlockEvent(GregTechTileClientEvents.CHANGE_CUSTOM_DATA, getUpdateData());
        updateHatchTexture();
    }

    public void updateHatchTexture() {
        for (IDualInputHatch h : mDualInputHatches)
            h.updateTexture(getHatchTextureId());
        for (MTEHatch h : mInputHatches)
            h.updateTexture(getHatchTextureId());
        for (MTEHatch h : mInputBusses)
            h.updateTexture(getHatchTextureId());
        for (MTEHatch h: mOutputHatches)
            h.updateTexture(getHatchTextureId());
        for (MTEHatch h : mOutputBusses)
            h.updateTexture(getHatchTextureId());
        for (MTEHatch h : mMaintenanceHatches)
            h.updateTexture(getHatchTextureId());
        for (MTEHatch h : mEnergyHatches)
            h.updateTexture(getHatchTextureId());
        for (MTEHatch h : mExoticEnergyHatches)
            h.updateTexture(getHatchTextureId());
        for (MTEHatch h : mMufflerHatches)
            h.updateTexture(getHatchTextureId());
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
            int colorIndex, boolean aActive, boolean redstoneLevel) {
        return Textures.BlockIcons.createTextureWithCasing(
                this,
                side,
                aFacing,
                aActive,
                OVERLAY_FRONT_SEE,
                OVERLAY_FRONT_SEE_GLOW,
                OVERLAY_FRONT_SEE_ACTIVE,
                OVERLAY_FRONT_SEE_ACTIVE_GLOW);
    }

    @Override
    public ITexture getCasingTexture() {
        return getCasingTextureForId(BlockCasings.MACHINE_CASING_WITH_COIL_SHOCKWAVE_REFLECTING_TEXTURE_ID);
    }

    private int getHatchTextureId() {
        return BlockCasings.MACHINE_CASING_SHOCKWAVE_REFLECTING_TEXTURE_ID;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivalBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            HORIZONTAL_OFF_SET,
            VERTICAL_OFF_SET,
            DEPTH_OFF_SET,
            elementBudget,
            env,
            false,
            true);
    }

}
