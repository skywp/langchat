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

package cn.tycoding.langchat.common.ai.dto;

import cn.tycoding.langchat.common.ai.utils.StreamEmitter;
import dev.langchain4j.model.input.Prompt;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 聊天请求数据传输对象，用于封装聊天请求所需的各种信息。
 * 
 * @author tycoding
 * @since 2024/1/30
 */
@Data
@Accessors(chain = true)
public class ChatReq {

    /**
     * 应用ID，用于标识请求所属的应用。
     */
    private String appId;

    /**
     * 模型ID，用于指定使用的AI模型。
     */
    private String modelId;

    /**
     * 模型名称，即使用的AI模型的具体名称。
     */
    private String modelName;

    /**
     * 模型提供者，表明该AI模型是由哪个供应商提供的。
     */
    private String modelProvider;

    /**
     * 用户发送的聊天消息内容。
     */
    private String message;

    /**
     * 对话ID，用于标识当前的对话上下文。
     */
    private String conversationId;

    /**
     * 用户ID，用于唯一标识发起聊天请求的用户。
     */
    private String userId;

    /**
     * 用户名，即发起聊天请求的用户的名称。
     */
    private String username;

    /**
     * 聊天ID，用于标识当前的聊天会话。
     */
    private String chatId;

    /**
     * 提示文本，可为AI模型提供额外的引导信息。
     */
    private String promptText;

    /**
     * 文档名称，与聊天相关的文档的名称。
     */
    private String docsName;

    /**
     * 知识ID，单个知识的标识。
     */
    private String knowledgeId;

    /**
     * 知识ID列表，包含多个知识的标识。
     */
    private List<String> knowledgeIds = new ArrayList<>();

    /**
     * 文档ID，用于标识相关的文档。
     */
    private String docsId;

    /**
     * 相关的URL链接。
     */
    private String url;

    /**
     * 用户的角色，表明用户在对话中的角色。
     */
    private String role;

    /**
     * 提示对象，包含更复杂的提示信息。
     */
    private Prompt prompt;

    /**
     * 流发射器，用于处理流式响应。
     */
    private StreamEmitter emitter;

    /**
     * 执行器，用于执行异步任务。
     */
    private Executor executor;
}
