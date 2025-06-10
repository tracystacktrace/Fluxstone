package net.tracystacktrace.fluxstone.mixins.serverlist;

import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.networking.ServerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiMultiplayer.class)
public interface AccessorGuiMultiplayer {
    @Accessor("internetServerList")
    ServerList getInternetServerList();

    @Accessor("selectedServer")
    int getSelectedServer();
}
