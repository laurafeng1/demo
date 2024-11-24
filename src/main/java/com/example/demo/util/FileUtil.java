package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private final static Logger log = LoggerFactory.getLogger(FileUtil.class);
    public static String readFile(String path) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        StringBuilder ris = new StringBuilder();
        try{
            fis = new FileInputStream(path);
            bis = new BufferedInputStream(fis);
            int by;
            while((by = bis.read()) != -1) {
                ris.append((char) by);
            }
        } catch(IOException e) {
            log.error("reading {} has error", path, e);
        } finally {
            try {
                if(bis != null){
                    bis.close();
                }
                if(fis != null){
                    fis.close();
                }
            } catch (IOException e){
                log.error("resource {} closing error", path, e);
            }
        }
        return ris.toString();
    }

    public static void writeFile(String path, String content) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try{
            fos = new FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            byte[] bytes = content.getBytes();
            bos.write(bytes);
        } catch (IOException e){
            log.error("writing to {} has error", path, e);
        } finally {
            try {
                if(bos != null){
                    bos.close();
                }
                if(fos != null){
                    fos.close();
                }
            } catch (IOException e){
                log.error("resource {} closing error", path, e);
            }
        }
    }

    public static List<List<String>> readCSV(String path){
        FileReader fr = null;
        BufferedReader bf = null;
        List<List<String>> contentList = new ArrayList<>();
        try{
            fr = new FileReader(path);
            bf = new BufferedReader(fr);
            String line;
            while((line = bf.readLine()) != null){
                String[] items = line.split(",");
                List<String> itemList = new ArrayList<>();
                for(String item : items){
                    itemList.add(item);
                }
                contentList.add(itemList);
            }
        } catch (IOException e) {
            log.error("reading CSV {} has error", path, e);
        } finally {
            try{
                if(bf != null){
                    bf.close();
                }
                if(fr != null){
                    fr.close();
                }
            } catch (IOException e){
                log.error("reading {} has error", path, e);
            }
        }
        return contentList;
    }
}
