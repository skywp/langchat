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

package cn.tycoding.langchat.common.auth.interceptor;

import cn.tycoding.langchat.common.core.constant.AuthConst;
import cn.tycoding.langchat.common.core.constant.CacheConst;
import cn.tycoding.langchat.common.core.utils.R;
import cn.tycoding.langchat.common.core.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 验证码拦截器，用于在请求处理前对验证码进行校验
 * 
 * @author tycoding
 * @since 2024/1/5
 */
@Slf4j
@AllArgsConstructor
public class CaptchaInterceptor implements HandlerInterceptor {

    /**
     * 用于操作 Redis 的模板类，用于从 Redis 中获取验证码
     */
    private final StringRedisTemplate redisTemplate;

    /**
     * 在请求处理之前进行验证码校验
     *
     * @param request  当前的 HTTP 请求
     * @param response 当前的 HTTP 响应
     * @param handler  执行链中的下一个处理器
     * @return 如果验证码校验通过返回 true，否则返回 false
     * @throws Exception 如果在处理过程中发生异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查请求的 URI 是否为 "/oauth/token"
        if ("/oauth/token".equals(request.getRequestURI())) {
            // 从请求头中获取验证码的 key
            String headerKey = request.getHeader(AuthConst.CAPTCHA_HEADER_KEY);
            // 如果请求头中没有携带验证码的 key
            if (headerKey == null) {
                // 记录日志，说明正在进行请求授权，未携带 Captcha-Key 请求头，不进行验证码校验
                log.info("正在进行请求授权，未携带Captcha-Key请求头，不进行验证码校验");
                // 直接放行请求
                return true;
            }

            // 从请求参数中获取用户输入的验证码
            String code = ServletRequestUtils.getStringParameter(request, AuthConst.CAPTCHA_FORM_KEY);
            // 从 Redis 中获取对应的验证码
            String redisCode = (String) redisTemplate.opsForValue().get(CacheConst.CAPTCHA_PREFIX + headerKey);
            // 如果用户输入的验证码为空或者与 Redis 中存储的验证码不匹配（忽略大小写）
            if (code == null || !code.toLowerCase().equals(redisCode)) {
                // 向客户端返回错误信息
                ServletUtil.write(response, new R<>(HttpStatus.BAD_REQUEST.value(), AuthConst.CAPTCHA_ERROR_INFO));
                // 拦截请求
                return false;
            }
        }
        // 非 "/oauth/token" 请求或者验证码校验通过，放行请求
        return true;
    }
}
