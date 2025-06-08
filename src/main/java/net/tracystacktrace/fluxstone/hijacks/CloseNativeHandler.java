package net.tracystacktrace.fluxstone.hijacks;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Methods to work with OS, reading/writing files, etc
 */
public final class CloseNativeHandler {
    public static File proceedToSave(String name) {
        FileDialog fileDialog = new FileDialog((Frame) null, "Backup World", FileDialog.SAVE);

        fileDialog.setFile(name);

        fileDialog.setDirectory(System.getProperty("user.home"));
        fileDialog.setVisible(true);


        String file = fileDialog.getFile();
        String dir = fileDialog.getDirectory();

        if (file != null && dir != null) return new File(dir, file);
        return null;
    }

    public static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return LocalDateTime.now().format(formatter);
    }

    public static boolean zipFolder(File sourceFolder, File targetZip) {
        try (FileOutputStream fos = new FileOutputStream(targetZip); ZipOutputStream zos = new ZipOutputStream(fos)) {
            Path sourcePath = sourceFolder.toPath();
            String folderName = sourceFolder.getName();

            Files.walk(sourcePath)
                    .forEach(path -> {
                        try {
                            if (path.equals(sourcePath)) return;

                            String relativePath = folderName + File.separator + sourcePath.relativize(path);
                            relativePath = relativePath.replace(File.separatorChar, '/');

                            if (Files.isDirectory(path)) {
                                zos.putNextEntry(new ZipEntry(relativePath + "/"));
                                zos.closeEntry();
                            } else {
                                ZipEntry zipEntry = new ZipEntry(relativePath);
                                zos.putNextEntry(zipEntry);
                                Files.copy(path, zos);
                                zos.closeEntry();
                            }
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
            return true;
        } catch (IOException e) {
            System.out.println("Failed to archive: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
