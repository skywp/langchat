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

package cn.tycoding.langchat.common.core.component;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * Redis 配置类，用于配置 RedisTemplate 和 StringRedisTemplate 的相关序列化方式和连接工厂。
 * 该类会被 Spring 容器扫描并加载，以完成 Redis 操作的基础配置。
 *
 * @author tycoding
 * @since 2024/1/2
 */
@Component
@EnableCaching // 启用 Spring 的缓存注解功能
@Configuration // 表明这是一个配置类
@AllArgsConstructor // 使用 Lombok 生成全参数构造函数
public class RedisConfiguration {

    /**
     * Redis 连接工厂，用于创建 Redis 连接
     */
    private final RedisConnectionFactory connectionFactory;

    /**
     * 创建一个 RedisTemplate 实例，用于操作 Redis 数据库。
     * 该方法配置了 RedisTemplate 的键和值的序列化方式，以提高存储和读取数据的效率。
     *
     * @return 配置好的 RedisTemplate 实例
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        // 创建 String 类型的键序列化器
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        // 创建通用的 Jackson2 JSON 序列化器，用于序列化值
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();

        // 创建 RedisTemplate 实例
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置 Redis 连接工厂
        redisTemplate.setConnectionFactory(connectionFactory);
        // 设置键的序列化器
        redisTemplate.setKeySerializer(keySerializer);
        // 设置哈希键的序列化器
        redisTemplate.setHashKeySerializer(keySerializer);
        // 设置值的序列化器
        redisTemplate.setValueSerializer(valueSerializer);
        // 设置哈希值的序列化器
        redisTemplate.setHashValueSerializer(valueSerializer);
        // 初始化 RedisTemplate 的属性
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 创建一个 StringRedisTemplate 实例，用于专门操作 Redis 中的字符串类型数据。
     * StringRedisTemplate 是 RedisTemplate 的一个特殊化版本，键和值都使用 String 类型。
     *
     * @return 配置好的 StringRedisTemplate 实例
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        // 创建 StringRedisTemplate 实例
        StringRedisTemplate stringTemplate = new StringRedisTemplate();
        // 设置 Redis 连接工厂
        stringTemplate.setConnectionFactory(connectionFactory);
        // 初始化 StringRedisTemplate 的属性
        stringTemplate.afterPropertiesSet();
        return stringTemplate;
    }
}
