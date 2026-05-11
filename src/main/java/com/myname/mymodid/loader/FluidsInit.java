package com.myname.mymodid.loader;

import com.myname.mymodid.common.enums.Fluids;

import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialBuilder;
import gregtech.api.enums.TextureSet;
import gregtech.api.enums.TCAspects;
import gregtech.api.objects.MaterialStack;
import bartworks.API.WerkstoffAdderRegistry;

public class FluidsInit {

    public static void init() {
        // WerkstoffAdderRegistry.addWerkstoffAdder(new Fluids());
        // Fluids.mixedAcid = loadMixedAcid();
    }

    // public static Materials loadMixedAcid() {
    //     return new MaterialBuilder().setName("MixedAcid")
    //             .setDefaultLocalName("Mixed Acid")
    //             .setChemicalFormula("H₅?₁₈")
    //             .setIconSet(TextureSet.SET_FLUID)
    //             .setARGB(0x00a0a0a0)
    //             .addCell()
    //             .addFluid()
    //             .addAspect(TCAspects.METALLUM, 2)
    //             .addAspect(TCAspects.VOLATUS, 1)
    //             .constructMaterial();
    // }
}
