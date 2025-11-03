package ua.kpi.books.crypto;

import java.io.FilterReader;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class StreamCipher {

    public static class EncryptingWriter extends FilterWriter {
        private final int keyCode;

        public EncryptingWriter(Writer out, char key) {
            super(out);
            this.keyCode = key;
        }

        @Override
        public void write(int c) throws IOException {
            int enc = c + keyCode;
            super.write(enc);
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            char[] tmp = new char[len];
            for (int i = 0; i < len; i++) {
                tmp[i] = (char) (cbuf[off + i] + keyCode);
            }
            super.write(tmp, 0, len);
        }

        @Override
        public void write(String str, int off, int len) throws IOException {
            StringBuilder sb = new StringBuilder(len);
            for (int i = off; i < off + len; i++) {
                char enc = (char) (str.charAt(i) + keyCode);
                sb.append(enc);
            }
            super.write(sb.toString(), 0, sb.length());
        }
    }

    public static class DecryptingReader extends FilterReader {
        private final int keyCode;

        public DecryptingReader(Reader in, char key) {
            super(in);
            this.keyCode = key;
        }

        @Override
        public int read() throws IOException {
            int c = super.read();
            if (c == -1) return -1;
            return c - keyCode;
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            int count = super.read(cbuf, off, len);
            if (count == -1) return -1;
            for (int i = off; i < off + count; i++) {
                cbuf[i] = (char) (cbuf[i] - keyCode);
            }
            return count;
        }
    }
}