package cn.yifan.geekgen.utils;

/**
 * @FileName ThreadLocalUtil
 * @Description
 * @Author yifan
 * @date 2025-02-26 15:48
 **/

public class ThreadLocalUtil {

    // 创建ThreadLocal对象
    private static final ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    // 设置值到ThreadLocal
    public static void setValue(Object value) {
        threadLocal.set(value);
    }

    // 从ThreadLocal获取值
    public static Object getValue() {
        return threadLocal.get();
    }

    // 清除ThreadLocal中的值
    public static void removeValue() {
        threadLocal.remove();
    }

}
