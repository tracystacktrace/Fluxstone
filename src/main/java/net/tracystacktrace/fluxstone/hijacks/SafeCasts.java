package net.tracystacktrace.fluxstone.hijacks;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.networking.ServerData;
import net.minecraft.common.world.chunk.SaveFormatComparator;
import net.tracystacktrace.fluxstone.bookmark.IBookmark;
import net.tracystacktrace.fluxstone.mixins.AccessorGuiScreen;
import net.tracystacktrace.fluxstone.mixins.serverlist.AccessorGuiMultiplayer;
import net.tracystacktrace.fluxstone.mixins.serverlist.AccessorGuiSlotServer;
import net.tracystacktrace.fluxstone.mixins.serverlist.AccessorServerList;
import net.tracystacktrace.fluxstone.mixins.worldlist.AccessorGuiSelectWorld;

import java.util.Comparator;
import java.util.List;

public class SafeCasts {
    public static List<SaveFormatComparator> getSaveListOf(GuiSelectWorld object) {
        return ((AccessorGuiSelectWorld) object).getSaveList();
    }

    public static AccessorGuiSlotServer getGuiSlotServerWrapper(GuiMultiplayer instance) {
        return (AccessorGuiSlotServer) ((AccessorGuiScreen)instance).getControlList().get(0);
    }

    public static GuiButton searchForButton(List<GuiElement> controlList, int id) {
        for(GuiElement element : controlList) {
            if(element instanceof GuiButton && ((GuiButton)element).id == id) {
                return (GuiButton) element;
            }
        }
        return null;
    }

    public static void sortServerList(Object guiMultiplayer) {
        ((AccessorServerList)((AccessorGuiMultiplayer)guiMultiplayer).getInternetServerList()).getServerListField().sort((o1, o2) -> {
            if (((IBookmark)o1).isBookmarked() && !((IBookmark)o2).isBookmarked()) return -1;
            if (!((IBookmark)o1).isBookmarked() && ((IBookmark)o2).isBookmarked()) return 1;
            return o1.serverName.compareTo(o2.serverName);
        });
    }
}
