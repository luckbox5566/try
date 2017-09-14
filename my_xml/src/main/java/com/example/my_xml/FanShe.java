package com.example.my_xml;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenlikang
 */

public class FanShe {
    /**
     * 得到非空的属性值列表
     *
     * @param list
     *            属性值列表
     * @return
     */
    public List<String> getPropertyVal(List<String> list) {

        List<String> propertyValues = new ArrayList<String>();
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                propertyValues.add(list.get(i));
            }
        }
        return propertyValues;
    }

    /**
     * 得到cla类的属性列表
     *
     * @param cla
     *            要反射的类
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List<String> getPropertyNames(Class cla) {
        return getPropertyNames(cla, false);
    }

    /**
     * 得到cla类的属性列表
     *
     * @param cla
     *            要反射的类
     * @param hasSuper
     *            是否包含父类的属性
     * @return
     */
    public List<String> getPropertyNames(
            @SuppressWarnings("rawtypes") Class cla, boolean hasSuper) {
        List<String> list = new ArrayList<String>();
        addPropertyNames(cla, list);
        if (hasSuper) {
            addPropertyNames(cla.getSuperclass(), list);
        }
        return list;

    }

    /**
     * 向集合中添加相应类的属性名
     *
     * @param cla
     * @param list
     */
    private void addPropertyNames(@SuppressWarnings("rawtypes") Class cla,
                                  List<String> list) {
        Field[] fs = cla.getDeclaredFields();
        // fs=cla.getFields();加了这个的话就只获得public 公有的
        for (Field f : fs) {
            if (!Modifier.toString(f.getModifiers()).contains("final")) {// 去掉常量
                list.add(f.getName());
            }
        }
    }

    /**
     * 得到与属性列表相对应的属性值列表
     *
     * @param shuxingList
     *            属性列表
     * @param obj
     *            实体类
     * @return 与属性对应的属性值列表
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @SuppressWarnings("rawtypes")
    public List getValueList(List<String> shuxingList, Object obj) {
        return getValueList(shuxingList, obj, false);
    }

    /**
     * 得到与属性列表相对应的属性值列表
     *
     * @param shuxingList
     *            属性列表
     * @param obj
     *            实体类
     * @param isSuper
     *            是否取该类的父类的属性集合
     * @return 与属性对应的属性值列表
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @SuppressWarnings("rawtypes")
    public List getValueList(List<String> shuxingList, Object obj,
                             boolean isSuper) {
        List valueList = new ArrayList();
        // Class cla = null;
        int i = 0;
        try {
            valueList = addOption(shuxingList, obj, isSuper, valueList, i);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return valueList;

    }

    /**
     * 向属性值集合中添加对应的值
     *
     * @param shuxingList
     *            属性列表
     * @param obj
     *            实体类
     * @param isSuper
     *            是否取该类的父类的属性集合
     * @param valueList
     *            属性值集合
     * @param i
     *            属性在集合中的位置
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("rawtypes")
    private List addOption(List<String> shuxingList, Object obj,
                           boolean isSuper, List valueList, int i)
            throws NoSuchFieldException, IllegalAccessException {
        Class cla;
        try {
            // if (isSuper) {
            // cla = obj.getClass().getSuperclass();
            // } else {
            cla = obj.getClass();
            // }

            // valueList = new ArrayList();
            for (; i < shuxingList.size(); i++) {
                addValue(shuxingList.get(i), obj, valueList, cla);
            }
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            if (isSuper) {
                cla = obj.getClass().getSuperclass();
                addValue(shuxingList.get(i), obj, valueList, cla);
                if (i != shuxingList.size() - 1) {
                    addOption(shuxingList, obj, isSuper, valueList, ++i);
                }
            }
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return valueList;
    }

    /**
     * 添加属性值到集合中
     *
     * @param shuxing
     *            属性名
     * @param obj
     *            实体类
     * @param valueList
     *            属性值列表
     * @param cla
     *            反射的类
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    private void addValue(String shuxing, Object obj,
                          @SuppressWarnings("rawtypes") List valueList,
                          @SuppressWarnings("rawtypes") Class cla)
            throws NoSuchFieldException, IllegalAccessException {
        Field f = cla.getDeclaredField(shuxing);
        f.setAccessible(true);// 加了这句才能改私有的值
        // 得到属性值
        Object str = f.get(obj);
        valueList.add(str);
    }

    /**
     * 将obj类的属性列表一个个赋值
     *
     * @param obj
     *            要反射的类
     * @param propertyNames
     *            类的属性列表
     * @param propertyVales
     *            与属性相对应的属性值列表
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("rawtypes")
    public void method(Object obj, List<String> propertyNames,
                       List propertyVales) {
        try {
            Class cla = obj.getClass();
            for (int i = 0, len = propertyNames.size(); i < len; i++) {
                // Log.i("propertyNames"+i, propertyNames.get(i)+"");
                Field f = cla.getDeclaredField(propertyNames.get(i).toString());
                f.setAccessible(true);// 加了这句才能改私有的值
                f.set(obj, propertyVales.get(i));
            }
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 给obj类的单个属性赋值
     *
     * @param obj
     *            要反射的类
     * @param shuXing
     *            要赋值的属性
     * @param value
     *            要给属性赋予的值
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public void method(Object obj, String shuXing, Object value) {
        @SuppressWarnings("rawtypes")
        Class cla = obj.getClass();
        Field f;
        try {
            f = cla.getDeclaredField(shuXing);
            // 加了这句才能改私有的值
            f.setAccessible(true);
            // 为属性赋值
            f.set(obj, value);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
