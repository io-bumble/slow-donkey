package io.bumble.slowdonkey;

import org.junit.Test;

import java.io.*;

/**
 * @author shenxiangyu on 2020/04/11
 */
public class FileOutputTest {

    private String filePath = "/usr/local/var/slow-donkey/file-output-test.txt";

    @Test
    public void write() throws IOException {
        OutputStream outputStream = new FileOutputStream(filePath);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(123);
        dataOutputStream.writeInt(456);
        outputStream.flush();
    }

    @Test
    public void read() throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int a = dataInputStream.readInt();
        int b = dataInputStream.readInt();
        System.out.println(a);
        System.out.println(b);
    }
}
