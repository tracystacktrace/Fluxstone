package net.tracystacktrace.fluxstone.mixins.serverlist;

import net.minecraft.client.networking.ServerData;
import net.minecraft.client.networking.ServerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ServerList.class)
public interface AccessorServerList {
    @Accessor("servers")
    List<ServerData> getServerListField();
}
