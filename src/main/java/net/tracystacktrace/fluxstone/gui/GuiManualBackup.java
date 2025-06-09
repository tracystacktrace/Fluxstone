package net.tracystacktrace.fluxstone.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.common.util.i18n.StringTranslate;
import net.tracystacktrace.fluxstone.hijacks.ThreadManualBackup;

import java.io.File;

public class GuiManualBackup extends GuiScreen {

    public static String[] OUTCOMES = new String[] {
            "success", "error.dialog", "error.zipio", "error.interrupt"
    };

    protected final ThreadManualBackup threadManualBackup;
    protected final String infoContinue;
    protected final String infoTerminate;

    protected String infoString;
    protected boolean finished = false;

    public GuiManualBackup(GuiScreen parentScreen, File worldFolder) {
        this.parentScreen = parentScreen;
        this.threadManualBackup = new ThreadManualBackup(worldFolder);
        this.threadManualBackup.start();

        final StringTranslate translate = StringTranslate.getInstance();
        this.infoContinue = translate.translateKey("fluxstone.manualbackup.continue");
        this.infoString = translate.translateKey("fluxstone.manualbackup.running");
        this.infoTerminate = translate.translateKey("fluxstone.manualbackup.terminate");
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        //check status
        byte status = threadManualBackup.getStatus();

        if(status != -1) {
            this.infoString = StringTranslate.getInstance().translateKey("fluxstone.manualbackup." + OUTCOMES[status]);
            this.finished = true;
        }

    }

    @Override
    public void keyTyped(char eventChar, int eventKey) {
        if(!finished && eventChar == 'o') {
            System.out.println("Sending interrupt signal to " + threadManualBackup.toString());
            threadManualBackup.interrupt();
            return;
        }

        if(!finished && (eventChar == '\r' || eventKey == 1)) {
            return;
        }

        if(finished && eventChar == '\r') {
            this.mc.displayGuiScreen(this.parentScreen);
            return;
        }

        super.keyTyped(eventChar, eventKey);
    }

    @Override
    public void initGui() {
        this.controlList.clear();
    }


    @Override
    public void drawScreen(float mouseX, float mouseY, float deltaTicks) {
        this.drawDefaultBackground();

        if(!finished) {
            this.drawCenteredString(fontRenderer, this.infoTerminate, this.width / 2, this.height / 2 - 32, 0xffffffff);
        }

        this.drawCenteredString(fontRenderer, this.infoString, this.width / 2, this.height / 2 - 16, 0xffffffff);
        if(finished) {
            this.drawCenteredString(fontRenderer, this.infoContinue, this.width / 2, this.height / 2 + 4, 0xffffffff);
        }

        super.drawScreen(mouseX, mouseY, deltaTicks);
    }
}
