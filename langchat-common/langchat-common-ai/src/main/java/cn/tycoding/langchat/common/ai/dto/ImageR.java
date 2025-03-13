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

import dev.langchain4j.model.input.Prompt;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 该类用于封装图像生成相关的请求信息。
 * 
 * @author tycoding
 * @since 2024/1/6
 */
@Data
@Accessors(chain = true)
public class ImageR {

    /**
     * 模型的唯一标识符
     */
    private String modelId;

    /**
     * 模型的名称
     */
    private String modelName;

    /**
     * 模型的提供商
     */
    private String modelProvider;

    /**
     * 用于生成图像的提示信息
     */
    private Prompt prompt;

    /**
     * 图像生成的内容描述
     */
    private String message;

    /**
     * 生成图像的质量，例如 high、medium、low 等
     */
    private String quality;

    /**
     * 生成图像的尺寸，例如 256x256、512x512 等
     */
    private String size;

    /**
     * 生成图像的风格，例如 realistic、cartoon 等
     */
    private String style;
}
