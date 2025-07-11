package net.tracystacktrace.fluxstone.mixins.worldlist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.renderer.world.Tessellator;
import net.minecraft.common.world.chunk.SaveFormatComparator;
import net.tracystacktrace.fluxstone.Fluxstone;
import net.tracystacktrace.fluxstone.bookmark.IBookmark;
import net.tracystacktrace.fluxstone.hijacks.InternalSignalDrop;
import net.tracystacktrace.fluxstone.hijacks.SafeCasts;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.GuiSelectWorld$GuiWorldSlot")
public abstract class MixinInsideGuiWorldSlot extends Gui {

    @Shadow
    @Final
    GuiSelectWorld this$0;

    @Shadow
    protected abstract int getSize();

    @Inject(method = "drawSlot", at = @At("HEAD"))
    private void fluxstone$renderGradient1(Minecraft minecraft, int index, float x, float y, int iconHeight, Tessellator tessellator, CallbackInfo ci) {
        if (Fluxstone.CONFIG.enableWorldBookmarkGradient) {
            SaveFormatComparator save = SafeCasts.getSaveListOf(this$0).get(index);
            if (((IBookmark) save).isBookmarked()) {
                this.drawGradientRect(x + 32.0f, y, x + 248f, y + 32.0f, Fluxstone.getBookmarkedWorldGrad(true), Fluxstone.getBookmarkedWorldGrad(false));
            }
        }
    }

    @Redirect(method = "drawSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/common/world/chunk/SaveFormatComparator;getDisplayName()Ljava/lang/String;"))
    private String fluxstone$insertBookmarkIcon(SaveFormatComparator instance) {
        if (Fluxstone.CONFIG.enableWorldBookmarkIcon && ((IBookmark) instance).isBookmarked()) {
            return instance.getDisplayName() + " " + Fluxstone.CONFIG.worldBookmarkIcon;
        }
        return instance.getDisplayName();
    }

    @Redirect(method = "drawSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSelectWorld;drawString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;FFI)V"))
    private void fluxstone$makeStringBrighter(GuiSelectWorld instance, FontRenderer fontRenderer, String s, float x, float y, int i) {
        instance.drawString(fontRenderer, s, x, y, i != 0x00FFFFFF ? 0x00C4C4C4 : 0x00FFFFFF);
    }

    @Inject(method = "elementClicked", at = @At("TAIL"))
    private void fluxstone$injectHookerElementClicked(int index, boolean doubleClick, CallbackInfo ci) {
        boolean selected = ((AccessorGuiSelectWorld) this$0).getSelectedWorld() >= 0 && ((AccessorGuiSelectWorld) this$0).getSelectedWorld() < this.getSize();
        ((InternalSignalDrop) this$0).fluxstone$performButtonsToggle(selected);
    }
}
