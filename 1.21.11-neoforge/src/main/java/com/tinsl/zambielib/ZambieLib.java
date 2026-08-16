package com.tinsl.zambielib;

import net.neoforged.fml.common.Mod;

/**
 * Shared internals for ZambieD's mods. This mod does nothing by itself; it
 * exists so DiscordPresence, Broadback, and friends can share one copy of the
 * code below instead of bundling it three times.
 */
@Mod(ZambieLib.MODID)
public class ZambieLib {
    public static final String MODID = "zambielib";
}
