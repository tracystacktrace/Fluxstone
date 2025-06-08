package net.tracystacktrace.fluxstone;

import com.fox2code.foxloader.config.ConfigEntry;

public final class FluxstoneConfig {

    @ConfigEntry(configName = "Bookmark Icon")
    public boolean enableBookmarkIcon = true;

    @ConfigEntry(configName = "Bookmark Gradient")
    public boolean enableBookmarkGradient = true;

    @ConfigEntry(configName = "Bookmark First Sort")
    public boolean pushBookmarkedFirstOrder = true;

    @ConfigEntry(configName = "World Modifications")
    public boolean enableWorldCheatToggle = true;

}
