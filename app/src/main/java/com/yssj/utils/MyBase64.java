package com.yssj.utils;



/**
 * Created by wdf on 16-4-9.
 */
public class MyBase64 {

    private static final int RANGE = 0xff;
    //自定义码表 可随意变换字母排列顺序，然后会自动生成解密表
    private static final char[] Base64ByteToStr = new char[] {
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',// 0 ~ 9
            'A', 'V', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',// 10 ~ 19
            'U', 'B', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',// 20 ~ 29
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',// 30 ~ 39
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',// 40 ~ 49
            'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',// 50 ~ 59
            '8', '9', '+', '/'// 60 ~ 63
    };

    /**
     * 加密
     * @param bytes
     * @return
     * @throws Exception
     */
    public final static String Base64Encode(byte[] bytes) throws Exception {
        StringBuilder res = new StringBuilder();
        //per 3 bytes scan and switch to 4 bytes
        for(int i = 0; i <= bytes.length - 1; i+=3) {
            byte[] enBytes = new byte[4];
            byte tmp = (byte)0x00;// save the right move bit to next position's bit
            //3 bytes to 4 bytes
            for(int k = 0; k <= 2; k++) {// 0 ~ 2 is a line
                if((i + k) <= bytes.length - 1) {
                    enBytes[k] = (byte) (((((int) bytes[i + k] & RANGE) >> (2 + 2 * k))) | (int)tmp);//note , we only get 0 ~ 127 ???
                    tmp = (byte) (((((int) bytes[i + k] & RANGE) << (2 + 2 * (2 - k))) & RANGE) >> 2);
                } else {
                    enBytes[k] = tmp;
                    tmp = (byte)64;//if tmp > 64 then the char is '=' hen '=' -> byte is -1 , so it is EOF or not print char
                }
            }
            enBytes[3] = tmp;//forth byte
            //4 bytes to encode string
            for (int k = 0; k <= 3; k++) {
                if((int)enBytes[k] <= 63) {
                    res.append(Base64ByteToStr[(int)enBytes[k]]);
                } else {
                    res.append('=');
                }
            }
        }
        return res.toString();
    }
}
