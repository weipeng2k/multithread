package com.murdock.books.multithread;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by weipeng2k on 16/8/19.
 */
public class ELogTest {

    @Test
    public void test() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("/Users/weipeng2k/Downloads/e.txt"));
        String line = null;
        int i = 0;
        while ((line = br.readLine()) != null) {
            if (line.contains("@srvr")) {
                String[] array = line.split("\\|");
                int count = Integer.parseInt(array[3].split(",")[0].trim());
                i += count;
            }
        }
        System.out.println(i);
    }

}
