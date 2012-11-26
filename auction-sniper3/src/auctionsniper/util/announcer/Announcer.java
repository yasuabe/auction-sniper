package auctionsniper.util.announcer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Announcer<T extends EventListener> {
	private final T       proxy;
	private final List<T> listeners = new ArrayList<T>();
	
	public Announcer(Class<? extends T> listenerType) {
		proxy = ProxyFactory.create(this, listenerType);
	}
	public void addListener(T listener) { listeners.add(listener); }
	
	public void removeListener(T listener) {	listeners.remove(listener); }
	
	public T announce() { return proxy; }

	void announce(Method m, Object[] args) {
		try { invokeAll(m, args); }
		catch (IllegalAccessException e) { couldNotInvokeListener(e); }
		catch (InvocationTargetException e) { handleInvocationTargetException(e); }
	}
	private void handleInvocationTargetException(InvocationTargetException e) { 
		Throwable cause = e.getCause();
        
		if (cause instanceof RuntimeException) throw (RuntimeException)cause;
		if (cause instanceof Error)            throw (Error)cause;
		throw new UnsupportedOperationException("listener threw exception", cause);
	}
	private void couldNotInvokeListener(IllegalAccessException e) {
		throw new IllegalArgumentException("could not invoke listener", e);
	}
	private void invokeAll(Method m, Object[] args)
			throws IllegalAccessException, InvocationTargetException {

		for (T listener : listeners) m.invoke(listener, args);
	}
	public static <T extends EventListener> Announcer<T> to(Class<? extends T> listenerType) {
		return new Announcer<T>(listenerType);
	}
}
