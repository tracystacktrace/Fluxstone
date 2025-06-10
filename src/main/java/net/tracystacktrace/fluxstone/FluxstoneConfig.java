package net.tracystacktrace.fluxstone;

import com.fox2code.foxloader.config.ConfigEntry;

public final class FluxstoneConfig {

    /* server bookmarks */
    @ConfigEntry
    public boolean enableServerBookmarkIcon = true;

    @ConfigEntry
    public String serverBookmarkIcon = "\u00A76\u2B50";

    @ConfigEntry
    public boolean enableServerBookmarkGradient = true;

    @ConfigEntry(lowerBounds = 0, upperBounds = 2)
    public int serverBookmarkGradient = 0;

    /* world bookmarks */

    @ConfigEntry
    public boolean enableWorldBookmarkIcon = true;

    @ConfigEntry
    public String worldBookmarkIcon = "\u00A76\u2B50";

    @ConfigEntry
    public boolean enableWorldBookmarkGradient = true;

    @ConfigEntry(lowerBounds = 0, upperBounds = 2)
    public int worldBookmarkGradient = 0;

    @ConfigEntry
    public String worldBookmarkGradientStart = "#FFFFFFFF";

    @ConfigEntry
    public String worldBookmarkGradientEnd = "#FFFFFFFF";

    @ConfigEntry
    public boolean pushWorldBookmarkedFirstOrder = true;

    @ConfigEntry
    public boolean enableWorldCheatToggle = false;


}
