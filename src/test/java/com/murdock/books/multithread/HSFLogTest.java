package com.murdock.books.multithread;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by weipeng2k on 16/8/19.
 */
@Ignore
public class HSFLogTest {

    @Test
    public void test() throws Exception {
        int old = count("/Users/weipeng2k/Downloads/511.txt");
        int last = count("/Users/weipeng2k/Downloads/512.txt");
        System.out.println(old);
        System.out.println(last);
        System.out.println(last - old);
    }

    @Test
    public void count() throws Exception {
        System.out.println(count("/Users/weipeng2k/Downloads/carts.txt"));
    }

    private int count(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = null;
        int sum = 0;
        while ((line = br.readLine()) != null) {
            if (line.contains("0")) {
                String[] array = line.split("\\|");
                sum += Integer.parseInt(array[7].trim());
            }
        }
        return sum;
    }
}
