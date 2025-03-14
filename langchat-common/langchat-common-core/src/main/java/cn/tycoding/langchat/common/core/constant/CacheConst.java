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

/**
 * 该接口定义了系统中所有与 Redis 缓存相关的常量。
 * 这些常量用于构建 Redis 缓存的键名，方便在不同的业务场景中统一管理和使用缓存。
 *
 * @author tycoding
 * @since 2024/1/15
 */
public interface CacheConst {

    /**
     * 系统所有 Redis 缓存 Key 前缀 prefix。
     * 该前缀会添加到所有 Redis 缓存键的开头，用于区分不同系统或项目的缓存。
     */
    String REDIS_KEY_PREFIX = "langchat:";

    /**
     * Auth 缓存前缀。
     * 用于标识与认证相关的缓存，所有认证相关的缓存键都会以该前缀开头。
     */
    String AUTH_PREFIX = REDIS_KEY_PREFIX + "auth:";

    /**
     * Auth Session 缓存前缀。
     * 用于标识与认证会话相关的缓存，通常包含用户的会话信息。
     */
    String AUTH_SESSION_PREFIX = AUTH_PREFIX + "session:";

    /**
     * Auth Session 缓存变量前缀。
     * 用于标识认证会话缓存中的用户信息部分，通常与 AUTH_SESSION_PREFIX 结合使用。
     */
    String AUTH_USER_INFO_KEY = "USER_INFO";

    /**
     * Auth Token 缓存变量前缀。
     * 用于标识认证会话缓存中的令牌信息部分，通常与 AUTH_SESSION_PREFIX 结合使用。
     */
    String AUTH_TOKEN_INFO_KEY = "TOKEN_INFO";

    /**
     * 用户信息缓存。
     * 用于存储用户的详细信息，方便快速获取用户数据，减少数据库查询。
     */
    String USER_DETAIL_KEY = REDIS_KEY_PREFIX + "user_details";

    /**
     * 验证码缓存前缀。
     * 用于标识与验证码相关的缓存，通常用于存储用户的验证码信息，设置一定的过期时间。
     */
    String CAPTCHA_PREFIX = REDIS_KEY_PREFIX + "auth:captcha:";

}
