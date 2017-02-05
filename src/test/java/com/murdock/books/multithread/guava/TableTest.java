package com.murdock.books.multithread.guava;

import com.google.common.collect.HashBasedTable;
import org.junit.Test;

import java.util.List;

/**
 * Created by weipeng2k on 16/3/7.
 */
public class TableTest {
    @Test
    public void test() {
        HashBasedTable<String, Integer, Double> objectObjectObjectHashBasedTable = HashBasedTable.create();
        objectObjectObjectHashBasedTable.put("A", 1, 1.0);
        objectObjectObjectHashBasedTable.put("A", 2, 2.0);
        objectObjectObjectHashBasedTable.put("A", 3, 3.0);
        objectObjectObjectHashBasedTable.put("B", 4, 4.0);
        objectObjectObjectHashBasedTable.put("B", 5, 5.0);
        objectObjectObjectHashBasedTable.put("B", 6, 6.0);
        objectObjectObjectHashBasedTable.put("C", 7, 7.0);
        objectObjectObjectHashBasedTable.put("C", 8, 8.0);
        objectObjectObjectHashBasedTable.put("C", 9, 9.0);
        objectObjectObjectHashBasedTable.put("C", 9, 9.0);
        objectObjectObjectHashBasedTable.put("C", 9, 9.0);
        System.out.println(objectObjectObjectHashBasedTable.contains("B", 6));
        System.out.println(objectObjectObjectHashBasedTable.row("A"));
        System.out.println(objectObjectObjectHashBasedTable.contains("A", 1));
        System.out.println(objectObjectObjectHashBasedTable.size());


    }

}
