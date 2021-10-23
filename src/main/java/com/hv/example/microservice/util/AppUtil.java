package com.hv.example.microservice.util;


import com.hv.example.microservice.dao.entity.UserEntity;

import javax.persistence.Column;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class AppUtil {

    public static String currentDate(){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    public static String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Get Final cause of exception
     * @param t
     * @return Throwable
     */
    public static String getFinalCause(Throwable t){
        if(t.getCause()!=null){
            return getFinalCause(t.getCause());
        }else{
            return t.toString().replaceAll("(\\r|\\n|\\t)", " ");
        }
    }

    /**
     * Get Class name by depth level
     * @param level
     * @return Class Name
     */
    public static String getClassName(int level) {
        return Thread.currentThread().getStackTrace()[level].getClassName();
    }

    /**
     * Get Class name default depth level 2
     * @return Class Name
     */
    public static String getClassName() {
        return getClassName(2);
    }

    /**
     * Get Method name by depth level
     * @param level
     * @return Method Name
     */
    public static String getMethodName(int level) {
        return Thread.currentThread().getStackTrace()[level].getMethodName();
    }

    /**
     * Get Method name default depth level 2
     * @return Method Name
     */
    public static String getMethodName() {
        return getMethodName(2);
    }

    public static String getMethodWithClass(){

        String clazz = getClassName(3);
        clazz =  clazz.substring(clazz.lastIndexOf(".")+1);
        return clazz+"."+getMethodName(3);
    }
}
