package de.tammo.cloud.core.log.component.impl;

import de.tammo.cloud.core.log.LogLevel;
import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.core.log.component.LoggerComponent;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Implementation of {@link LoggerComponent} to print a progress bar
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
public class ProgressBarComponent implements LoggerComponent {

	/**
	 * Length of bytes from the content
	 *
	 * @since 1.0
	 */
	private final long length;

	/**
	 * Current downloaded bytes
	 *
	 * @since 1.0
	 */
	@Setter
	private long current;

	/**
	 * {@inheritDoc}
	 */
	public void print() {
		final int percent = (int) (((double) this.current / this.length) * 100);
		System.out.print("\r[" + Logger.getContext().getTimeFormat().format(new Date()) + "] " + Logger.getContext().getPrefix()
				+ " [" + LogLevel.INFO.getName() + "] " + this.createProgress());

		if (percent == 100) {
			System.out.println();
			System.out.print("\r");

			//TODO Fire event
		}
	}

	/**
	 * Create a progress bar for the current download
	 *
	 * @return String which show the current progress of the download in a progress bar
	 *
	 * @since 1.0
	 */
	private String createProgress() {
		final int percent = (int) (((double) this.current / this.length) * 100);
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for (int i = 0; i < 25; i++) {
			if (i < percent / 4) {
				stringBuilder.append("=");
			} else if (i == percent / 4) {
				stringBuilder.append(">");
			} else {
				stringBuilder.append(" ");
			}
		}
		stringBuilder.append("]  ").append(this.current / 1024).append("/").append(this.length / 1024).append(" KB");
		return stringBuilder.toString();
	}

}
