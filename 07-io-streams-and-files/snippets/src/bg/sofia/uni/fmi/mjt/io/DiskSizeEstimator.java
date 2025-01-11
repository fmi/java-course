package bg.sofia.uni.fmi.mjt.io;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.Locale;

public class DiskSizeEstimator {

    private static final double KILOBYTE = 1024.0;

    public static void main(String[] args) throws IOException {

        // обхождаме всички дялове на файловата система по подразбиране
        Iterable<FileStore> partitions = FileSystems.getDefault().getFileStores();

        for (FileStore fs : partitions) {
            long totalSpace = fs.getTotalSpace();
            long unallocatedSpace = fs.getUnallocatedSpace();
            long usableSpace = fs.getUsableSpace();

            System.out.println("Partition: " + fs.name());
            System.out.println(
                String.format(Locale.US, "Total space: %,d bytes (%.2f GB)", totalSpace, toGigabytes(totalSpace)));
            System.out.println(String.format(Locale.US, "Unallocated space: %,d bytes (%.2f GB)", unallocatedSpace,
                toGigabytes(unallocatedSpace)));
            System.out.println(
                String.format(Locale.US, "Usable space: %,d bytes (%.2f GB)", usableSpace, toGigabytes(usableSpace)));
            System.out.println();
        }

    }

    private static double toGigabytes(long bytes) {
        return bytes / (KILOBYTE * KILOBYTE * KILOBYTE);
    }

}
