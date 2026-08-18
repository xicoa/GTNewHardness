package com.xicoa.gtnewhardness.loaders;

import com.xicoa.gtnewhardness.common.enums.ItemList;
import com.xicoa.gtnewhardness.common.tileentities.machines.multi.MTEShockwaveEnergyEnhancer;

public class MachineLoader {

    public static void load() {
        ItemList.MACHINE_MULTI_SHOCKWAVE_ENERGY_ENHANCER.set(
            new MTEShockwaveEnergyEnhancer(
                30000,
                "multimachine.shockwave_energy_enhancer",
                "Shockwave Energy Enhancer"));
    }
}
