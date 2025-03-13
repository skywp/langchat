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

package cn.tycoding.langchat.common.ai;

import cn.tycoding.langchat.common.ai.properties.ChatProps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 通用AI自动配置类
 * 该类用于启用特定的配置属性类，使Spring Boot能够自动加载和绑定配置文件中的属性。
 * 
 * @author tycoding
 * @since 2024/11/16
 */
@Configuration
// 启用指定的配置属性类，将配置文件中的属性绑定到对应的类实例上
@EnableConfigurationProperties({
        // 启用ChatProps类，使其能够从配置文件中读取并绑定相关属性
        ChatProps.class,
})
public class CommonAiAutoConfiguration {
    // 该类作为配置类，通常不需要额外的实例方法或成员变量，
    // 主要通过注解来声明配置信息
}
