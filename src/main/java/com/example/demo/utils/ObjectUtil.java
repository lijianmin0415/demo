package com.example.demo.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class ObjectUtil {


	private ObjectUtil(){}
	
	
	/**
	 * 判断传入的对象是否为空
	 * @param obj 对象类型有:String,StringBuffer,StringBuilder,List,Set,Map,数组
	 * @return true/false 为空/不为空
	 */
	public static boolean isEmpty(Object obj){
		if(obj == null){
			return true;
		}
		if(obj instanceof String){
			String str = (String)obj;
			return "underfined".equals(str) || "null".equals(str.toLowerCase()) || str.trim().isEmpty() || str.length() == 0;
		}
		if(obj instanceof StringBuffer){
			StringBuffer sb = (StringBuffer)obj;
			return sb.length() == 0;
		}
		if(obj instanceof StringBuilder){
			StringBuilder sb = (StringBuilder)obj;
			return sb.length() == 0;
		}
		if(obj instanceof Collection<?>){
			Collection<?> c = (Collection<?>)obj;
			return c.isEmpty() || c.size()==0;
		}
		if(obj instanceof Map<?, ?>){
			Map<?,?> map = (Map<?,?>)obj;
			return map.isEmpty() || map.keySet().isEmpty() || map.size() ==0;
		}
		if(obj.getClass().isArray()){
			Object[] objs = (Object[])obj;
			boolean b = false;
			for(Object o : objs){
				if(isEmpty(o)){
					b = true;
					break;
				}
			}
			return b;
		}
		return false;
	}
	
	/**
	 * 判断对象不为空
	 * @param obj 对象类型有:String,StringBuffer,StringBuilder,List,Set,Map,数组
	 * @return
	 */
	public static boolean isNotEmpty(Object obj){
		return !isEmpty(obj);
	}
	
	/**
	 * 将map转换为java实体类
	 * 注：map的key值必须和java实体类的属性名一致
	 * @param <T>
	 * @param map map集合
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static <T> Object mapToBean(Map<String, Object> map,Class<T> clazz) throws Exception{
        T t = clazz.newInstance();
        for (String key : map.keySet()){
        	try{
        		Field temFiels = clazz.getDeclaredField(key);
                temFiels.setAccessible(true);
                //若map的key值和实体的属性名一致，则对实体的属性赋值
                if(temFiels.getName().equals(key)){
                	if(temFiels.getClass().equals(int.class)){
                		String val =  String.valueOf(map.get(key));
                		temFiels.set(t, Integer.parseInt(val));
                	}else{
                		temFiels.set(t, String.valueOf(map.get(key)));
                	}
                }
        	}catch(Exception e){
        	}
        }
        return t;
    }
	
	/**
	 * 将传入的参数转换为int类型
	 * 注：若不能转换，则返回 0
	 * @param obj
	 * @return
	 */
	public static int objToInt(Object obj){
		if(isEmpty(obj)){
			return 0;
		}
		try{
			int i = Integer.parseInt(String.valueOf(obj));
			return i;
		}catch(Exception e){}
		return 0;
	}
	
	/**
	 * 将传入的参数转换为String类型
	 * @param obj
	 * @return
	 */
	public static String objToString(Object obj){
		return String.valueOf(obj);
	}
	


}
