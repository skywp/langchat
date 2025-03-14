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

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 该类用于配置 Jackson 的序列化和反序列化行为。
 * Jackson 是一个用于处理 JSON 数据的 Java 库，本配置类可自定义其日期、时间等类型的处理方式。
 * 
 * @author tycoding
 * @since 2024/1/2
 */
@Configuration(proxyBeanMethods = false)
// 当类路径中存在 ObjectMapper 类时，才会加载此配置
@ConditionalOnClass(ObjectMapper.class)
// 在 JacksonAutoConfiguration 之前进行自动配置
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfiguration {

    /**
     * 定义一个 Jackson2ObjectMapperBuilderCustomizer 的 Bean，用于自定义 Jackson 的 ObjectMapper 构建器。
     * 
     * @return 自定义的 Jackson2ObjectMapperBuilderCustomizer 实例
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            // 设置本地化信息为中国
            builder.locale(Locale.CHINA);
            // 设置时区为系统默认时区
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            // 注释掉的代码，若启用则会将全局 Long 类型序列化为字符串
            // builder.serializerByType(Long.class, ToStringSerializer.instance);

            // 设置日期时间的默认格式
            builder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);

            // 配置 LocalDateTime 类型的序列化器
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            // 配置 LocalDate 类型的序列化器
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            // 配置 LocalTime 类型的序列化器，这里原代码有误，应为 LocalTimeSerializer
            builder.serializerByType(LocalTime.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));

            // 配置 LocalDateTime 类型的反序列化器
            builder.deserializerByType(LocalDateTime.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            // 配置 LocalDate 类型的反序列化器
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            // 配置 LocalTime 类型的反序列化器，这里原代码有误，应为 LocalTimeDeserializer
            builder.deserializerByType(LocalTime.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
        };
    }
}
