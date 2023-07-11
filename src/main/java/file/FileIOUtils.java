package file;

import java.io.*;
import java.nio.channels.FileChannel;

public class FileIOUtils {
    public static void copyFileBySteam(File source, File dest) throws IOException {
        try (InputStream inputStream = new FileInputStream(source);
             OutputStream outputStream = new FileOutputStream(dest)) {
            outputStream.write(inputStream.readAllBytes());
        }
    }

    public static void copyFileByChannel(File source, File dest) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(source);
             FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
            FileChannel inputChannel = fileInputStream.getChannel();
            FileChannel outputChannel = fileOutputStream.getChannel();
            for (long count = inputChannel.size(); count > 0; ) {
                long transferred = inputChannel.transferTo(inputChannel.position(), count, outputChannel);
                inputChannel.position(inputChannel.position() + transferred);
                count -= transferred;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File source = new File("/Users/wade/Desktop/source.txt");
        File dest = new File("/Users/wade/Desktop/dest.txt");
        long start = System.currentTimeMillis();
        copyFileBySteam(source, dest);
        System.out.printf("Cost: %d", System.currentTimeMillis() - start);

        File source1 = new File("/Users/wade/Desktop/source.txt");
        File dest1 = new File("/Users/wade/Desktop/dest.txt");
        long start1 = System.currentTimeMillis();
        copyFileByChannel(source1, dest1);
        System.out.printf("Cost: %d", System.currentTimeMillis() - start1);
    }
}
