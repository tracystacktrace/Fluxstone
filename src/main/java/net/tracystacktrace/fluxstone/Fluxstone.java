package net.tracystacktrace.fluxstone;

import com.fox2code.foxloader.loader.Mod;

public class Fluxstone extends Mod {
    public static final FluxstoneConfig CONFIG = new FluxstoneConfig();

    @Override
    public void onPreInit() {
        this.setConfigObject(CONFIG);
    }
}
