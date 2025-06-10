package net.tracystacktrace.fluxstone.mixins.serverlist;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.networking.ServerData;
import net.minecraft.client.renderer.world.Tessellator;
import net.tracystacktrace.fluxstone.Fluxstone;
import net.tracystacktrace.fluxstone.bookmark.IBookmark;
import net.tracystacktrace.fluxstone.hijacks.SafeCasts;
import net.tracystacktrace.fluxstone.mixins.AccessorGuiScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.GuiMultiplayer$GuiSlotServer")
public class MixinGuiSlotServer extends Gui {
    @Shadow
    @Final
    GuiMultiplayer this$0;

    @Unique
    ServerData fluxstone$dangerousMethodTapering;

    @Inject(method = "elementClicked", at = @At("HEAD"))
    private void fluxstone$injectTriggerElementClicked(int par1, boolean par2, CallbackInfo ci) {
        if (par1 < ((AccessorGuiMultiplayer) this$0).getInternetServerList().countServers()) {
            boolean selectedAny = ((AccessorGuiMultiplayer) this$0).getSelectedServer() < ((AccessorGuiMultiplayer) this$0).getInternetServerList().countServers();

            GuiButton bookmarkButton = SafeCasts.searchForButton(((AccessorGuiScreen) this$0).getControlList(), -1);
            if (bookmarkButton != null) {
                bookmarkButton.enabled = selectedAny;
            }
        }
    }

    @ModifyVariable(method = "drawServerSlot", at = @At("STORE"), ordinal = 0)
    private ServerData fluxstone$modifyHookServerData(ServerData value) {
        this.fluxstone$dangerousMethodTapering = value;
        return value;
    }

    //add a bookmark icon to server name
    @Redirect(method = "drawServerSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMultiplayer;drawString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;FFI)V"))
    private void fluxstone$redirectAddBookmarkStar(GuiMultiplayer instance, FontRenderer fontRenderer, String s, float x, float y, int i) {
        if (Fluxstone.CONFIG.enableServerBookmarkIcon && i == 16777215) {
            if ((this.fluxstone$dangerousMethodTapering != null && ((IBookmark) this.fluxstone$dangerousMethodTapering).isBookmarked())) { //server title
                s = s + " " + Fluxstone.CONFIG.serverBookmarkIcon;
            }
        }
        instance.drawString(fontRenderer, s, x, y, i);
    }

    //render a bookmark gradient to this...
    @Inject(method = "drawServerSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMultiplayer;drawString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;FFI)V", ordinal = 0))
    private void fluxstone$injectDrawBookmarkGradient(int index, float x, float y, int iconHeight, Tessellator tessellator, CallbackInfo ci) {
        if (Fluxstone.CONFIG.enableServerBookmarkGradient) {
            if ((this.fluxstone$dangerousMethodTapering != null && ((IBookmark) this.fluxstone$dangerousMethodTapering).isBookmarked())) {
                this.drawGradientRect(x, y, x + 216, y + 32, 0xff52349B, 0xff291755);
            }
        }
    }
}
