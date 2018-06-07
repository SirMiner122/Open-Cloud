/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.setup.requests;

import de.tammo.cloud.core.exceptions.FileDownloadException;
import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.setup.requests.util.ReadableByteChannelWrapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.*;

public class DownloadRequest {

	public void request(final String url, final String path, final Runnable complete) throws IOException {
		final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			final int length = connection.getContentLength();

			final ReadableByteChannel channel = new ReadableByteChannelWrapper(Channels.newChannel(connection.getInputStream()), current -> Logger.progress(current, length));
			final FileOutputStream fos = new FileOutputStream(path);
			fos.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
			complete.run();
		} else {
			Logger.error("Unable to download file from requested url!", new FileDownloadException("Unable to download file from requested url!"));
		}

		connection.disconnect();
	}

}
