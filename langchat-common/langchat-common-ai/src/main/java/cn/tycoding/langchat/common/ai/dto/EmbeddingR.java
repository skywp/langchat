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
 * 该类用于封装 Embedding 操作的返回结果。
 * Embedding 是将文本数据转换为向量表示的过程，此对象存储了相关的关键信息。
 *
 * @author tycoding
 * @since 2024/4/26
 */
@Data
@Accessors(chain = true)
public class EmbeddingR {

    /**
     * 写入到vector store的ID
     */
    private String vectorId;

    /**
     * 文档的唯一标识符。
     * 表明该 Embedding 结果对应的原始文档。
     */
    private String docsId;

    /**
     * 知识库的唯一标识符。
     * 指示该 Embedding 结果所属的知识库。
     */
    private String knowledgeId;

    /**
     * Embedding 操作后切片的文本内容。
     * 即原始文本经过处理和 Embedding 操作后，得到的文本片段。
     */
    private String text;
}
