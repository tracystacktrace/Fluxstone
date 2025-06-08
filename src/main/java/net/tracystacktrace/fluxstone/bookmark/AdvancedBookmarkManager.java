package net.tracystacktrace.fluxstone.bookmark;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.NbtIo;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.IOException;

public final class AdvancedBookmarkManager {

    public static File getReindevWorldFile(String name) {
        return new File(Minecraft.getInstance().getMinecraftDir(), "saves_ReIndev" + File.separatorChar + name);
    }

    public static AdvancedBookmark readWorldBookmark(File worldFolder) {
        final File tagsFile = new File(worldFolder, "bookmark.nbt");
        if (tagsFile.isDirectory() || !tagsFile.exists()) {
            return null;
        }

        try {
            final CompoundTag tags = NbtIo.read(tagsFile);
            final AdvancedBookmark returnValue = new AdvancedBookmark();

            //fetch values
            returnValue.starred = getBooleanOrDefault(tags, "starred", false);


            return returnValue;
        } catch (IOException e) {
            System.out.println("Error while reading bookmark: " + e.getMessage());
            return null;
        }
    }

    public static void saveWorldBookmark(File worldFolder, IBookmark tags) {
        final File tagsFile = new File(worldFolder, "bookmark.nbt");
        if (tagsFile.exists()) {
            tagsFile.delete();
        }

        final CompoundTag saveTags = new CompoundTag();

        //fetch values
        saveTags.setBoolean("starred", tags.isBookmarked());

        try {
            NbtIo.write(saveTags, tagsFile);
        } catch (IOException e) {
            System.out.println("Error while writing bookmark: " + e.getMessage());
        }
    }

    private static boolean getBooleanOrDefault(CompoundTag tag, String name, boolean defValue) {
        return tag.hasKey(name) ? tag.getBoolean(name) : defValue;
    }
}
