package net.tracystacktrace.fluxstone.hijacks;

import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.common.world.chunk.SaveFormatComparator;
import net.tracystacktrace.fluxstone.mixins.worldlist.AccessorGuiSelectWorld;

import java.util.List;

public class SafeCasts {
    public static List<SaveFormatComparator> getSaveListOf(GuiSelectWorld object) {
        return ((AccessorGuiSelectWorld) object).getSaveList();
    }
}
