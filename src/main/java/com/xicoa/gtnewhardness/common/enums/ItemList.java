package com.xicoa.gtnewhardness.common.enums;

import static gregtech.api.enums.GTValues.NI;
import static gregtech.api.util.GTRecipeBuilder.WILDCARD;

import java.util.List;
import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.Fluid;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.ImmutableList;

import gregtech.GTMod;
import gregtech.api.interfaces.IItemContainer;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.util.GTLanguageManager;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

public enum ItemList {

    MACHINE_CASING_TRANSCENDENT_STABLE,
    MACHINE_CASING_SHOCKWAVE_REFLECTING,
    MACHINE_CASING_WITH_COIL_SHOCKWAVE_REFLECTING,
    MACHINE_CASING_OBLIQUE_SHOCKWAVE_REFLECTING,
    MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_UNFILLED,
    MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_FILLED,
    MACHINE_CASING_PLASMA_CONTAINMENT,


    MACHINE_MULTI_SHOCKWAVE_ENERGY_ENHANCER;

    private boolean mHasNotBeenSet;
    private boolean mDeprecated;
    private boolean mWarned;

    private ItemStack mStack;

    ItemList() {
        mHasNotBeenSet = true;
    }

    ItemList(boolean aDeprecated) {
        if (aDeprecated) {
            mDeprecated = true;
            mHasNotBeenSet = true;
        }
    }

    public Item getItem() {
        sanityCheck();
        if (GTUtility.isStackInvalid(mStack))
            return null;
        return mStack.getItem();
    }

    public Block getBlock() {
        sanityCheck();
        return Block.getBlockFromItem(getItem());
    }

    public ItemStack get(int aAmount, Object... aReplacements) {
        sanityCheck();
        return GTUtility.copyAmountUnsafe(aAmount, mStack);
    }

    public int getMeta() {
        return mStack.getItemDamage();
    }

    public ItemList set(Item aItem) {
        mHasNotBeenSet = false;
        if (aItem == null)
            return this;
        ItemStack aStack = new ItemStack(aItem, 1, 0);
        mStack = GTUtility.copyAmountUnsafe(1, aStack);
        return this;
    }

    public ItemList set(ItemStack aStack) {
        if (aStack != null) {
            mHasNotBeenSet = false;
            mStack = GTUtility.copyAmountUnsafe(1, aStack);
            // workaround: add machines to the creative tab
            // if (Block.getBlockFromItem(aStack.getItem()) == GregTechAPI.sBlockMachines) {
            //     TstCreativeTabs.registerMachineToCreativeTab(mStack);
            // }
        }
        return this;
    }

    public ItemList set(IMetaTileEntity metaTileEntity) {
        if (metaTileEntity == null)
            throw new IllegalArgumentException();
        return set(metaTileEntity.getStackForm(1L));
    }

    public boolean hasBeenSet() {
        return !mHasNotBeenSet;
    }

    /**
     * Returns the internal stack. This method is unsafe. It's here only for quick
     * operations. DON'T CHANGE THE RETURNED
     * VALUE!
     */
    public ItemStack getInternalStack_unsafe() {
        return mStack;
    }

    private void sanityCheck() {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (mDeprecated && !mWarned) {
            new Exception(this + " is now deprecated").printStackTrace(GTLog.err);
            // warn only once
            mWarned = true;
        }
    }

    public boolean equal(@Nullable ItemStack itemStack) {
        if (itemStack == null)
            return false;
        if (mHasNotBeenSet)
            return false;
        if (this.mStack == itemStack)
            return true;
        return this.mStack.isItemEqual(itemStack);
    }
}
