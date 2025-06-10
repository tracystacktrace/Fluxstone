package net.tracystacktrace.fluxstone.mixins.worldlist;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.common.world.chunk.SaveFormatComparator;
import net.tracystacktrace.fluxstone.bookmark.AdvancedBookmarkManager;
import net.tracystacktrace.fluxstone.bookmark.IBookmark;
import net.tracystacktrace.fluxstone.gui.GuiConfigureWorld;
import net.tracystacktrace.fluxstone.hijacks.InternalSignalDrop;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Mixin(GuiSelectWorld.class)
public abstract class MixinGuiSelectWorld extends GuiScreen implements InternalSignalDrop {

    @Shadow
    private List<SaveFormatComparator> saveList;
    @Shadow
    private int selectedWorld;

    @Shadow
    protected abstract String getSaveFileName(int index);

    @Unique
    GuiButton fluxstone$addSettings;
    @Unique
    GuiButton fluxstone$bookmarkWorld;

    @SuppressWarnings("UnnecessaryUnicodeEscape")
    @Inject(method = "initGui", at = @At("TAIL"))
    private void fluxstone$injectGUIButtons(CallbackInfo ci) {
        this.controlList.add(fluxstone$addSettings = new GuiButton(
                301, this.width / 2 - 182, this.height - 52, 20, 20, "\u00A7a\u270E"
        ));
        this.controlList.add(fluxstone$bookmarkWorld = new GuiButton(
                302, this.width / 2 - 182, this.height - 28, 20, 20, "\u00A76\u2B50"
        ));
        this.fluxstone$addSettings.enabled = false;
        this.fluxstone$bookmarkWorld.enabled = false;
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void fluxstone$injectButtonAction(GuiButton button, CallbackInfo ci) {
        if (button.enabled) {
            if (button.id == 301) {
                this.mc.displayGuiScreen(new GuiConfigureWorld(this, saveList.get(this.selectedWorld)));
                ci.cancel();
            }

            if (button.id == 302) {
                //handle bookmark tagging
                final File worldFile = AdvancedBookmarkManager.getReindevWorldFile(this.getSaveFileName(this.selectedWorld));
                IBookmark bookmark = (IBookmark) this.saveList.get(this.selectedWorld);
                bookmark.setBookmarked(!bookmark.isBookmarked());
                AdvancedBookmarkManager.saveWorldBookmark(worldFile, bookmark);
                Collections.sort(this.saveList);
                ci.cancel();
            }

        }
    }

    @Override
    public void fluxstone$performButtonsToggle(boolean signal) {
        this.fluxstone$bookmarkWorld.enabled = signal;
        this.fluxstone$addSettings.enabled = signal;
    }
}
