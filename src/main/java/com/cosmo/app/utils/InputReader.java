package com.cosmo.app.utils;

public interface InputReader extends AutoCloseable {

    boolean hasNextLine();

    String readLine(String prompt);

    int readInt(String prompt);

    // Ridefinisce close() senza "throws Exception" (AutoCloseable lo dichiara
    // con Exception generico). Restringere la firma qui permette di usare
    // InputReader nel try-with-resources senza dover scrivere un catch.
    @Override
    void close();
}
