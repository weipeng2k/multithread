package com.murdock.books.multithread;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by weipeng2k on 16/8/18.
 */
public class LogATest {

    @Test
    public void test() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("/Users/weipeng2k/Downloads/t.txt"));
        String line = null;
        int i = 0;
        int max = 0;
        int num = 0;
        while ((line = br.readLine()) != null) {
            if (line.contains("HSF-ProviderDetail")) {
                String[] array = line.split("\\|");
                int count = Integer.parseInt(array[3].split(",")[0].trim());
                if (count > max) {
                    max = count;
                }
                System.out.println(count);
                i += count;
                num++;
            }
        }
        System.out.println(i);
        System.out.println(max);

    }

}
