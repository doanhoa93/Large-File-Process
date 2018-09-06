package com.ngocnv.io.search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BinarySearch {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String FILE_PATH = "/Users/apple/Documents/CoinExchange/Large-File-Process/src/com/ngocnv/io/BTC_JPY.csv";

    public static void main(String[] args) throws IOException, ParseException {

        String firstDate = getFirstDateStr(FILE_PATH);
        long distance1 = distance(firstDate, "2019-05-17 11:18:54");
        long distance2 = distance(firstDate, "2019-08-17 11:19:05");

        RandomAccessFile file = new RandomAccessFile(FILE_PATH, "r");
        int LINE_LEN = 57;
        int LINE_BREAK_LEN = 1;
        int sizeBuff =(int)(distance2 - distance1 + 1) * (LINE_LEN + LINE_BREAK_LEN);
        byte[] bytes = new byte[sizeBuff];
        final int size = (int) ((file.length() / (LINE_LEN + LINE_BREAK_LEN)));
        file.seek((LINE_LEN + LINE_BREAK_LEN) * distance1);
        file.read(bytes, 0, bytes.length);
        file.close();
        System.out.println(new String(bytes));


//        String target = "2018-08-18 17:56:11";
//        RandomAccessFile file = new RandomAccessFile(FILE_PATH, "r");
//        int LINE_LEN = 57;
//        int LINE_BREAK_LEN = 1;
//        final int size = (int) ((file.length() / (LINE_LEN + LINE_BREAK_LEN)));
////        System.out.println(size);
//        long startTime = System.currentTimeMillis();
//        System.out.println("start: "+startTime);
//        int result =  Collections.binarySearch(
//                new AbstractList<String>() {
//                    public String get(int pIdx) {
//                        try {
//                            file.seek((LINE_LEN + LINE_BREAK_LEN) * pIdx);
//                            return file.readLine();
//                        } catch (IOException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                    }
//
//                    public int size() {return size;}
//                },
//                target,
//                (e1, e2) -> e1.compareTo(e2)
//        );
//
//        System.out.println("result: "+result);
//        long endTime = System.currentTimeMillis();
//        System.out.println("end: "+endTime);
//        double totalTime = (double)(endTime - startTime) / 1000;
//        System.out.println("Total Time: "+totalTime);

    }


    static String readFirstLine(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String firstLine;
        while ((firstLine=bufferedReader.readLine()) != null) {
            return firstLine;
        }

        throw new IOException();
    }

    static String getFirstDateStr(String path) throws IOException {
        return readFirstLine(path).split(",")[0];
    }

    static Date convertStringToDate(String dateString) throws ParseException {
        return SIMPLE_DATE_FORMAT.parse(dateString);
    }

    public static long distance(String fromStr, String toStr) throws ParseException {
        Date from = convertStringToDate(fromStr);
        Date to = convertStringToDate(toStr);
        long diffInMillies = to.getTime() - from.getTime();
        return TimeUnit.MILLISECONDS.toSeconds(diffInMillies);
    }

    public static long distance(Date from, Date to) throws ParseException {
        long diffInMillies = to.getTime() - from.getTime();
        return TimeUnit.MILLISECONDS.toSeconds(diffInMillies);
    }

    public static int binarySearch(int input, int[] values, int start, int end) {
        if (start > end) return -1;

        int mid = (start + end) / 2;

        if (input == values[mid]) return mid;
        else if (input > values[mid]) return binarySearch(input, values, mid + 1, end);
        else return binarySearch(input, values, start, mid - 1);
    }
}
