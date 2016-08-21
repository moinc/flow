package nl.agiletech.flow.bot.http;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public final class HttpAdapterUtil {
	@SuppressWarnings("unchecked")
	public static <T> T createUnimplementAdapter(Class<T> httpServletApi) {
		class UnimplementedHandler implements InvocationHandler {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				throw new UnsupportedOperationException(
						"Not implemented: " + method + ", args=" + Arrays.toString(args));
			}
		}

		return (T) Proxy.newProxyInstance(UnimplementedHandler.class.getClassLoader(),
				new Class<?>[] { httpServletApi }, new UnimplementedHandler());
	}
}
