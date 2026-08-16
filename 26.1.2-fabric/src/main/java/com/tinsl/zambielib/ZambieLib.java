package com.tinsl.zambielib;

import net.fabricmc.api.ModInitializer;

/**
 * Shared internals for ZambieD's mods. This mod does nothing by itself; it
 * exists so the rest of the family can share one copy of the code instead of
 * bundling it repeatedly.
 */
public class ZambieLib implements ModInitializer {
    public static final String MODID = "zambielib";

    @Override
    public void onInitialize() {
    }
}
