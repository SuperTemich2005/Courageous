package co.uk.squishling.courageous.util;

import net.minecraft.world.World;

public class Reference {

    public static final String MOD_ID = "courageous";
    public static final String NAME = "Courageous";
    public static final String VERSION = "Alpha 1.0";

    public boolean isClient(World world) {
        return world.isRemote;
    }

    public static boolean isServer(World world) {
        return !world.isRemote;
    }

}
