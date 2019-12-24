package com.example.myelastisearch.utils;

import com.example.myelastisearch.vo.EmployeeVo;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BeanUtil {

    //将map转为对应的类型
    public static <T>T map2Bean(Map map,Class<T> type)throws Exception{
        //通过反射获取对应的类型
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        //创建对象实例
        Object obj = type.newInstance();

        //获取对应的属性集合
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor property:propertyDescriptors){
            //获取属性名称
            String name = property.getName();
            //如果map包含对应的键
            if(map.containsKey(name)){
                Object value = map.get(name);
                //如果属性值是主键
                if("id".equals(value)){
                    //调用反射将值赋予对象实例
                    property.getWriteMethod().invoke(obj,Long.parseLong(value+""));
                    //属性值不是主键
                }else{
                    property.getWriteMethod().invoke(obj,value);
                }
            }
        }

        return (T)obj;
    }

    //将bean转为的map
    public static Map beanTtoMap(Object obj)throws Exception{
        //获取对象的class对象
        Class type = obj.getClass();
        //创建返回对象
        Map map = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor descriptor:propertyDescriptors){
            String name = descriptor.getName();
            if(!"class".equals(name)){
                Method readMethod = descriptor.getReadMethod();
                Object invoke = readMethod.invoke(obj);
                if(invoke!=null){
                    map.put(name,invoke);
                }else{
                    map.put(name,"");
                }
            }
        }
        return map;
    }


}
