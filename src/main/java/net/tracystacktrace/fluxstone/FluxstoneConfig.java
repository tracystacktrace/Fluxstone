package net.tracystacktrace.fluxstone;

import com.fox2code.foxloader.config.ConfigEntry;

public final class FluxstoneConfig {

    @ConfigEntry
    public boolean enableBookmarkIcon = true;

    @ConfigEntry
    public String bookmarkIcon = "\u00A76\u2B50";

    @ConfigEntry
    public boolean enableBookmarkGradient = true;

    @ConfigEntry(lowerBounds = 0, upperBounds = 2)
    public int bookmarkGradientType = 0;

    @ConfigEntry
    public String bookmarkGradientStart = "#FFFFFFFF";

    @ConfigEntry
    public String bookmarkGradientEnd = "#FFFFFFFF";

    @ConfigEntry
    public boolean pushBookmarkedFirstOrder = true;

    @ConfigEntry
    public boolean enableWorldCheatToggle = false;


}
