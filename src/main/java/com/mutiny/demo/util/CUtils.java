package com.mutiny.demo.util;

import java.io.IOException;

public class CUtils {
    public static native int mde(int a,int b);

    public static int calculation(String param) {
        try {
            NativeLoader.loader("native");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        int result = mde(1,2);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(calculation("ssss"));
    }
}
