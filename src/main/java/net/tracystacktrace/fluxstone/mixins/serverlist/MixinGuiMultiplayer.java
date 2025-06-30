package net.tracystacktrace.fluxstone.mixins.serverlist;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.networking.ServerData;
import net.tracystacktrace.fluxstone.Fluxstone;
import net.tracystacktrace.fluxstone.bookmark.IBookmark;
import net.tracystacktrace.fluxstone.hijacks.SafeCasts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public class MixinGuiMultiplayer extends GuiScreen {

    @Shadow private int selectedServer;

    @Unique private GuiButton fluxstone$buttonBookmarkServer;

    @Inject(method = "initGuiControls", at = @At("TAIL"))
    private void fluxstone$injectServerButtons(CallbackInfo ci) {
        this.controlList.add(this.fluxstone$buttonBookmarkServer = new GuiButton(-1, this.width / 2 - 182, this.height - 28, 20, 20, "\u00A76\u2B50"));
        this.fluxstone$buttonBookmarkServer.enabled = (this.selectedServer >= 0 && this.selectedServer < SafeCasts.getGuiSlotServerWrapper(GuiMultiplayer.class.cast(this)).getOverallSize());
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void fluxstone$injectServerButtonsActions(GuiButton button, CallbackInfo ci) {
        if(button.enabled && button.id == this.fluxstone$buttonBookmarkServer.id) {
            ServerData serverData = ((AccessorGuiMultiplayer)this).getInternetServerList().getServerData(this.selectedServer);
            ((IBookmark)serverData).toggleBookmarked();

            //sort data
            if(Fluxstone.CONFIG.pushServerBookmarkedFirstOrder) {
                SafeCasts.sortServerList(this);
            }

            //save servers
            ((AccessorGuiMultiplayer)this).getInternetServerList().saveServerList();


            ci.cancel();
        }
    }

}
