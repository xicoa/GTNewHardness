package com.xicoa.gtnewhardness.common.blocks;

import java.util.List;

import com.xicoa.gtnewhardness.client.iconContainers.blocks.NHBlockIconContainer;
import com.xicoa.gtnewhardness.common.enums.ItemList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockCasingsAbstract;
import gregtech.common.blocks.ItemCasings;
import gregtech.common.blocks.MaterialCasings;

public class BlockCasings extends BlockCasingsAbstract {

    public static final byte TEXTURE_PAGE = 105;
    public static final byte MAX_META = 5;
    public static final byte TEXTURE_START_INDEX = 0;

    public static final int MACHINE_CASING_TRANSCENDENT_STABLE_TEXTURE_ID = GTUtility.getTextureId(TEXTURE_PAGE, TEXTURE_START_INDEX, (byte) 0);


    public BlockCasings() {
        super(ItemCasings.class, "gtnewhardness.blockcasings", MaterialCasings.INSTANCE, MAX_META + 1);

        ItemList.MACHINE_CASING_TRANSCENDENT_STABLE.set(new ItemStack(this, 1, 0));

    }

    // @Override
    // public int getTextureIndex(int aMeta) {
    //     return GTUtility.getTextureId(TEXTURE_PAGE, TEXTURE_START_INDEX, (byte) aMeta);
    // }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int ordinalSide, int aMeta) {
        return switch (aMeta) {
            case 0 -> NHBlockIconContainer.MACHINE_CASING_TRANSCENDENT_STABLE.getIcon();
            case 1 -> NHBlockIconContainer.MACHINE_CASING_SHOCKWAVE_REFLECTING.getIcon();
            case 2 -> NHBlockIconContainer.MACHINE_CASING_MAGNETIC_CONFINING_SHOCKWAVE_REFLECTING.getIcon();
            case 3 -> NHBlockIconContainer.MACHINE_CASING_OBLIQUE_SHOCKWAVE_REFLECTING.getIcon();
            case 4 -> NHBlockIconContainer.MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_FILLED.getIcon();
            case 5 -> NHBlockIconContainer.MACHINE_CASING_PLASMA_CONFINING.getIcon();
            default -> Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
        };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTab, List<ItemStack> list) {
        for (int i = 0; i <= MAX_META; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
