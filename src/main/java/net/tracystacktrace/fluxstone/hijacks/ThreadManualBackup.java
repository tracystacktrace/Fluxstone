package net.tracystacktrace.fluxstone.hijacks;

import java.io.File;

public class ThreadManualBackup extends Thread {

    protected final File worldFolder;
    protected volatile byte resultStatus = -1;

    public ThreadManualBackup(File worldFolder) {
        this.worldFolder = worldFolder;
    }

    //checked by game thread
    public byte getStatus() {
        return this.resultStatus;
    }

    @Override
    public void run() {
        final File chosenPath = CloseNativeHandler.openSaveDialog(this.worldFolder.getName() + "_" + CloseNativeHandler.getCurrentTimestamp() + ".zip");

        if (chosenPath == null) {
            this.resultStatus = 1; //no file/path specific, or quitting save dialog
            return;
        }

        if (Thread.interrupted()) {
            this.resultStatus = 3; //interrupted by external factors
            return;
        }

        final byte zipIOStatus = CloseNativeHandler.zipFolder(this.worldFolder, chosenPath);

        if (Thread.interrupted()) {
            this.resultStatus = 3; //interrupted by external factors
            return;
        }

        //if the archiving fails, set its status byte
        if (zipIOStatus != 0) {
            this.resultStatus = zipIOStatus;
            return;
        }

        System.out.println("Successfully saved backup to: " + chosenPath.getAbsolutePath());
        this.resultStatus = 0;

    }

}
