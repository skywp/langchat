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
 * 自定义日志记录切面注解
 * 此注解用于标记需要进行日志记录的方法，可在方法执行前后进行日志记录操作，
 * 例如记录方法的调用信息、参数、执行时间等，方便后续的系统审计和问题排查。
 *
 * @author tycoding
 * @since 2024/4/15
 */
// 该注解只能应用于方法上
@Target(ElementType.METHOD)
// 该注解在运行时可见，以便在运行时通过反射机制获取注解信息
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLog {

    /**
     * 日志描述信息，可用于说明该方法的操作内容
     * 默认为空字符串
     *
     * @return 日志描述信息
     */
    String value() default "";
}
