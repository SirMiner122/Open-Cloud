package de.tammo.cloud.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Data;

/**
 *
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
public class EventService {

	/**
	 * {@link Map} of all registered {@link Event}s
	 *
	 * @since 1.0
	 */
	private final Map<Class, CopyOnWriteArrayList<MethodData>> methods = new HashMap<>();

	/**
	 * Register one or more {@link Event}s from a instance
	 *
	 * @param target Target instance
	 *
	 * @since 1.0
	 */
	public void registerEvents(final Object target) {
		Arrays.stream(target.getClass().getDeclaredMethods()).filter(method -> method.getParameterCount() > 0 && method.isAnnotationPresent(EventTarget.class)).forEach(method -> {
			final Class<?> eventClass = method.getParameterTypes()[0];
			final MethodData methodData = new MethodData(target, method);

			if (this.methods.containsKey(eventClass)) {
				this.methods.get(eventClass).add(methodData);
			} else {
				this.methods.put(eventClass, new CopyOnWriteArrayList<>(Arrays.asList(methodData)));
			}
		});
	}

	/**
	 * Register on or more {@link Event}s from a class
	 *
	 * @param target Target class
	 *
	 * @since 1.0
	 */
	public void registerEventsFromClass(final Class target) {
		Arrays.stream(target.getDeclaredMethods()).filter(method -> method.getParameterCount() > 0 && method.isAnnotationPresent(EventTarget.class)).forEach(method -> {
			final Class<?> eventClass = method.getParameterTypes()[0];
			final MethodData methodData = new MethodData(target, method);

			if (this.methods.containsKey(eventClass)) {
				this.methods.get(eventClass).add(methodData);
			} else {
				this.methods.put(eventClass, new CopyOnWriteArrayList<>(Arrays.asList(methodData)));
			}
		});
	}

	/**
	 * Fire an {@link Event}
	 *
	 * @param event Event which should be fired
	 *
	 * @since 1.0
	 */
	public void fireEvent(final Event event) {
		final CopyOnWriteArrayList<MethodData> methodDatas = this.methods.get(event.getClass());

		if (methodDatas == null) return;

		methodDatas.forEach(methodData -> {
			if (!methodData.getMethod().isAccessible()) {
				methodData.getMethod().setAccessible(true);
			}
			try {
				methodData.getMethod().invoke(methodData.getSource(), event);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Object of important data to invoke a method reflective
	 *
	 * @since 1.0
	 */
	@Data
	private class MethodData {

		/**
		 * Source instance or class from the method
		 *
		 * @since 1.0
		 */
		private final Object source;

		/**
		 * Method object
		 *
		 * @since 1.0
		 */
		private final Method method;
	}

}
