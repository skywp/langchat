/*
 * Copyright (c) 2024 LangChat. TyCoding All Rights Reserved.
 *
 * Licensed under the GNU Affero General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.gnu.org/licenses/agpl-3.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.tycoding.langchat.common.auth.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.strategy.SaStrategy;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.core.util.StrUtil;
import cn.tycoding.langchat.common.core.constant.CacheConst;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * TokenDaoRedis 类实现了 SaTokenDao 接口，用于将 Sa-Token 的数据存储到 Redis 中。
 * 它支持存储字符串和对象类型的数据，并处理数据的读写、过期时间设置等操作。
 * 
 * @author tycoding
 * @since 2024/1/5
 */
@Slf4j
@Component
public class TokenDaoRedis implements SaTokenDao {

    // 日期时间格式化模式
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    // 日期格式化模式
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    // 时间格式化模式
    public static final String TIME_PATTERN = "HH:mm:ss";
    // 日期时间格式化器
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    // 日期格式化器
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    // 时间格式化器
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    // Redis 缓存的前缀
    private static final String prefix = CacheConst.AUTH_PREFIX;

    /**
     * ObjectMapper 对象 (以 public 作用域暴露出此对象，方便开发者二次更改配置)
     *
     * <p> 例如：
     * <pre>
     *      SaTokenDaoRedisJackson redisJackson = (SaTokenDaoRedisJackson) SaManager.getSaTokenDao();
     *      redisJackson.objectMapper.xxx = xxx;
     * 	</pre>
     * </p>
     */
    public ObjectMapper objectMapper;

    /**
     * String 读写专用的 Redis 模板
     */
    public StringRedisTemplate stringRedisTemplate;

    /**
     * Object 读写专用的 Redis 模板
     */
    public RedisTemplate<String, Object> objectRedisTemplate;

    /**
     * 标记：是否已初始化成功
     */
    public boolean isInit;

    /**
     * 对传入的 key 进行处理，添加统一的前缀
     *
     * @param key 原始的 key
     * @return 添加前缀后的 key
     */
    private static String sub(String key) {
        int index = StrUtil.ordinalIndexOf(key, ":", 2);
        return prefix + StrUtil.subSuf(key, index + 1);
    }

