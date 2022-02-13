package com.murdock.books.multithread;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * @author weipeng2k 2021年10月21日 下午21:37:39
 */
public class FileWriteTest {

    /**
     * 117239
     */
    @Test
    public void write() throws Exception {
        PrintWriter out = new PrintWriter("test.t");
        char[] chars = new char[2000];
        for (int i = 0; i < 2000; i++) {
            chars[i] = 'A';
        }

        long start = System.nanoTime();
        out.write(chars);
        out.flush();
        System.out.println(System.nanoTime() - start);
    }

    @Test
    public void read() throws Exception {
        File file = new File("test.t");
        System.out.println(file.length());
    }
}
