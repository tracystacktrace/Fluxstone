package net.tracystacktrace.fluxstone.mixins.worldlist;

import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.common.world.chunk.SaveFormatComparator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(GuiSelectWorld.class)
public interface AccessorGuiSelectWorld {
    @Accessor(value = "saveList")
    List<SaveFormatComparator> getSaveList();

    @Accessor(value = "selectedWorld")
    int getSelectedWorld();
}
