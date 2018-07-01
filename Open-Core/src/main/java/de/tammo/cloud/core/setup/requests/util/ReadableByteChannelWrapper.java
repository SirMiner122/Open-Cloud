/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.setup.requests.util;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Consumer;

/**
 * A wrapper for the {@link ReadableByteChannel} to have the possibility, to show a progress bar of a download
 *
 * @author Tammo
 * @since 1.0
 */
@RequiredArgsConstructor
public class ReadableByteChannelWrapper implements ReadableByteChannel {

	/**
	 * {@link ReadableByteChannel} which is wrapped
	 */
	private final ReadableByteChannel rbc;

	/**
	 * {@link Consumer} to get a callback with the current state of progress
	 */
	private final Consumer<Long> update;

	/**
	 * Current downloaded size of the file
	 */
	private long currentSize;

	/**
	 * Read bytes from a stream and added the read bytes to the current size, to show the actual progress
	 *
	 * @param dst The buffer into which bytes are to be transferred
	 * @return The number of bytes read, possibly zero, or -1 if the channel has reached end-of-stream
	 * @throws IOException Error while reading from the {@link ByteBuffer}
	 */
	public int read(final ByteBuffer dst) throws IOException {
		int read;

		if ((read = this.rbc.read(dst)) > 0) {
			this.currentSize += read;
			this.update.accept(this.currentSize);
		}

		return read;
	}

	/**
	 * @return True, if the {@link ReadableByteChannel} is not closed
	 */
	public boolean isOpen() {
		return this.rbc.isOpen();
	}

	/**
	 * Close the {@link ReadableByteChannel}
	 *
	 * @throws IOException If an I/O error occurs
	 */
	public void close() throws IOException {
		this.rbc.close();
	}

}