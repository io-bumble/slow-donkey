package io.bumble.slowdonkey.file;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author shenxiangyu on 2020/04/11
 */
public class FileInputTest extends AbstractFileTest {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int a = dataInputStream.readInt();
        int b = dataInputStream.readInt();
        System.out.println(a);
        System.out.println(b);
    }
}
