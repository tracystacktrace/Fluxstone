package net.tracystacktrace.fluxstone.bookmark;

/**
 * An API implementation thats hooked into {@link net.minecraft.common.world.chunk.SaveFormatComparator} via mixins.
 * <br>
 * Should be used in internal code only
 */
public interface IBookmark {

    boolean isBookmarked();

    void setBookmarked(boolean b);

}
