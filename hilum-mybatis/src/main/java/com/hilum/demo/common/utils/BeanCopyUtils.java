package com.hilum.demo.common.utils;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class BeanCopyUtils {

    final static Logger log = LoggerFactory.getLogger(BeanCopyUtils.class);


    private BeanCopyUtils() {
    }

    static {
        ConvertUtils.register(new Converter() {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public Object convert(Class type, Object value) {
                if (value == null)
                    return null;
                if (type.equals(Date.class)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 非线程安全的，只能在方法体里
                    try {
                        if (value instanceof Date) {
                            return sf.parse(sf.format(value));
                        }
                        return sf.parse(value.toString());
                    } catch (ParseException e) {
                        throw new RuntimeException("日期转换错误", e);
                    }
                }
                return null;
            }
        }, Date.class);

        ConvertUtils.register(new Converter() {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public Object convert(Class type, Object value) {
                if (value == null)
                    return null;
                if (type.equals(BigDecimal.class)) {
                    return new BigDecimal(value.toString());
                }

                return null;
            }
        }, BigDecimal.class);
    }


    /**
     * <转换单个对象> 此方法采用apache的BeanUtils实现，此类支持类型不匹配时自动转换<br/>
     * 建议：如果有些类型需要自动转换并且赋值的情况下，请使用此方法，性能一般。
     * 
     * @param sourceObj
     *            要进行转换的源数据对象
     * @param clazz
     *            要转换成的目标对象的Class类型
     * @return 返回转换后的对象
     */
    public static <T, D> T populateTbyDByApache(D sourceObj, Class<T> clazz) {
        if (sourceObj == null)
            return null;
        T t = null;
        try {
            t = (T) clazz.newInstance();
            BeanUtils.copyProperties(t, sourceObj);
        } catch (Exception e) {
            throw new RuntimeException("execute populateTbyDByApache error ", e);
        }

        return t;
    }


    /**
     * <转换list对象> 此方法采用apache的BeanUtils实现，此类支持类型不匹配时自动转换<br/>
     * 建议：如果有些类型需要自动转换并且赋值的情况下，请使用此方法，性能一般。
     * 
     * @param
     *
     * @param clazz
     *            要转换成的目标对象的Class类型
     * @return 返回转换后的List对象集合
     */
    public static <T, D> List<T> populateTListbyDListByApache(List<D> sourceObjs, Class<T> clazz) {
        if (sourceObjs == null)
            return null;
        int len = sourceObjs.size();
        List<T> ts = new ArrayList<T>(len);
        T t = null;
        for (int i = 0; i < len; i++) {
            D d = sourceObjs.get(i);
            t = populateTbyDByApache(d, clazz);
            ts.add(t);
        }
        return ts;
    }


    /**
     * <转换单个对象> 此方法采用spring的BeanUtils实现，不支持类型自动转换,仅copy部分属性<br/>
     * 建议：如果能够预知不需要类型转换的情况下，请使用此方法，性能较好。
     * 
     * @param sourceObj
     *            要进行转换的源数据对象
     * @param clazz
     *            要转换成的目标对象的Class类型
     * @return 返回转换后的对象
     */
    public static <T, D> T populateTbyDBySpring(D sourceObj, Class<T> clazz) {
        if (sourceObj == null)
            return null;
        T t = null;
        try {
            t = (T) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("execute populateTbyDBySpring error ", e);
        }
        org.springframework.beans.BeanUtils.copyProperties(sourceObj, t);
        return t;
    }


    /**
     * <转换list对象> 此方法采用spring的BeanUtils实现，不支持类型自动转换,仅copy部分属性<br/>
     * 建议：如果能够预知不需要类型转换的情况下，请使用此方法，性能较好。
     * 
     * @param sourceObjs
     *            要进行转换的源数据对象
     * @param clazz
     *            要转换成的目标对象的Class类型
     * @return 返回转换后的List对象集合
     */
    public static <T, D> List<T> populateTListbyDListBySpring(List<D> sourceObjs, Class<T> clazz) {
        if (sourceObjs == null)
            return null;
        int len = sourceObjs.size();
        List<T> ts = new ArrayList<T>(len);
        T t = null;
        for (int i = 0; i < len; i++) {
            D d = sourceObjs.get(i);
            t = populateTbyDBySpring(d, clazz);
            ts.add(t);
        }
        return ts;
    }

    private static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();


    public static BeanCopier getBeanCopierObj(Object sourceObj, Class<?> targetClazz) {
        String key = generateMapKey(sourceObj, targetClazz);
        if (beanCopierMap.containsKey(key)) {
            return beanCopierMap.get(key);
        }
        BeanCopier beanCopier = BeanCopier.create(sourceObj.getClass(), targetClazz, false);
        beanCopierMap.put(key, beanCopier);
        return beanCopier;
    }


    private static String generateMapKey(Object sourceObj, Class<?> targetClazz) {
        return sourceObj.getClass().toString() + targetClazz;
    }


    /**
     * <转换单个对象> 此方法采用cglib的BeanCopier实现，不支持类型自动转换,不copy任何属性<br/>
     * 建议：如果能够预知不需要类型转换的情况下，请使用此方法，性能最优。
     * 注意：对于getter/setter方法不匹配的情况下，此方法会抛出异常，不copy任何属性。
     * 
     * @param sourceObj
     *            要进行转换的源数据对象
     * @param clazz
     *            要转换成的目标对象的Class类型
     * @return 返回转换后的对象
     */
    public static <T, D> T populateTbyDByCglib(D sourceObj, Class<T> clazz) {
        if (sourceObj == null)
            return null;
        T t = null;
        try {
            t = (T) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("execute populateTbyDByCglib error ", e);
        }
        BeanCopier beanCopier = getBeanCopierObj(sourceObj, clazz);
        beanCopier.copy(sourceObj, t, null);
        return t;
    }


    /**
     * <转换List对象> 此方法采用cglib的BeanCopier实现，不支持类型自动转换,如果出现类型不匹配的话，不copy任何属性<br/>
     * 建议：如果能够预知不需要类型转换的情况下，请使用此方法，性能最优。
     * 注意：对于getter/setter方法不匹配的情况下，此方法会抛出异常，不copy任何属性。
     * 
     * @param
     *
     * @param clazz
     *            要转换成的目标对象的Class类型
     * @return 返回转换后的对象
     */
    public static <T, D> List<T> populateTListbyDListByCglib(List<D> sourceObjs, Class<T> clazz) {
        if (sourceObjs == null)
            return null;
        int len = sourceObjs.size();
        List<T> ts = new ArrayList<T>(len);
        T t = null;
        BeanCopier beanCopier = getBeanCopierObj(sourceObjs.get(0), clazz);
        try {
            for (int i = 0; i < len; i++) {
                D d = sourceObjs.get(i);
                t = (T) clazz.newInstance();
                beanCopier.copy(d, t, null);
                ts.add(t);
            }
        } catch (Exception e) {
            throw new RuntimeException("execute populateTListbyDListByCglib error ", e);
        }
        return ts;
    }


    /**
     * apache实现。该方法用于实现扩展原有对象的属性值(即对原有对象的某些字段重新copy属性值)
     * 
     * @param sourceObj
     * @param targetObj
     * @return
     */
    public static <T, D> T populateTbyDByApache(D sourceObj, T targetObj) {
        if (sourceObj == null)
            return null;
        try {

            BeanUtils.copyProperties(targetObj, sourceObj);
        } catch (Exception e) {
            throw new RuntimeException("execute populateTbyDByApache error ", e);
        }
        return targetObj;
    }


    /**
     * Spring实现。该方法用于实现扩展原有对象的属性值(即对原有对象的某些字段重新copy属性值)
     * 
     * @param sourceObj
     * @param targetObj
     * @return
     */
    public static <T, D> T populateTbyDBySpring(D sourceObj, T targetObj) {
        if (sourceObj == null)
            return null;
        org.springframework.beans.BeanUtils.copyProperties(sourceObj, targetObj);
        return targetObj;
    }


    /**
     * Cglib实现。该方法用于实现扩展原有对象的属性值(即对原有对象的某些字段重新copy属性值)
     * 
     * @param sourceObj
     * @param targetObj
     * @return
     */
    public static <T, D> T populateTbyDByCglib(D sourceObj, T targetObj) {
        if (sourceObj == null)
            return null;
        BeanCopier beanCopier = getBeanCopierObj(sourceObj, targetObj.getClass());
        beanCopier.copy(sourceObj, targetObj, null);
        return targetObj;
    }


}
