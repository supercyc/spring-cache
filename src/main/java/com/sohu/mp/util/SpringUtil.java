package com.sohu.mp.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * spring �����࣬ͨ������ͨJAVA����ֱ�ӻ�ȡSpring��bean.
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
