package io.bumble.slowdonkey;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author shenxiangyu on 2020/04/11
 */
public class FileOutputTest {

    private static String filePath = "/usr/local/var/slow-donkey/file-output-test.txt";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = new FileOutputStream(filePath);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(123);
        dataOutputStream.writeInt(456);
        outputStream.flush();
    }
}
