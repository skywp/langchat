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

package cn.tycoding.langchat.common.ai.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.tycoding.langchat.common.ai.dto.PromptConst;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.util.Map;

/**
 * 该工具类用于构建不同类型的 Prompt 对象。
 * Prompt 对象通常用于向语言模型提供输入信息。
 *
 * @author tycoding
 * @since 2024/3/1
 */
public class PromptUtil {

    /**
     * 根据给定的消息构建一个简单的 Prompt 对象。
     *
     * @param message 要构建 Prompt 的消息内容
     * @return 包含该消息的 Prompt 对象
     */
    public static Prompt build(String message) {
        return new Prompt(message);
    }

    /**
     * 根据给定的消息和提示文本构建一个 Prompt 对象。
     * 会将提示文本和常量 PromptConst.EMPTY 拼接后，使用消息替换其中的占位符。
     *
     * @param message    要构建 Prompt 的消息内容
     * @param promptText 提示文本
     * @return 替换占位符后的 Prompt 对象
     */
    public static Prompt build(String message, String promptText) {
        return new PromptTemplate(promptText + PromptConst.EMPTY).apply(Map.of(PromptConst.QUESTION, message));
    }

    /**
     * 根据给定的消息、提示文本和参数对象构建一个 Prompt 对象。
     * 会将参数对象转换为 Map，将消息添加到 Map 中，然后使用该 Map 替换提示文本中的占位符。
     *
     * @param message    要构建 Prompt 的消息内容
     * @param promptText 提示文本
     * @param param      包含额外参数的对象
     * @return 替换占位符后的 Prompt 对象
     */
    public static Prompt build(String message, String promptText, Object param) {
        // 将参数对象转换为 Map
        Map<String, Object> params = BeanUtil.beanToMap(param, false, true);
        // 将消息添加到参数 Map 中
        params.put(PromptConst.QUESTION, message);
        // 使用参数 Map 替换提示文本中的占位符
        return new PromptTemplate(promptText).apply(params);
    }

    /**
     * 根据给定的消息和文档提示常量构建一个 Prompt 对象。
     *
     * @param message 要构建 Prompt 的消息内容
     * @return 使用消息替换文档提示常量中占位符后的 Prompt 对象
     */
    public static Prompt buildDocs(String message) {
        return new PromptTemplate(PromptConst.DOCUMENT).apply(Map.of(PromptConst.QUESTION, message));
    }
}
