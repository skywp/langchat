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

package cn.tycoding.langchat.common.auth.interceptor;

import cn.dev33.satoken.interceptor.SaInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 认证拦截器配置类，实现 WebMvcConfigurer 接口，用于注册拦截器。
 * 该类会配置验证码拦截器和 Sa-Token 拦截器，并指定它们的拦截路径。
 *
 * @author tycoding
 * @since 2024/1/5
 */
@Component
@AllArgsConstructor
public class AuthInterceptor implements WebMvcConfigurer {

    /**
     * Redis 模板，用于操作 Redis 中的字符串类型数据，供验证码拦截器使用。
     */
    private final StringRedisTemplate redisTemplate;

    /**
     * 重写 WebMvcConfigurer 接口的 addInterceptors 方法，用于注册拦截器。
     *
     * @param registry 拦截器注册器，用于添加拦截器和配置拦截路径。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册验证码拦截器，指定该拦截器拦截 /auth/login 路径的请求
        // 验证码拦截器会使用 redisTemplate 进行验证码相关的操作
        registry.addInterceptor(new CaptchaInterceptor(redisTemplate)).addPathPatterns("/auth/login");
        // 注册 Sa-Token 拦截器，指定该拦截器拦截所有路径的请求
        // Sa-Token 拦截器用于处理系统的认证和授权逻辑
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }
}
