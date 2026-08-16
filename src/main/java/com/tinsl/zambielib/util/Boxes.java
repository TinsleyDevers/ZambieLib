package com.tinsl.zambielib.util;

import net.minecraft.world.phys.AABB;

/** Bounding box math shared by mods that treat entities as physical objects. */
public final class Boxes {
    private Boxes() {
    }

    /** Volume in cubic blocks. A rough but honest stand-in for mass. */
    public static double volume(AABB box) {
        return box.getXsize() * box.getYsize() * box.getZsize();
    }

    /** True if the two boxes overlap when viewed from above. */
    public static boolean overlapsHorizontally(AABB a, AABB b) {
        return a.minX < b.maxX && a.maxX > b.minX && a.minZ < b.maxZ && a.maxZ > b.minZ;
    }

    /** Overlapping footprint area when viewed from above, zero if none. */
    public static double horizontalOverlapArea(AABB a, AABB b) {
        double x = Math.min(a.maxX, b.maxX) - Math.max(a.minX, b.minX);
        double z = Math.min(a.maxZ, b.maxZ) - Math.max(a.minZ, b.minZ);
        return x <= 0 || z <= 0 ? 0 : x * z;
    }

    /** The top portion of a box, at most the given thickness. */
    public static AABB topSlab(AABB box, double thickness) {
        double minY = Math.max(box.minY, box.maxY - thickness);
        return new AABB(box.minX, minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    /**
     * True if a pair of feet at feetY count as standing on the box top,
     * within the given tolerance below and above the surface.
     */
    public static boolean feetOnTop(double feetY, AABB box, double below, double above) {
        return feetY >= box.maxY - below && feetY <= box.maxY + above;
    }
}
