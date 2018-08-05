package de.tammo.cloud.core.queue;

import java.util.*;

/**
 * Queue to store elements and to work off the elements
 *
 * @param <Type> Type of the elements which should stored in the {@link Queue}
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
public class Queue<Type> {

	/**
	 * {@link LinkedList} to store the components
	 *
	 * @since 1.0
	 */
	private final List<Type> queue = new LinkedList<>();

	/**
	 * Add an element to the {@link Queue}
	 *
	 * @param queueElement Element, which should added to the {@link Queue}
	 *
	 * @since 1.0
	 */
	public void offer(final Type queueElement) {
		this.queue.add(queueElement);
	}

	/**
	 * Get next element from the {@link Queue}
	 *
	 * @return Next element
	 *
	 * @since 1.0
	 */
	public final Type getFirst() {
		final Type queueElement = this.queue.get(0);
		this.queue.remove(queueElement);
		return queueElement;
	}

	/**
	 * Checks, if the {@link Queue} is empty
	 *
	 * @return If the {@link Queue} is empty
	 *
	 * @since 1.0
	 */
	public final boolean isEmpty() {
		return this.queue.isEmpty();
	}

	/**
	 * Clear the {@link Queue}
	 *
	 * @since 1.0
	 */
	public void clear() {
		this.queue.clear();
	}

}
