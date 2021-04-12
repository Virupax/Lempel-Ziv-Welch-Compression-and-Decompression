package com.Compression;

import com.Util.FileUtil;

public class LZW {

    public static final String SPACEDELIMITER = " ";
    public static final String DOTDELIMITER = "\\.";
    public static final int BASE = 2;
    public static final String ENCODINGTYPEUTF8 = "UTF-8";
    public static final String ENCODINGTYPEUTF16 = "UTF-16BE";
    public static final String NULLCHARACTER = "\\u0000";
    public static int maxTableSize = 0;
    public static int FIRSTINDEX = 0;

    public static int dictionaryLookup(String[] dictionary, String value){
        int key = -1;
        for(int i=0; i<dictionary.length; i++){
            if(dictionary[i] != null && dictionary[i].equals(value)){
                key = i;
                break;
            }
        }
        return key;
    }

    public static void Encode(String inputFileName, int bitLength){
        int dictionaryIndex = 256;
        String string = "", output = "";
        maxTableSize = (int)Math.pow(BASE, bitLength);
        String[] asciiDictionary = FileUtil.readASCIIDictionaryToIntArray(maxTableSize);
        String inputData = FileUtil.readFile(inputFileName);

        for (int i = 0; i < inputData.length(); i++){
            String symbol = "" + inputData.charAt(i);
            int key = dictionaryLookup(asciiDictionary, string + symbol);
            if(key > -1){
                string+= symbol;
            }
            else{
                output+= dictionaryLookup(asciiDictionary, string) + SPACEDELIMITER;
                if(dictionaryIndex < maxTableSize){
                    asciiDictionary[dictionaryIndex++] = string + symbol;
                }
                string = symbol;
            }
        }
        output+= dictionaryLookup(asciiDictionary, string) + SPACEDELIMITER;
        FileUtil.writeFile(inputFileName.split(DOTDELIMITER)[0] + ".lzw", output, ENCODINGTYPEUTF16);
    }

    public static void Decode(String inputFileName, int bitLength){
        int dictionaryLength = 256;
        String string = "", output = "", newString = "";
        maxTableSize = (int)Math.pow(BASE, bitLength);
        String[] asciiDictionary = FileUtil.readASCIIDictionaryToIntArray(maxTableSize);
        String[] inputData = FileUtil.readFile(inputFileName).split(SPACEDELIMITER);

        int code = Integer.parseInt(inputData[FIRSTINDEX].replaceAll(NULLCHARACTER, "").trim());
        string = asciiDictionary[code];
        output+= string;

        for (int i = 1; i < inputData.length; i++){
            code = Integer.parseInt(inputData[i].replaceAll(NULLCHARACTER, "").trim());

            if(asciiDictionary[code] == null){
                newString = string + string.charAt(FIRSTINDEX);
            }else{
                newString = asciiDictionary[code];
            }
            output+= newString;

            if(dictionaryLength < maxTableSize){
               asciiDictionary[dictionaryLength++] = string + newString.charAt(FIRSTINDEX);
            }
            string = newString;
        }
        FileUtil.writeFile(inputFileName.split(DOTDELIMITER)[0] + "_decoded.txt", output, ENCODINGTYPEUTF8);
    }
}
