package net.tracystacktrace.fluxstone.mixins;

import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(GuiScreen.class)
public interface AccessorGuiScreen {
    @Accessor("controlList")
    List<GuiElement> getControlList();
}
