package com.github.satr.ask.client.mocked;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class TestInputStream extends ByteArrayInputStream {

    private final String string;

    public TestInputStream(String s) throws UnsupportedEncodingException {
        super(s.getBytes(Charset.defaultCharset()));
        this.string = s;
    }

    public String getString() {
        return string;
    }
}
