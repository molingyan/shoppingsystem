package com.shop.utils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapToBeanUtil<T> {
    private final static SimpleDateFormat yMdHms_sdf_util = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Map<String, Object> convertBeanToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if (null == value) {
                        map.put(key, "");
                    } else {
                        map.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * map转化成实体类
     *
     * @param tempMap
     * @param classModel
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T convertMapToBean(Map<String, Object> tempMap, Class<T> classModel) throws InstantiationException, IllegalAccessException {
        T tempClass = classModel.newInstance();
        Class<?> clz = tempClass.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            String paramType = field.getType().toString();//.substring(field.getType().toString().lastIndexOf('.')+1);
            field.setAccessible(true);
            try {
                if (tempMap.containsKey(field.getName()) && null != tempMap.get(field.getName())) { //如果map集合存在与属性名相同的键
                    String tmValue = tempMap.get(field.getName()).toString();//获取目标类对应的属性值
                    if (("class java.lang.Integer".equals(paramType) || "int".equals(paramType)) && !tmValue.equals("")) {
                        field.set(tempClass, Integer.valueOf(tmValue.trim())); //把属性值赋予给目标类对应属性
                    } else if ("class java.lang.String".equals(paramType)) {
                        field.set(tempClass, tmValue.trim()); //把属性值赋予给目标类对应属性
                    } else if ("class java.math.BigDecimal".equals(paramType) && !tmValue.equals("")) {
                        field.set(tempClass, new BigDecimal(tmValue.trim())); //把属性值赋予给目标类对应属性
                    } else if (("class java.lang.Double".equals(paramType) || "double".equals(paramType)) && !tmValue.equals("")) {
                        field.set(tempClass, new Double(tmValue.trim())); //把属性值赋予给目标类对应属性
                    } else if (("class java.lang.Long".equals(paramType) || "long".equals(paramType)) && !tmValue.equals("")) {
                        field.set(tempClass, new Long(tmValue.trim())); //把属性值赋予给目标类对应属性
                    } else if (("class java.lang.Short".equals(paramType) || "short".equals(paramType)) && !tmValue.equals("")) {
                        field.set(tempClass, new Short(tmValue.trim())); //把属性值赋予给目标类对应属性
                    } else if (("class java.lang.Float".equals(paramType) || "float".equals(paramType)) && !tmValue.equals("")) {
                        field.set(tempClass, new Float(tmValue.trim())); //把属性值赋予给目标类对应属性
                    } else if ("class java.util.Date".equals(paramType) && !tmValue.equals("")) {
                        field.set(tempClass, yMdHms_sdf_util.parse(tmValue.trim())); //把属性值赋予给目标类对应属性
                    } else if ("class java.sql.Date".equals(paramType) && !tmValue.equals("")) {
                        long time = yMdHms_sdf_util.parse(tmValue.trim()).getTime();
                        field.set(tempClass, new Date(time)); //把属性值赋予给目标类对应属性
                    } else {
                        field.set(tempClass, tmValue);//把属性值赋予给目标类对应属性
                    }
                }
            } catch (Exception e) {
                System.out.println("转换异常：" + e.getMessage());
            }
        }
        return tempClass;
    }










}