package com.Algorithms;

import com.Compression.LZW;

public class Main {
    public static final int OPERATIONINDEX = 0;
    public static final int FILENAMEINDEX = 1;
    public static final int BITLENGTHINDEX = 2;
    public static final String ENCODER = "Encoder";
    public static final String DECODER = "Decoder";

    public static void main(String[] args) {
        if(args[OPERATIONINDEX].equals(ENCODER)){
            LZW.Encode(args[FILENAMEINDEX], Integer.parseInt(args[BITLENGTHINDEX]));
        }else if(args[OPERATIONINDEX].equals(DECODER)) {
            LZW.Decode(args[FILENAMEINDEX], Integer.parseInt(args[BITLENGTHINDEX]));
        }else{
            System.out.println("Wrong Operation Code");
        }
    }
}
