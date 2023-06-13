package alexey.grizly.com.users.utils;

public class DBExceptionUtils {
    public static <T> T unwrapCause(Class<T> clazz, Throwable e) {
        while (!clazz.isInstance(e) && e.getCause() != null && e != e.getCause()) {
            e = e.getCause();
        }
        return clazz.isInstance(e) ? clazz.cast(e) : null;
    }
}
