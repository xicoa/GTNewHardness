package com.xicoa.gtnewhardness.loaders;

import com.xicoa.gtnewhardness.GTNewHardness;
import com.xicoa.gtnewhardness.client.iconContainers.blocks.NHBlockIconContainer;
import com.xicoa.gtnewhardness.common.blocks.BlocksRegister;
import com.xicoa.gtnewhardness.common.blocks.BlockCasings;

import gregtech.api.util.GTUtility;

public class BlockLoader {

    public static void load() {
        // Textures.BlockIcons.custom(...) must run before GregTech's block icon stitching callback.
        NHBlockIconContainer.init();

        if (!GTUtility.addTexturePage(BlockCasings.TEXTURE_PAGE)) {
            GTNewHardness.LOG.warn(
                "GregTech casing texture page {} was already allocated; texture IDs may conflict with another mod",
                BlockCasings.TEXTURE_PAGE);
        }

        BlocksRegister.register();
    }
}
