package com.myname.mymodid.common.enums;

import static bartworks.util.BWUtil.subscriptNumbers;
import static gregtech.api.enums.Materials.*;

import bartworks.system.material.Werkstoff;
import gregtech.api.enums.TextureSet;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialBuilder;
import gregtech.api.enums.TextureSet;
import gregtech.api.enums.TCAspects;
import gregtech.api.objects.MaterialStack;

public class Fluids implements Runnable {

    public static Materials mixedAcid;


    protected static final int OffsetId = 14500;



    // public static final Werkstoff mixedAcid = new Werkstoff(
    //     new short[] { 0x79, 0xd8, 0x55 },
    //     "Mixed Acid",
    //     subscriptNumbers("H5?18"),
    //     new Werkstoff.Stats(),
    //     Werkstoff.Types.MIXTURE,
    //     new Werkstoff.GenerationFeatures().disable()
    //         .addCells(),
    //     OffsetId + 0,
    //     TextureSet.SET_FLUID);

    public static final Werkstoff electronPlasma = new Werkstoff(
        new short[] { 0x100, 0x100, 0x100 },
        "Electron Plasma",
        subscriptNumbers("e⁻"),
        new Werkstoff.Stats(),
        Werkstoff.Types.MIXTURE,
        new Werkstoff.GenerationFeatures().disable()
                .addCells(),
        OffsetId + 1,
        TextureSet.SET_FLUID);

    @Override
    public void run() {

    }
}
