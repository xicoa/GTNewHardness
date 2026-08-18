package com.xicoa.gtnewhardness.client.iconContainers.blocks;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;

public final class NHBlockIconContainer {

    public static final IIconContainer MACHINE_CASING_TRANSCENDENT_STABLE = Textures.BlockIcons
        .custom("gtnewhardness:casings/MACHINE_CASING_TRANSCENDENT_STABLE"),
        MACHINE_CASING_SHOCKWAVE_REFLECTING = Textures.BlockIcons
            .custom("gtnewhardness:casings/MACHINE_CASING_SHOCKWAVE_REFLECTING"),
        MACHINE_CASING_WITH_COIL_SHOCKWAVE_REFLECTING = Textures.BlockIcons
            .custom("gtnewhardness:casings/MACHINE_CASING_WITH_COIL_SHOCKWAVE_REFLECTING"),
        MACHINE_CASING_OBLIQUE_SHOCKWAVE_REFLECTING = Textures.BlockIcons
            .custom("gtnewhardness:casings/MACHINE_CASING_OBLIQUE_SHOCKWAVE_REFLECTING"),
        MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_UNFILLED_TOP = Textures.BlockIcons
            .custom("gtnewhardness:casings/MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_UNFILLED/TOP"),
        MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_UNFILLED_SIDE = Textures.BlockIcons
            .custom("gtnewhardness:casings/MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_UNFILLED/SIDE"),
        MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_FILLED_TOP = Textures.BlockIcons
            .custom("gtnewhardness:casings/MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_FILLED/TOP"),
        MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_FILLED_SIDE = Textures.BlockIcons
            .custom("gtnewhardness:casings/MACHINE_CASING_MAGNETICALLY_LEVITATED_HIGH_DENSITY_EXPLOSIVE_FILLED/SIDE"),
        MACHINE_CASING_PLASMA_CONTAINMENT = Textures.BlockIcons
            .custom("gtnewhardness:casings/MACHINE_CASING_PLASMA_CONTAINMENT"),

        OVERLAY_FRONT_SEE = Textures.BlockIcons.custom("gtnewhardness:machines/controllerFaces/OVERLAY_FRONT_SEE"),
        OVERLAY_FRONT_SEE_ACTIVE = Textures.BlockIcons
            .custom("gtnewhardness:machines/controllerFaces/OVERLAY_FRONT_SEE_ACTIVE"),
        OVERLAY_FRONT_SEE_GLOW = Textures.BlockIcons
            .custom("gtnewhardness:machines/controllerFaces/OVERLAY_FRONT_SEE_GLOW"),
        OVERLAY_FRONT_SEE_ACTIVE_GLOW = Textures.BlockIcons
            .custom("gtnewhardness:machines/controllerFaces/OVERLAY_FRONT_SEE_ACTIVE_GLOW");

    public static void init() {}

    private NHBlockIconContainer() {}
}
