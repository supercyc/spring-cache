package com.sohu.mp.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * spring 工具类，通过它普通JAVA类能直接获取Spring的bean.
 * @since CMS5.0
 * @version 1.0
 * @author bingjiema
 *
 */
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
