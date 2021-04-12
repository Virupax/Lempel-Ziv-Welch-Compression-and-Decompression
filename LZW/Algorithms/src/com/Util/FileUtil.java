package com.Util;

import java.io.*;
import java.util.Scanner;

public class FileUtil {
    public static final String DICTIONARYPATH = "./Dictionary/";
    public static final String ASCIIFILENAME = "ascii-codes.txt";
    public static final String TABDELIMITER = "\t";
    public static final String NEWLINEDELIMITER = "\n";
    public static final String SCANNERDELIMITER = "\\Z";
    public static final String ZEROWIDTHNOBREAKSPACE = "\uFEFF";
    public static final String ENCODINGTYPEUTF8 = "UTF-8";
    public static final int KEY = 0;
    public static final int VALUE = 1;

    public static String readFile(String fileName){
        String fileContents = "";
        try{
            System.out.println(new File(".").getAbsoluteFile());
            File file = new File(fileName);
            fileContents = new Scanner(file,ENCODINGTYPEUTF8).useDelimiter(SCANNERDELIMITER).next();
        }catch(IOException ex){
            System.out.println(ex.getStackTrace());
        }
        return fileContents;
    }

    public static String[] readASCIIDictionaryToIntArray(int allocSize){
        String[] asciiDictionary = new String[allocSize];
        try{
            String[] tokens = readFile(DICTIONARYPATH + ASCIIFILENAME).split(NEWLINEDELIMITER);
            for(int i=0; i<tokens.length; i++){
                if(tokens[i] != ""){
                    String[] keyPair = tokens[i].split(TABDELIMITER);
                    int key = Integer.parseInt(keyPair[KEY].replaceAll(ZEROWIDTHNOBREAKSPACE, "").trim());
                    asciiDictionary[key] = keyPair[VALUE];
                }
            }
        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }
        return asciiDictionary;
    }

    public static void writeFile(String fileName, String fileContent, String encoding){
        try {
            File fileDir = new File(fileName);
            byte[] data = fileContent.getBytes(encoding);
            FileOutputStream fileOutputStream = new FileOutputStream(fileDir);
            fileOutputStream.write(data);
        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }
    }
}
