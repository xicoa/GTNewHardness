package com.xicoa.gtnewhardness.common.materials;

import com.xicoa.gtnewhardness.common.enums.Materials;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.MaterialBuilder;
import gregtech.api.enums.TCAspects;
import gregtech.api.enums.TextureSet;

public class MaterialsRegister {

    private static final int MIXED_ACID_META_ID = 250;
    private static final int ELECTRON_PLASMA_META_ID = 251;

    public static void register() {

        Materials.mixedAcid = new MaterialBuilder().setName("MixedAcid")
            .setDefaultLocalName("Mixed Acid")
            .setChemicalFormula("H₅?₁₈")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0xffa0a0a0)
            .addCell()
            .addFluid()
            .addAspect(TCAspects.METALLUM, 2)
            .addAspect(TCAspects.VOLATUS, 1)
            .constructMaterial();

        Materials.electron = new MaterialBuilder().setName("ElectronPlasma")
            .setDefaultLocalName("Electron")
            .setChemicalFormula("e⁻")
            .setIconSet(TextureSet.SET_FLUID)
            .setARGB(0xffffffff)
            .addFluid()
            .addPlasma()
            .addCell()
            .addAspect(TCAspects.ELECTRUM, 3)
            .constructMaterial();

        registerGeneratedMaterial(Materials.mixedAcid, MIXED_ACID_META_ID);
        registerGeneratedMaterial(Materials.electron, ELECTRON_PLASMA_META_ID);
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
