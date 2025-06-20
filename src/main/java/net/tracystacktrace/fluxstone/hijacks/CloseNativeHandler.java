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
    public static File openSaveDialog(String name) {
        FileDialog fileDialog = new FileDialog((Frame) null, "Backup World", FileDialog.SAVE);

        fileDialog.setFile(name);

        fileDialog.setDirectory(System.getProperty("user.home"));
        fileDialog.setVisible(true);


        String file = fileDialog.getFile();
        String dir = fileDialog.getDirectory();

        if (file != null && dir != null) {
            return new File(dir, file);
        }
        return null;
    }

    public static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return LocalDateTime.now().format(formatter);
    }

    public static byte zipFolder(File sourceFolder, File targetZip) {
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
            return 0;
        } catch (IOException e) {
            System.out.println("Failed to archive: " + e.getMessage());
            e.printStackTrace();
            return 2; //ZIP IO Exception
        }
    }

    public static long getFolderSize(File file) {
        try {
            return Files.walk(file.toPath()).filter(p -> p.toFile().isFile()).mapToLong(p -> p.toFile().length()).sum();
        } catch (IOException e) {
            return -1;
        }
    }

    public static String humanFriendlySize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        return String.format("%.4f %s", bytes / Math.pow(1024, exp), "KMGTPE".charAt(exp - 1) + "B");
    }
}
