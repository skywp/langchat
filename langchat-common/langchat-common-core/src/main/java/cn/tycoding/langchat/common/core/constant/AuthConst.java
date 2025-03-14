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
 * 该接口定义了与认证相关的常量，用于统一管理认证过程中使用的默认值、标识和错误信息。
 *
 * @author tycoding
 * @since 2024/1/15
 */
public interface AuthConst {

    /**
     * 默认密码（重置密码）
     * 当用户需要重置密码时，将使用此默认密码。
     * 建议在实际应用中，重置密码后引导用户及时修改为自定义密码以增强安全性。
     */
    String DEFAULT_PASS = "123456";

    /**
     * 登录表单验证码Key标识
     * 在登录表单中，使用该 Key 来标识验证码字段。
     * 服务器端会根据该 Key 从表单数据中获取用户输入的验证码进行验证。
     */
    String CAPTCHA_FORM_KEY = "captcha";

    /**
     * 登录验证码Header Key标识
     * 在进行登录请求时，使用该 Key 作为请求头的名称来携带验证码相关信息。
     * 例如，客户端可以将验证码的唯一标识放在该请求头中，以便服务器端进行验证。
     */
    String CAPTCHA_HEADER_KEY = "Captcha-Key";

    /**
     * 验证码错误信息
     * 当用户输入的验证码不正确时，系统将返回此错误信息给用户。
     * 可以根据实际需求对该错误信息进行国际化处理。
     */
    String CAPTCHA_ERROR_INFO = "验证码不正确";
}
