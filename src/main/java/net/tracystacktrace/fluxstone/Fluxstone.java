package net.tracystacktrace.fluxstone;

import com.fox2code.foxloader.loader.Mod;

public class Fluxstone extends Mod {
    public static final FluxstoneConfig CONFIG = new FluxstoneConfig();

    @Override
    public void onPreInit() {
        this.setConfigObject(CONFIG);
    }

    public static int getBookmarkedWorldGrad(boolean first) {
        switch (CONFIG.worldBookmarkGradient) {
            case 0:
                return first ? 0xff52349B : 0xff291755;
            case 1:
                return first ? 0xffD76276 : 0xffC01C53;
            case 2:
            default:
                return first ? getColorFromHexString(CONFIG.worldBookmarkGradientStart) : getColorFromHexString(CONFIG.worldBookmarkGradientEnd);
        }
    }

    public static int getBookmarkedServerGrad(boolean first) {
        switch (CONFIG.serverBookmarkGradient) {
            case 0:
                return first ? 0xff52349B : 0xff291755;
            case 1:
                return first ? 0xffD76276 : 0xffC01C53;
            case 2:
            default:
                return first ? getColorFromHexString(CONFIG.serverBookmarkGradientStart) : getColorFromHexString(CONFIG.serverBookmarkGradientEnd);
        }
    }

    private static int getColorFromHexString(String s) {
        if (s == null || s.isEmpty()) return 0;
        if (s.startsWith("#")) s = s.substring(1);

        switch (s.length()) {
            case 6:
                return 0xFF000000 | Integer.parseInt(s, 16);
            case 8:
                return (int) Long.parseLong(s, 16);
            default:
                return 0;
        }
    }
}
