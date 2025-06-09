package net.tracystacktrace.fluxstone.gui;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.NbtIo;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.common.util.i18n.StringTranslate;
import net.minecraft.common.world.chunk.SaveFormatComparator;
import net.tracystacktrace.fluxstone.Fluxstone;
import net.tracystacktrace.fluxstone.bookmark.AdvancedBookmarkManager;
import net.tracystacktrace.fluxstone.hijacks.CloseNativeHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GuiConfigureWorld extends GuiScreen {

    protected final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected final File worldFolder;
    protected final String beautifulWorldName;
    protected final String beautifulLastPlayed;
    protected final String beautifulWorldSize;

    protected boolean enableCheats;
    protected boolean enableCreative;
    protected boolean changesTriggered;

    protected final String labelGeneralOn;
    protected final String labelGeneralOff;

    public GuiConfigureWorld(GuiScreen parentScreen, SaveFormatComparator save) {
        this.parentScreen = parentScreen;
        this.worldFolder = AdvancedBookmarkManager.getReindevWorldFile(save.getFileName());

        final StringTranslate translate = StringTranslate.getInstance();

        this.labelGeneralOff = translate.translateKey("fluxstone.act.off");
        this.labelGeneralOn = translate.translateKey("fluxstone.act.on");

        this.beautifulWorldName = translate.translateKeyFormat("fluxstone.worldmanager.name", save.getDisplayName(), save.getFileName());
        this.beautifulWorldSize = translate.translateKeyFormat("fluxstone.worldmanager.size", CloseNativeHandler.humanFriendlySize(CloseNativeHandler.getFolderSize(this.worldFolder)));
        this.beautifulLastPlayed = translate.translateKeyFormat("fluxstone.worldmanager.lastplay", sdf.format(new Date(save.func_22163_e())));

        this.enableCheats = save.getCheatsEnabled();
        this.enableCreative = save.getGameType() == 1;
    }

    @Override
    public void initGui() {
        this.controlList.clear();

        final int offsetX = this.width / 2 - 90;
        final int offsetY = this.height / 2 - 78;

        final StringTranslate translate = StringTranslate.getInstance();

        this.controlList.add(new GuiButton(0, offsetX, offsetY + 46, 180, 20, translate.translateKey("fluxstone.worldmanager.act.open")));
        this.controlList.add(new GuiButton(1, offsetX, offsetY + 46 + 30, 180, 20, translate.translateKey("fluxstone.worldmanager.act.backup")));
        this.controlList.add(new GuiButton(2, offsetX, offsetY + 46 + 60, 85, 20, translate.translateKey("fluxstone.worldmanager.switch.cheats")));
        this.controlList.add(new GuiButton(3, offsetX + 95, offsetY + 46 + 60, 85, 20, translate.translateKey("fluxstone.worldmanager.switch.creative")));
        this.updateButtonsNames();

        this.controlList.add(new GuiButton(4, offsetX, offsetY + 46 + 90, 180, 20, translate.translateKey("gui.done")));
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                try {
                    Desktop.getDesktop().open(this.worldFolder);
                } catch (IOException e) {
                    System.out.println("Failed to open a folder: " + e.getMessage());
                }
            }

            if (guiButton.id == 1) {
                this.mc.displayGuiScreen(new GuiManualBackup(this, this.worldFolder));
            }

            if (guiButton.id == 2) {
                this.changesTriggered = true;
                this.enableCheats = !this.enableCheats;
                this.updateButtonsNames();
            }

            if (guiButton.id == 3) {
                this.changesTriggered = true;
                this.enableCreative = !this.enableCreative;
                this.updateButtonsNames();
            }


            if (guiButton.id == 4) {
                if (this.changesTriggered) {
                    System.out.println("World modifications pending! Applying to world + \"" + worldFolder.getName() + "\"!");
                    File levelDat = new File(worldFolder, "level.dat");
                    if (levelDat.exists() && levelDat.length() != 0L) {
                        try {
                            CompoundTag compoundTag = NbtIo.readCompressed(Files.newInputStream(levelDat.toPath()));
                            CompoundTag dataTags = compoundTag.getCompoundTag("Data");

                            dataTags.setBoolean("Cheats", this.enableCheats);
                            dataTags.setInteger("GameType", this.enableCreative ? 1 : 0);

                            NbtIo.writeCompressed(compoundTag, Files.newOutputStream(levelDat.toPath()));
                        } catch (IOException e) {
                            System.out.println("Failed to modify the world: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
                this.mc.displayGuiScreen(parentScreen);
            }

        }
    }

    @Override
    public void drawScreen(float mouseX, float mouseY, float deltaTicks) {
        this.drawDefaultBackground();

        final int offsetY = this.height / 2 - 78;

        this.drawCenteredString(fontRenderer, this.beautifulWorldName, this.width / 2, offsetY, 0xffffffff);
        this.drawCenteredString(fontRenderer, this.beautifulLastPlayed, this.width / 2, offsetY + 12, 0xffffffff);
        this.drawCenteredString(fontRenderer, this.beautifulWorldSize, this.width / 2, offsetY + 24, 0xffffffff);

        super.drawScreen(mouseX, mouseY, deltaTicks);
    }

    private void updateButtonsNames() {
        final StringTranslate translate = StringTranslate.getInstance();
        ((GuiButton) this.controlList.get(2)).displayString = translate.translateKeyFormat("fluxstone.worldmanager.switch.cheats", (this.enableCheats ? labelGeneralOn : labelGeneralOff));
        ((GuiButton) this.controlList.get(3)).displayString = translate.translateKeyFormat("fluxstone.worldmanager.switch.creative", (this.enableCreative ? labelGeneralOn : labelGeneralOff));

        ((GuiButton) this.controlList.get(2)).enabled = Fluxstone.CONFIG.enableWorldCheatToggle;
        ((GuiButton) this.controlList.get(3)).enabled = Fluxstone.CONFIG.enableWorldCheatToggle;
    }
}
