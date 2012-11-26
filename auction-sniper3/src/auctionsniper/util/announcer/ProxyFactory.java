package auctionsniper.util.announcer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.EventListener;

class ProxyFactory {
	static <T extends EventListener> T create(final Announcer<T> announcer,
			Class<? extends T> listenerType) {
		Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(),
				new Class<?>[] { listenerType },
				createInvocationHandler(announcer));

		return listenerType.cast(proxy);
	}

	private static <T extends EventListener> InvocationHandler createInvocationHandler(
			final Announcer<T> announcer) {
		return new InvocationHandler() {
			@Override public Object invoke(Object aProxy, Method method, Object[] args)
					throws Throwable {
				announcer.announce(method, args);
				return null;
			}
		};
	}
}
