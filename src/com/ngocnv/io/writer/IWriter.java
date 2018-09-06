package com.ngocnv.io.writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface IWriter {

    void write(String path) throws IOException;
}
