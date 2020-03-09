package co.uk.squishling.courageous.util;

import net.minecraft.world.World;

public class Reference {

    public static final String MOD_ID = "courageous";

    public static boolean isClient(World world) {
        return world.isRemote;
    }

    public static boolean isServer(World world) {
        return !world.isRemote;
    }

}
