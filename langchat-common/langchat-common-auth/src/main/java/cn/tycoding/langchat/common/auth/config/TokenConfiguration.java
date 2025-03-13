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

import cn.dev33.satoken.config.SaTokenConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Token 配置类，用于配置 Sa-Token 的相关参数。
 * Sa-Token 是一个轻量级 Java 权限认证框架，用于快速集成权限认证功能。
 * 该类通过创建一个 SaTokenConfig 的 Bean 来定制 Sa-Token 的默认配置。
 *
 * @author tycoding
 * @since 2024/1/5
 */
@Configuration
public class TokenConfiguration {

    /**
     * 创建并配置 SaTokenConfig Bean。
     * 该 Bean 定义了 Sa-Token 的各项参数，如是否打印日志、Token 名称、超时时间等。
     * 使用 @Primary 注解确保在存在多个 SaTokenConfig Bean 时，优先使用此配置。
     *
     * @return 配置好的 SaTokenConfig 实例
     */
    @Bean
    @Primary
    public SaTokenConfig getTokenConfig() {
        // 创建一个新的 SaTokenConfig 实例，并进行参数配置
        return new SaTokenConfig()
                // 禁止打印 Sa-Token 的调试日志
                .setIsPrint(false)
                // 设置 Token 在请求头中的名称为 "Authorization"
                .setTokenName("Authorization")
                // 设置 Token 的超时时间为 24 小时（以秒为单位）
                .setTimeout(24 * 60 * 60)
                // 设置 Token 的生成样式为 UUID
                .setTokenStyle("uuid")
                // 禁止记录 Sa-Token 的操作日志
                .setIsLog(false)
                // 禁止从 Cookie 中读取 Token
                .setIsReadCookie(false);
    }
}
