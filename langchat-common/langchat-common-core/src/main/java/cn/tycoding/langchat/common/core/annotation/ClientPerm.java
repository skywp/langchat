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

package cn.tycoding.langchat.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义AIGC用户权限注解。
 * 此注解用于标记需要特定AIGC用户权限才能访问的方法。
 * 可以在方法上添加该注解，并指定相应的权限值，以便进行权限验证。
 *
 * @author tycoding
 * @since 2024/4/15
 */
@Target(ElementType.METHOD) // 该注解只能应用于方法上
@Retention(RetentionPolicy.RUNTIME) // 该注解在运行时保留，以便在运行时进行反射处理
public @interface ClientPerm {

    /**
     * 定义权限值，用于指定访问该方法所需的具体权限。
     * 默认为空字符串，表示不指定特定权限。
     *
     * @return 权限值
     */
    String value() default "";
}
