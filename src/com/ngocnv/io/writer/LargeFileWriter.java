package com.ngocnv.io.writer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;


public class LargeFileWriter implements IWriter {

    private static final String INPUT_FILE_PATH = "/Users/apple/Documents/CoinExchange/Large-File-Process/src/com/ngocnv/io/BTC_JPY.csv";
    private static final String OUTPUT_FILE_PATH = "/Users/apple/Documents/CoinExchange/Large-File-Process/src/com/ngocnv/io/BTC_JPY_LARGE.csv";
    private static final int BUFF_SIZE = 1024 * 8;
    private static final Long MAX_FILE_SIZE = 1024 * 1024 * 1024 * 4L;
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public void write(String path) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(INPUT_FILE_PATH));
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(OUTPUT_FILE_PATH));
        byte[] bytes = new byte[BUFF_SIZE];
        long fileSize = 0L;
        int nRead = inputStream.read(bytes, 0, BUFF_SIZE);
        while(fileSize < MAX_FILE_SIZE) {
            fileSize += nRead;
            outputStream.write(bytes, 0, bytes.length);
            outputStream.flush();
        }
        inputStream.close();
        outputStream.close();
    }

    public void write1(String path) throws IOException, ParseException {
        String firstLine = "2018-08-18 11:18:41,728372.0,728375.0,728365.0,728372.0,0";
        String[] eles = firstLine.split(",");
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(INPUT_FILE_PATH));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        long fileSize = 0L;
        while(fileSize < MAX_FILE_SIZE) {
            eles[0] = add1s(eles[0]);
            firstLine = Arrays.stream(eles).collect(Collectors.joining(","));
            bufferedWriter.write(firstLine);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        outputStream.close();
    }

    String add1s(String currentTime) throws ParseException {
        Date temp = SIMPLE_DATE_FORMAT.parse(currentTime);
        temp.setTime(temp.getTime() + 1000);
        return SIMPLE_DATE_FORMAT.format(temp);
    }

    public Long read(String path) throws IOException {
        Long fileSize = 0L;

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(INPUT_FILE_PATH));
        byte[] bytes = new byte[BUFF_SIZE];
        int nRead;
        while ((nRead=inputStream.read(bytes, 0, BUFF_SIZE)) != -1) {
            fileSize += nRead;
            for (int i = 0; i < nRead; i++) {

            }
        }

        inputStream.close();
        System.out.println("Finished to read file: "+fileSize/MAX_FILE_SIZE + "G");
        return fileSize;
    }

    public static void main(String[] args) throws IOException, ParseException {
        LargeFileWriter writer = new LargeFileWriter();
        writer.write1(null);
//        ((LargeFileWriter) writer).read(null);
    }
}
