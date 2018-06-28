/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.setup.requests.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ReadableByteChannelWrapper implements ReadableByteChannel {

	private final ReadableByteChannel rbc;

	private final Consumer<Long> update;

	@Getter
	private long currentSize;

	public int read(final ByteBuffer dst) throws IOException {
		int read;

		if ((read = this.rbc.read(dst)) > 0) {
			this.currentSize += read;
			this.update.accept(this.currentSize);
		}

		return read;
	}

	public boolean isOpen() {
		return this.rbc.isOpen();
	}

	public void close() throws IOException {
		this.rbc.close();
	}

}