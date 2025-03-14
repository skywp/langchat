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

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置类，用于解决前后端分离项目中的跨域请求问题。
 * 通过实现 WebMvcConfigurer 接口，重写 addCorsMappings 方法来配置跨域规则。
 *
 * @author tycoding
 * @since 2024/1/5
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 添加跨域映射规则。
     *
     * @param registry 跨域注册器，用于配置跨域规则
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 添加映射路径，这里表示对所有的请求路径都应用跨域规则
        registry.addMapping("/**")
                // 是否允许发送 Cookie，设置为 true 表示允许
               .allowCredentials(true)
                // 设置放行哪些原始域，Spring Boot 2.4.4 及以上版本使用 allowedOriginPatterns("*")，表示允许所有的源
               .allowedOriginPatterns("*")
                // 放行哪些请求方式，这里明确列出了 GET、POST、PUT、DELETE 四种请求方式
               .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                // 也可以使用 .allowedMethods("*") 放行所有的请求方式
                //.allowedMethods("*") 
                // 放行哪些原始请求头部信息，设置为 "*" 表示允许所有的请求头
               .allowedHeaders("*")
                // 暴露哪些原始请求头部信息，设置为 "*" 表示允许客户端获取所有的响应头
               .exposedHeaders("*");
    }
}
