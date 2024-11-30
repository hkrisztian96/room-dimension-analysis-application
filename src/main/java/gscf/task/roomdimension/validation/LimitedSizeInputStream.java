package gscf.task.roomdimension.validation;

import java.io.IOException;
import java.io.InputStream;

import gscf.task.roomdimension.exception.SizeLimitExceededIOException;

/**
 * A custom {@link InputStream} implementation that limits the size of the data that can be read from the stream.
 *
 * @author krisztian.hathazi
 */
public class LimitedSizeInputStream extends InputStream {

    private final InputStream originalInputStream;

    private final long maxSize;

    private long totalSize;

    /**
     * Constructs a new {@link LimitedSizeInputStream} that wraps the provided {@link InputStream} and enforces the size limit.
     *
     * @param original
     *            The original {@link InputStream} to be wrapped.
     * @param maxSize
     *            The maximum number of bytes that can be read from the stream.
     */
    public LimitedSizeInputStream(InputStream original, long maxSize) {
        this.originalInputStream = original;
        this.maxSize = maxSize;
    }

    /**
     * Reads the next byte of data from the input stream.
     * <p>
     * If the read byte causes the total size to exceed the maximum size, an exception will be thrown.
     * </p>
     *
     * @return The next byte of data, or -1 if the end of the stream is reached.
     * @throws IOException
     *             If an I/O error occurs or if the size limit is exceeded.
     */
    @Override
    public int read() throws IOException {
        int i = originalInputStream.read();
        if (i >= 0) {
            incrementCounter(1);
        }
        return i;
    }

    /**
     * Reads up to {@code len} bytes of data into an array from the input stream.
     * <p>
     * If the total data size exceeds the maximum size, an exception will be thrown.
     * </p>
     *
     * @param b
     *            The buffer into which the data is read.
     * @return The number of bytes read, or -1 if the end of the stream is reached.
     * @throws IOException
     *             If an I/O error occurs or if the size limit is exceeded.
     */
    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    /**
     * Reads up to {@code len} bytes of data into a portion of an array from the input stream.
     * <p>
     * If the total data size exceeds the maximum size, an exception will be thrown.
     * </p>
     *
     * @param b
     *            The buffer into which the data is read.
     * @param off
     *            The start offset in the buffer at which data is written.
     * @param len
     *            The maximum number of bytes to read.
     * @return The number of bytes read, or -1 if the end of the stream is reached.
     * @throws IOException
     *             If an I/O error occurs or if the size limit is exceeded.
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int i = originalInputStream.read(b, off, len);
        if (i >= 0) {
            incrementCounter(i);
        }
        return i;
    }

    private void incrementCounter(int size) throws IOException {
        totalSize += size;
        if (totalSize > maxSize) {
            throw new SizeLimitExceededIOException("InputStream exceeded maximum size in bytes.");
        }
    }
}
