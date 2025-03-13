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

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 聊天响应数据传输对象
 * 该类用于封装聊天交互过程中的响应信息，包括是否完成标识、消息内容、使用的令牌数量和响应耗时
 * 
 * @author tycoding
 * @since 2024/1/29
 */
@Data
@Accessors(chain = true)
public class ChatRes {

    /**
     * 标识聊天是否完成
     * 默认值为 false，表示聊天未完成
     */
    private boolean isDone = false;

    /**
     * 聊天响应的消息内容
     */
    private String message;

    /**
     * 本次聊天使用的令牌数量
     */
    private Integer usedToken;

    /**
     * 本次聊天的响应耗时（毫秒）
     */
    private long time;

    /**
     * 构造函数，用于创建包含消息内容的聊天响应对象
     * 
     * @param message 聊天响应的消息内容
     */
    public ChatRes(String message) {
        this.message = message;
    }

    /**
     * 构造函数，用于创建表示聊天完成的响应对象
     * 
     * @param usedToken 本次聊天使用的令牌数量
     * @param startTime 聊天开始的时间戳（毫秒）
     */
    public ChatRes(Integer usedToken, long startTime) {
        // 标记聊天已完成
        this.isDone = true;
        this.usedToken = usedToken;
        // 计算响应耗时
        this.time = System.currentTimeMillis() - startTime;
    }
}
