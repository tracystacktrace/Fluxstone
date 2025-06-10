package net.tracystacktrace.fluxstone.mixins.serverlist;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "net.minecraft.client.gui.GuiMultiplayer$GuiSlotServer")
public interface AccessorGuiSlotServer {
    @Invoker("getSize")
    int getOverallSize();
}
