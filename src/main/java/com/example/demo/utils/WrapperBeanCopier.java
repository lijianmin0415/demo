package com.example.demo.utils;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 封装 cglib 的 BeanCopier 工具类，实现对象属性的 copy <br/>
 * 主要用来做 DO、DTO、VO 之间的属性 copy 工作，cglib 的 bean copy 比 spring 和 apache 的 BeanUtils 性能要好 10 倍左右，
 * 主要性能瓶颈出在 BeanCopier 的创建上，所以封装工具类把创建过的 BeanCopier 对象放在缓存中，可以重用<br/>
 * 注：此类封装的方法未对类型做处理，只能 copy 名称和类型都相同的属性
 * 
 * @author zhao.wang Dec 10, 2019
 *
 */
public class WrapperBeanCopier {
    // BeanCopier 缓存
    private static final Map<String, BeanCopier> bean_copier_cache = new ConcurrentHashMap<>();
    // 高性能反射类缓存，ConstructorAccess 能通过字节码的方式生成反射对象
    @SuppressWarnings("rawtypes")
    private static final Map<String, ConstructorAccess> constructor_access_cache = new ConcurrentHashMap<>();

    private WrapperBeanCopier() {
    }

    /**
     * 将源对象的属性 copy 到目标对象，只能 copy 名称和类型都相同的属性
     * 
     * @param source
     * @param target
     * @author zhao.wang Dec 10, 2019
     */
    public static void copyProperties(Object source, Object target) {
        if(null == source) {
            return;
        }
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    /**
     * 将源对象 copy 成目标类的对象，只能 copy 名称和类型都相同的属性
     * 
     * @param source
     * @param targetClass
     * @return
     * @author zhao.wang Dec 10, 2019
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        T t = null;
        if(null == source) {
            return t;
        }
        try {
            t = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("创建目标类 " + targetClass + " 的新实例失败：" + e.getMessage());
        }
        copyProperties(source, t);
        return t;
    }

    /**
     * 将源对象集合 copy 成目标类的对象集合，只能 copy 名称和类型都相同的属性
     * 
     * @param sourceList
     * @param targetClass
     * @return
     * @author zhao.wang Dec 10, 2019
     */
    public static <T> List<T> copyPropertiesOfList(List<?> sourceList, Class<T> targetClass) {
        if (null == sourceList || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        ConstructorAccess<T> constructorAccess = getConstructorAccess(targetClass);
        List<T> resultList = new ArrayList<>(sourceList.size());
        for (Object o : sourceList) {
            T t = constructorAccess.newInstance();
            copyProperties(o, t);
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * 使用原类和目标类的全限定名拼接起来构成 key
     * 
     * @param sourceClass
     * @param targetClass
     * @return
     * @author zhao.wang Dec 10, 2019
     */
    private static String generateKey(Class<?> sourceClass, Class<?> targetClass) {
        return sourceClass.getName() + targetClass.getName();
    }

    /**
     * 获取 BeanCopier 对象
     * 
     * @param sourceClass
     * @param targetClass
     * @return
     * @author zhao.wang Dec 10, 2019
     */
    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> targetClass) {
        String beanKey = generateKey(sourceClass, targetClass);
        BeanCopier copier = bean_copier_cache.get(beanKey);
        if (null == copier) {
            copier = BeanCopier.create(sourceClass, targetClass, false);
            bean_copier_cache.put(beanKey, copier);
        }
        return copier;
    }

    /**
     * 获取构造器访问器 ConstructorAccess
     * 
     * @param targetClass
     * @return
     * @author zhao.wang Dec 10, 2019
     */
    private static <T> ConstructorAccess<T> getConstructorAccess(Class<T> targetClass) {
        @SuppressWarnings("unchecked")
        ConstructorAccess<T> constructorAccess = constructor_access_cache.get(targetClass.getName());
        if (null == constructorAccess) {
            try {
                constructorAccess = ConstructorAccess.get(targetClass);
                constructorAccess.newInstance();
                constructor_access_cache.put(targetClass.toString(), constructorAccess);

            } catch (Exception e) {
                throw new RuntimeException("创建目标类 " + targetClass + " 的新实例失败：" + e.getMessage());
            }
        }
        return constructorAccess;
    }

}
