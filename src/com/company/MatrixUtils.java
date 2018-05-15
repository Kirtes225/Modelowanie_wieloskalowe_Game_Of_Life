package com.company;

import java.util.Random;

public class MatrixUtils {

    public static void randomizeMatrix(boolean[][] matrix){
        for(int i = 0; i<matrix.length; i++){
            for (int j = 0; j<matrix[0].length; j++){
                matrix[i][j] = randomNumber(0, 1) == 1;
            }
        }
    }

    public static void randomizeMatrix(int[][] matrix, int min, int max){
        for(int i = 0; i<matrix.length; i++){
            for (int j = 0; j<matrix[0].length; j++){
                matrix[i][j] = randomNumber(min, max);
            }
        }
    }

    private static int randomNumber(int min, int max) {
        Random rand = new Random();
        return min + rand.nextInt((max - min) + 1);
    }

    public static final int getWidth(final int[][] matrix) {
        int max = 0;
        if (matrix.length > 0) {
            max = matrix[0].length;
            if (matrix.length > 1) {
                for (int i = 1; i < matrix.length; i++) {
                    if (matrix[i].length > max) {
                        max = matrix[i].length;
                    }
                }
            }
        }
        return max;
    }

    public static final int getHeight(final int[][] matrix) {
        return matrix.length;
    }

    public static int[][] resize(final int[][] matrix, final int h, final int w) {
        int width = getWidth(matrix);
        int height = getHeight(matrix);
        return addRows(addCols(matrix, w - width), h - height);
    }

    private static int[][] addRows(final int[][] matrix, final int n) {
        if (n == 0) {
            return matrix;
        }
        int oldHeight = matrix.length;
        int newHeight = oldHeight + n;
        int width = getWidth(matrix);
        int[][] copy = new int[newHeight][width];
        System.arraycopy(matrix, 0, copy, 0, n > 0 ? oldHeight : newHeight);
        for (int i = oldHeight; i < newHeight; i++) {
            copy[i] = new int[width];
        }
        return copy;
    }

    private static int[][] addCols(final int[][] matrix, final int n) {
        if (n == 0) {
            return matrix;
        }
        int oldWidth = getWidth(matrix);
        int newWidth = oldWidth + n;
        int height = matrix.length;
        int[][] copy = new int[height][newWidth];
        for (int i = 0; i < height; i++) {
            copy[i] = new int[newWidth];
            System.arraycopy(matrix[i], 0, copy[i], 0, n > 0 ? oldWidth
                    : newWidth);
        }
        return copy;
    }

    public static String formatMatrix(final int[][] matrix) {
        return formatMatrix(matrix, "\n", ",").toString();
    }


    public static String formatMatrix(final int[][] matrix, String vSep, String hSep) {
        return join(new StringBuffer(), matrix, vSep, hSep).toString();
    }

    public static StringBuffer join(final int[][] arr, String vSep, String hSep) {
        return join(new StringBuffer(), arr, vSep, hSep);
    }

    public static StringBuffer join(final int[] arr, String sep) {
        return join(new StringBuffer(), arr, sep);
    }

    protected static StringBuffer join(StringBuffer buff, final int[][] arr, String vSep, String hSep) {
        if (arr.length > 0) {
            join(buff, arr[0], hSep);
            for (int i = 1; i < arr.length; i++) {
                join(buff.append(vSep), arr[i], hSep);
            }
        }
        return buff;
    }

    protected static StringBuffer join(StringBuffer buff, int[] arr, String sep) {
        if (arr.length > 0) {
            buff.append(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                buff.append(sep).append(arr[i]);
            }
        }
        return buff;
    }

    //boolean

    public static final int getWidth(final boolean[][] matrix) {
        int max = 0;
        if (matrix.length > 0) {
            max = matrix[0].length;
            if (matrix.length > 1) {
                for (int i = 1; i < matrix.length; i++) {
                    if (matrix[i].length > max) {
                        max = matrix[i].length;
                    }
                }
            }
        }
        return max;
    }

    public static final int getHeight(final boolean[][] matrix) {
        return matrix.length;
    }

    public static boolean[][] resize(final boolean[][] matrix, final int h, final int w) {
        int width = getWidth(matrix);
        int height = getHeight(matrix);
        return addRows(addCols(matrix, w - width), h - height);
    }

    private static boolean[][] addRows(final boolean[][] matrix, final int n) {
        if (n == 0) {
            return matrix;
        }
        int oldHeight = matrix.length;
        int newHeight = oldHeight + n;
        int width = getWidth(matrix);
        boolean[][] copy = new boolean[newHeight][width];
        System.arraycopy(matrix, 0, copy, 0, n > 0 ? oldHeight : newHeight);
        for (int i = oldHeight; i < newHeight; i++) {
            copy[i] = new boolean[width];
        }
        return copy;
    }

    private static boolean[][] addCols(final boolean[][] matrix, final int n) {
        if (n == 0) {
            return matrix;
        }
        int oldWidth = getWidth(matrix);
        int newWidth = oldWidth + n;
        int height = matrix.length;
        boolean[][] copy = new boolean[height][newWidth];
        for (int i = 0; i < height; i++) {
            copy[i] = new boolean[newWidth];
            System.arraycopy(matrix[i], 0, copy[i], 0, n > 0 ? oldWidth
                    : newWidth);
        }
        return copy;
    }

    public static String formatMatrix(final boolean[][] matrix) {
        return formatMatrix(matrix, "\n", ",").toString();
    }


    public static String formatMatrix(final boolean[][] matrix, String vSep, String hSep) {
        return join(new StringBuffer(), matrix, vSep, hSep).toString();
    }

    public static StringBuffer join(final boolean[][] arr, String vSep, String hSep) {
        return join(new StringBuffer(), arr, vSep, hSep);
    }

    public static StringBuffer join(final boolean[] arr, String sep) {
        return join(new StringBuffer(), arr, sep);
    }

    protected static StringBuffer join(StringBuffer buff, final boolean[][] arr, String vSep, String hSep) {
        if (arr.length > 0) {
            join(buff, arr[0], hSep);
            for (int i = 1; i < arr.length; i++) {
                join(buff.append(vSep), arr[i], hSep);
            }
        }
        return buff;
    }

    protected static StringBuffer join(StringBuffer buff, boolean[] arr, String sep) {
        if (arr.length > 0) {
            buff.append(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                buff.append(sep).append(arr[i]);
            }
        }
        return buff;
    }
}