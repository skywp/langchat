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

package cn.tycoding.langchat.common.auth.service;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Token 信息实体类，用于封装与 Token 相关的信息。
 * 该类使用了 Lombok 的 @Data 注解自动生成 getter、setter、toString、equals 和 hashCode 方法，
 * 同时使用 @Accessors(chain = true) 注解支持链式调用。
 *
 * @param <T> 用户信息的泛型类型，允许存储不同类型的用户信息
 * @author tycoding
 * @since 2024/1/5
 */
@Data
@Accessors(chain = true)
public class TokenInfo<T> {

    /**
     * Token 字符串，用于身份验证和授权。
     * 客户端在后续请求中携带此 Token 以证明其身份。
     */
    private String token;

    /**
     * Token 的过期时间，以毫秒为单位。
     * 表示该 Token 从生成时刻开始，到指定的毫秒数后将失效。
     */
    private Long expiration;

    /**
     * 用户信息，具体类型由泛型 T 决定。
     * 可以存储用户的基本信息、角色信息等。
     */
    private T user;

    // 以下是由 @Data 注解自动生成的方法的功能说明
    //
    // getToken(): 获取当前 Token 字符串。
    // setToken(String token): 设置当前 Token 字符串，支持链式调用。
    // getExpiration(): 获取 Token 的过期时间（毫秒）。
    // setExpiration(Long expiration): 设置 Token 的过期时间（毫秒），支持链式调用。
    // getUser(): 获取存储的用户信息。
    // setUser(T user): 设置存储的用户信息，支持链式调用。
    // toString(): 返回包含 Token、过期时间和用户信息的字符串表示形式。
    // equals(Object o): 比较当前对象与另一个对象是否相等。
    // hashCode(): 生成当前对象的哈希码。
}
