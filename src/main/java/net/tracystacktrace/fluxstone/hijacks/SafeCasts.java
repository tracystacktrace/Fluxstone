package net.tracystacktrace.fluxstone.hijacks;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.common.world.chunk.SaveFormatComparator;
import net.tracystacktrace.fluxstone.mixins.AccessorGuiScreen;
import net.tracystacktrace.fluxstone.mixins.serverlist.AccessorGuiSlotServer;
import net.tracystacktrace.fluxstone.mixins.worldlist.AccessorGuiSelectWorld;

import java.util.List;

public class SafeCasts {
    public static List<SaveFormatComparator> getSaveListOf(GuiSelectWorld object) {
        return ((AccessorGuiSelectWorld) object).getSaveList();
    }

    public static AccessorGuiSlotServer getPrivateInnerClass$1(GuiMultiplayer instance) {
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
}