    /**
     * 初始化 Redis 相关配置，包括序列化器、ObjectMapper 等
     *
     * @param connectionFactory Redis 连接工厂
     */
    @Autowired
    public void init(RedisConnectionFactory connectionFactory) {
        // 如果已经初始化成功了，就立刻退出，不重复初始化
        if (this.isInit) {
            return;
        }

        // 指定相应的序列化方案
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();

        // 通过反射获取Mapper对象, 增加一些配置, 增强兼容性
        try {
            Field field = GenericJackson2JsonRedisSerializer.class.getDeclaredField("mapper");
            field.setAccessible(true);
            this.objectMapper = (ObjectMapper) field.get(valueSerializer);

            // 配置[忽略未知字段]
            this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // 配置[时间类型转换]
            JavaTimeModule timeModule = new JavaTimeModule();

            // LocalDateTime序列化与反序列化
            timeModule.addSerializer(new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
            timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));

            // LocalDate序列化与反序列化
            timeModule.addSerializer(new LocalDateSerializer(DATE_FORMATTER));
            timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));

            // LocalTime序列化与反序列化
            timeModule.addSerializer(new LocalTimeSerializer(TIME_FORMATTER));
            timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TIME_FORMATTER));

            this.objectMapper.registerModule(timeModule);

            // 重写 SaSession 生成策略
            SaStrategy.instance.createSession = (sessionId) -> new TokenDaoCustomized(sessionId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        // 构建StringRedisTemplate
        StringRedisTemplate stringTemplate = new StringRedisTemplate();
        stringTemplate.setConnectionFactory(connectionFactory);
        stringTemplate.afterPropertiesSet();

        // 构建RedisTemplate
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();

        // 开始初始化相关组件
        this.stringRedisTemplate = stringTemplate;
        this.objectRedisTemplate = template;

        // 打上标记，表示已经初始化成功，后续无需再重新初始化
        this.isInit = true;
    }

    /**
     * 获取指定 key 的字符串值
     *
     * @param key 要获取值的 key
     * @return key 对应的值，如果不存在则返回 null
     */
    @Override
    public String get(String key) {
        key = sub(key);
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 写入字符串值到 Redis 中，并设置存活时间
     *
     * @param key     要写入的 key
     * @param value   要写入的值
     * @param timeout 存活时间，单位为秒
     */
    @Override
    public void set(String key, String value, long timeout) {
        key = sub(key);
        if (timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            stringRedisTemplate.opsForValue().set(key, value);
        } else {
            stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 修改指定 key 的值，过期时间保持不变
     *
     * @param key   要修改的 key
     * @param value 新的值
     */
    @Override
    public void update(String key, String value) {
        key = sub(key);
        long expire = getTimeout(key);
        // -2 = 无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.set(key, value, expire);
    }

    /**
     * 删除指定 key 的值
     *
     * @param key 要删除的 key
     */
    @Override
    public void delete(String key) {
        key = sub(key);
        stringRedisTemplate.delete(key);
    }

    /**
     * 获取指定 key 的剩余存活时间
     *
     * @param key 要查询的 key
     * @return 剩余存活时间，单位为秒
     */
    @Override
    public long getTimeout(String key) {
        key = sub(key);
        return stringRedisTemplate.getExpire(key);
    }

    /**
     * 修改指定 key 的剩余存活时间
     *
     * @param key     要修改的 key
     * @param timeout 新的存活时间，单位为秒
     */
    @Override
    public void updateTimeout(String key, long timeout) {
        key = sub(key);
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getTimeout(key);
            if (expire == SaTokenDao.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                this.set(key, this.get(key), timeout);
            }
            return;
        }
        stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取指定 key 的对象值
     *
     * @param key 要获取值的 key
     * @return key 对应的值，如果不存在则返回 null
     */
    @Override
    public Object getObject(String key) {
        key = sub(key);
        return objectRedisTemplate.opsForValue().get(key);
    }

    /**
     * 写入对象值到 Redis 中，并设置存活时间
     *
     * @param key     要写入的 key
     * @param object  要写入的对象
     * @param timeout 存活时间，单位为秒
     */
    @Override
    public void setObject(String key, Object object, long timeout) {
        key = sub(key);
        if (timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            objectRedisTemplate.opsForValue().set(key, object);
        } else {
            objectRedisTemplate.opsForValue().set(key, object, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 修改指定 key 的对象值，过期时间保持不变
     *
     * @param key    要修改的 key
     * @param object 新的对象值
     */
    @Override
    public void updateObject(String key, Object object) {
        key = sub(key);
        long expire = getObjectTimeout(key);
        // -2 = 无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.setObject(key, object, expire);
    }

    /**
     * 删除指定 key 的对象值
     *
     * @param key 要删除的 key
     */
    @Override
    public void deleteObject(String key) {
        key = sub(key);
        objectRedisTemplate.delete(key);
    }

    /**
     * 获取指定 key 的对象值的剩余存活时间
     *
     * @param key 要查询的 key
     * @return 剩余存活时间，单位为秒
     */
    @Override
    public long getObjectTimeout(String key) {
        key = sub(key);
        return objectRedisTemplate.getExpire(key);
    }

    /**
     * 修改指定 key 的对象值的剩余存活时间
     *
     * @param key     要修改的 key
     * @param timeout 新的存活时间，单位为秒
     */
    @Override
    public void updateObjectTimeout(String key, long timeout) {
        key = sub(key);
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getObjectTimeout(key);
            if (expire == SaTokenDao.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                this.setObject(key, this.getObject(key), timeout);
            }
            return;
        }
        objectRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 搜索符合条件的数据
     *
     * @param prefix    前缀
     * @param keyword   关键词
     * @param start     起始位置
     * @param size      数量
     * @param sortType  排序类型
     * @return 符合条件的数据列表
     */
    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        prefix = sub(prefix);
        Set<String> keys = stringRedisTemplate.keys(prefix + "*" + keyword + "*");
        List<String> list = new ArrayList<>(keys);
        return SaFoxUtil.searchList(list, start, size, sortType);
    }
}
