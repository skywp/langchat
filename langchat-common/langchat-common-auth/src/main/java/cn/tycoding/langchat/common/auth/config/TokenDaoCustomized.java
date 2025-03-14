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

package cn.tycoding.langchat.common.auth.config;

import cn.dev33.satoken.session.SaSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Jackson 定制版 SaSession，忽略 timeout 等属性的序列化
 * 该类继承自 SaSession，主要用于在使用 Jackson 进行序列化时，
 * 忽略 SaSession 中的 timeout 属性，避免其被序列化到 JSON 数据中。
 *
 * @author click33
 * @since 1.34.0
 */
@JsonIgnoreProperties({"timeout"})
public class TokenDaoCustomized extends SaSession {
    // 序列化版本号，用于在反序列化时确保版本的兼容性
    private static final long serialVersionUID = -7600983549653130681L;

    /**
     * 默认构造函数
     * 调用父类的无参构造函数来初始化对象
     */
    public TokenDaoCustomized() {
        super();
    }

    /**
     * 带参数的构造函数
     * 用于构建一个带有指定 Session ID 的 Session 对象
     *
     * @param id Session 的唯一标识符
     */
    public TokenDaoCustomized(String id) {
        super(id);
    }
}
