package netty.web.scoket.server.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 获取 SpringBeanFactory
 *
 * @author qiding
 */
@Component
public final class SpringBeanFactory implements ApplicationContextAware {

    private static ApplicationContext CONTENT;

    public static <T> T getBean(Class<T> c) {
        return CONTENT.getBean(c);
    }


    public static <T> T getBean(String name, Class<T> clazz) {
        return CONTENT.getBean(name, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) CONTENT.getBean(name);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        CONTENT = applicationContext;
    }

}
