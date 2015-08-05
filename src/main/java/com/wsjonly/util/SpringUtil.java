package com.wsjonly.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringUtil implements  ApplicationContextAware{
    private static ApplicationContext ctx;


     public static void main(String[] args) {

     }

     public void displayAppContext() {
       System.out.println(ctx);
     }

    public static Object getBean(String name){
        return ctx.getBean(name);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx=applicationContext;
    }
}
