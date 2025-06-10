package net.tracystacktrace.fluxstone.mixins.serverlist;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.GuiMultiplayer;
import net.tracystacktrace.fluxstone.hijacks.SafeCasts;
import net.tracystacktrace.fluxstone.mixins.AccessorGuiScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.GuiMultiplayer$GuiSlotServer")
public class MixinGuiSlotServer {
    @Shadow @Final GuiMultiplayer this$0;

    @Inject(method = "elementClicked", at = @At("HEAD"))
    private void lol(int par1, boolean par2, CallbackInfo ci) {
        if(par1 < ((AccessorGuiMultiplayer)this$0).getInternetServerList().countServers()) {
            boolean selectedAny = ((AccessorGuiMultiplayer)this$0).getSelectedServer() < ((AccessorGuiMultiplayer)this$0).getInternetServerList().countServers();

            GuiButton bookmarkButton = SafeCasts.searchForButton(((AccessorGuiScreen)this$0).getControlList(), -1);
            if(bookmarkButton != null) {
                bookmarkButton.enabled = selectedAny;
            }
        }
    }
}
