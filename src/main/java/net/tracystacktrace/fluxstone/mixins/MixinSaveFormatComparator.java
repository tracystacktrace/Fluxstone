package net.tracystacktrace.fluxstone.mixins;

import net.minecraft.common.world.chunk.SaveFormatComparator;
import net.tracystacktrace.fluxstone.Fluxstone;
import net.tracystacktrace.fluxstone.bookmark.AdvancedBookmark;
import net.tracystacktrace.fluxstone.bookmark.AdvancedBookmarkManager;
import net.tracystacktrace.fluxstone.bookmark.IBookmark;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SaveFormatComparator.class)
public class MixinSaveFormatComparator implements IBookmark {

    @Unique
    public boolean fluxstone$starred;

    @Override
    public boolean isBookmarked() {
        return this.fluxstone$starred;
    }

    @Override
    public void setBookmarked(boolean b) {
        this.fluxstone$starred = b;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void fluxstone$injectBookmark1(String arg1, String arg2, long arg3, long arg5, boolean arg7, int i, boolean par9, boolean par10, CallbackInfo ci) {
        AdvancedBookmark tags = AdvancedBookmarkManager.readWorldBookmark(AdvancedBookmarkManager.getReindevWorldFile(arg1));
        if (tags != null) {
            this.fluxstone$starred = tags.starred;
        }
    }

    @Inject(method = "compareTo(Lnet/minecraft/common/world/chunk/SaveFormatComparator;)I", at = @At("HEAD"), cancellable = true)
    private void fluxstone$manipulateSaveOrder(SaveFormatComparator saveFormatComparator, CallbackInfoReturnable<Integer> cir) {
        if (Fluxstone.CONFIG.pushBookmarkedFirstOrder) {
            if (this.fluxstone$starred != ((IBookmark) saveFormatComparator).isBookmarked()) {
                cir.setReturnValue(this.fluxstone$starred ? -1 : 1);
            }
        }
    }
}
