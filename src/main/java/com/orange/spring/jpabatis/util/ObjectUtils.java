package com.orange.spring.jpabatis.util;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xieyong
 * @date 2020/1/15
 * @Description:
 */
public class ObjectUtils {
    /**
     * 获取对象Field属性对象集合ap
     *
     * @param clazz
     * @return
     */
    public static Map<String, Field> getFieldMap(Class clazz) {
        Map<String, Field> result = new LinkedHashMap<String, Field>(16);
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                result.put(field.getName(), field);
            }
        }
        return result;
    }
//ps：之前漏了一些方法的说明，补充一下，关于ObjectUtils中涉及到的方法
//另外补充一下，其实将Field保存为Map，使用key的方式搜索会更好一点
    /**
     * 获取对象Field属性对象
     *
     * @param clazz
     * @param name  属性名称
     * @return
     */
    public static Field getField(Class clazz, String name) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (name.equals(field.getName())) {
                    field.setAccessible(true);
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * 获取对象Field属性对象
     *
     * @param obj
     * @param name
     * @param <T>
     * @return
     */
    public static <T> Field getField(T obj, String name) {
        return getField(obj.getClass(), name);
    }
}
