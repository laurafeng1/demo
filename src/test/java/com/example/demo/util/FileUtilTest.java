package com.example.demo.util;

import com.example.demo.entity.User;
import org.junit.Test;

import java.util.List;

public class FileUtilTest {

    @Test
    public void testReadFile() {
        String readValue = FileUtil.readFile("src/test/aaa.txt");
        System.out.println(readValue);
    }

    @Test
    public void testWriteFile() {
        FileUtil.writeFile("src/test/bbb.txt", "xyz");
    }

    @Test
    public void testReadCSV() {
        List<List<String>> list = FileUtil.readCSV("src/test/user.csv");
        System.out.println(list);
        int index = 0;
        for(List<String> list1 : list) {
            if(index != 0) {
                System.out.println(User.createByString(list1));
            }
            index++;
        }
    }
}