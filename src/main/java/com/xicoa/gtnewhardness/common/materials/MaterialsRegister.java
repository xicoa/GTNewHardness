package com.xicoa.gtnewhardness.common.materials;

import com.xicoa.gtnewhardness.common.enums.Materials;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.MaterialBuilder;
import gregtech.api.enums.TCAspects;
import gregtech.api.enums.TextureSet;

public class MaterialsRegister {

    private static final int ELECTRON_PLASMA_META_ID = 251;
    private static final int IMPURE_EXCITED_HYDROGEN_PLASMA_META_ID = 252;
    private static final int EXCITED_HYDROGEN_PLASMA_META_ID = 253;
    private static final int MIXED_ACIDIC_PLASMA_META_ID = 254;

    public static void register() {

        Materials.electronPlasma = new MaterialBuilder().setName("electronPlasma")
            .setDefaultLocalName("Electron")
            .setChemicalFormula("e⁻")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0xffffffff)
            .addPlasma()
            .addCell()
            .addAspect(TCAspects.ELECTRUM, 16)
            .constructMaterial();

        Materials.impureExcitedHydrogenPlasma = new MaterialBuilder().setName("impureExcitedHydrogenPlasma")
            .setDefaultLocalName("Impure Excited Hydrogen")
            .setChemicalFormula("H⁺??")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0xff6666ff)
            .addPlasma()
            .addCell()
            .addAspect(TCAspects.ELECTRUM, 16)
            .constructMaterial();

        Materials.excitedHydrogenPlasma = new MaterialBuilder().setName("excitedHydrogenPlasma")
            .setDefaultLocalName("Excited Hydrogen")
            .setChemicalFormula("H⁺*")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0xff0044ff)
            .addPlasma()
            .addCell()
            .addAspect(TCAspects.ELECTRUM, 16)
            .constructMaterial();

        Materials.mixedAcidicPlasma = new MaterialBuilder().setName("mixedAcidicPlasma")
            .setDefaultLocalName("Mixed Acidic Plasma")
            .setChemicalFormula("??")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0xff839192)
            .addPlasma()
            .addCell()
            .addAspect(TCAspects.ELECTRUM, 16)
            .constructMaterial();

        registerGeneratedMaterial(Materials.electronPlasma, ELECTRON_PLASMA_META_ID);
        registerGeneratedMaterial(Materials.impureExcitedHydrogenPlasma, IMPURE_EXCITED_HYDROGEN_PLASMA_META_ID);
        registerGeneratedMaterial(Materials.excitedHydrogenPlasma, EXCITED_HYDROGEN_PLASMA_META_ID);
        registerGeneratedMaterial(Materials.mixedAcidicPlasma, MIXED_ACIDIC_PLASMA_META_ID);
    }

    private static void registerGeneratedMaterial(gregtech.api.enums.Materials material, int metaItemSubID) {
        if (metaItemSubID < 0 || metaItemSubID >= GregTechAPI.sGeneratedMaterials.length) {
            throw new IllegalArgumentException("Material meta item sub ID out of range: " + metaItemSubID);
        }

        gregtech.api.enums.Materials existingMaterial = GregTechAPI.sGeneratedMaterials[metaItemSubID];
        if (existingMaterial != null && existingMaterial != material) {
            throw new IllegalStateException(
                "Material meta item sub ID " + metaItemSubID + " is already used by " + existingMaterial);
        }
        material.mMetaItemSubID = metaItemSubID;
    }

    public static void showAvailableMaterialMetaItemSubIDs() {
        for (int i = 0; i < GregTechAPI.sGeneratedMaterials.length; i++) {
            if (GregTechAPI.sGeneratedMaterials[i] == null) {
                System.out.println("Material meta item sub ID " + i + " is available for use.");
            }
        }
    }
}
