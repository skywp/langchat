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

package cn.tycoding.langchat.common.ai.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 聊天相关的配置属性类，用于从配置文件中读取并存储聊天相关的配置信息。
 * 这些配置信息将在聊天功能的实现中使用，以定制聊天的行为和性能。
 * 
 * @author tycoding
 * @since 2024/8/21
 */
@Data
// 从配置文件中读取以 "langchat.chat" 为前缀的配置项，并将其映射到该类的属性上
@ConfigurationProperties("langchat.chat")
public class ChatProps {

    /**
     * 上下文的长度，即聊天过程中能够保留的最大消息数量。
     * 该属性用于限制聊天上下文的大小，避免内存占用过多。
     * 当聊天消息数量超过该值时，较早的消息将被移除。
     */
    private Integer memoryMaxMessage = 20;

    /**
     * 前端渲染的消息长度，即前端页面一次渲染的最大消息数量。
     * 过长的消息列表会导致页面渲染卡顿，因此需要限制渲染的消息数量。
     * 该属性用于优化前端页面的性能，提高用户体验。
     */
    private Integer previewMaxMessage = 100;
}
