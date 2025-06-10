package net.tracystacktrace.fluxstone.mixins.serverlist;

import com.mojang.nbt.CompoundTag;
import net.minecraft.client.networking.ServerData;
import net.tracystacktrace.fluxstone.bookmark.IBookmark;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerData.class)
public class MixinServerData implements IBookmark {

    @Unique
    private boolean fluxstone$bookmark;

    @Inject(method = "getNBTCompound", at = @At("RETURN"), cancellable = true)
    private void fluxstone$injectAddBookmark(CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag tag = cir.getReturnValue();
        tag.setBoolean("fluxstone_bookmark", this.isBookmarked());
        cir.setReturnValue(tag);
    }

    @Inject(method = "getServerDataFromNBTCompound", at = @At("RETURN"), cancellable = true)
    private static void fluxstone$injectFetchBookmark(CompoundTag tag, CallbackInfoReturnable<ServerData> cir) {
        ServerData serverData = cir.getReturnValue();
        ((IBookmark)serverData).setBookmarked(tag.hasKey("fluxstone_bookmark") && tag.getBoolean("fluxstone_bookmark"));
        cir.setReturnValue(serverData);
    }

    @Override
    public boolean isBookmarked() {
        return this.fluxstone$bookmark;
    }

    @Override
    public void setBookmarked(boolean b) {
        this.fluxstone$bookmark = b;
    }
}
