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

package cn.tycoding.langchat.common.core.constant;

import lombok.Getter;

/**
 * 该枚举类定义了在对话交互场景中可能出现的角色类型。
 * 每个角色都有一个对应的名称，用于明确在对话中的身份和职责。
 *
 * @author tycoding
 * @since 2024/2/20
 */
@Getter
public enum RoleEnum {
    /**
     * 用户角色，表示与系统进行交互的人类用户。
     */
    USER("user"),
    /**
     * 助手角色，表示系统针对用户的输入提供响应的部分。
     */
    ASSISTANT("assistant"),
    /**
     * 系统角色，通常用于设置对话的初始上下文或提供全局指令。
     */
    SYSTEM("system");

    /**
     * 角色的名称，用于在代码中标识和使用。
     */
    private final String name;

    /**
     * 枚举类的构造函数，用于初始化角色的名称。
     *
     * @param name 角色的名称
     */
    RoleEnum(String name) {
        this.name = name;
    }
}
