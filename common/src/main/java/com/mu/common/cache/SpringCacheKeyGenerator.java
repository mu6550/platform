package com.mu.common.cache;

import com.mu.common.util.BeanHelper;
import com.mu.common.util.JsonUtil;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 自定义缓存key的生成策略 实现spring cache的KeyGenerator接口
 * 重写方法：generator
 */
@Component
public class SpringCacheKeyGenerator implements KeyGenerator {

    private final static int NO_PARAM_KEY = 0;
    private String keyPrefix = "jf";// key前缀，用于区分不同项目的缓存，建议每个项目单独设置

    @Override
    public Object generate(Object target, Method method, Object... objects) {
        char sp = ':';
        StringBuilder strBuilder = new StringBuilder(30);
        strBuilder.append(keyPrefix);
        strBuilder.append(sp);
        // 类名
        strBuilder.append(target.getClass().getSimpleName());
        strBuilder.append(sp);
        // 方法名
        strBuilder.append(method.getName());
        strBuilder.append(sp);
        if (objects.length > 0) {
            // 参数值
            for (Object object : objects) {
                if (BeanHelper.isSimpleValueType(object.getClass())) {
                    strBuilder.append(object);
                } else {
                    strBuilder.append(JsonUtil.toJson(object).hashCode());
                }
            }
        } else {
            strBuilder.append(NO_PARAM_KEY);
        }
        return strBuilder.toString();
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
