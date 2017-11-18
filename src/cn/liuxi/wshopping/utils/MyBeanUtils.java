package cn.liuxi.wshopping.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.util.Map;

public class MyBeanUtils {


    /**
     * 高级封装不需要new Javabean
     * 例如：
     *    User user = MybeanUtils.populate(User.class,request.getParameterMap() );
     * @param beanClass
     * @param properties
     * @param <T>
     * @return
     */
    public static <T> T populate(Class<T> beanClass,Map<String,String[]> properties) {

        try {

            //TODO 1使用反射创建实例
            T bean = beanClass.newInstance();

            //TODO 2.1创建beanUtils提供的时间转换器
            DateConverter dateConverter = new DateConverter();

            //TODO 2.2设置需要转换的格式
            dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"});

            //TODO 2.3注册转换器
            ConvertUtils.register(dateConverter, java.util.Date.class);

            //TODO 3封装数据
            BeanUtils.populate(bean,properties);

            return bean;

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }



}
