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

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import org.springframework.stereotype.Component;

/**
 * 认证监听器类，实现了 SaTokenListener 接口，用于监听 Sa-Token 框架的各种认证事件。
 * 当发生如登录、注销、被踢下线等事件时，会触发相应的方法。
 * 目前这些方法为空，可根据实际需求添加具体的业务逻辑。
 *
 * @author tycoding
 * @since 2024/1/5
 */
@Component
public class AuthListener implements SaTokenListener {

    /**
     * 每次登录时触发此方法。
     *
     * @param loginType 登录类型，用于区分不同的登录场景
     * @param loginId 登录的用户 ID
     * @param tokenValue 生成的 token 值
     * @param loginModel 登录的相关配置信息
     */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        // 可在此添加登录成功后的业务逻辑，如记录登录日志等
    }

    /**
     * 每次注销时触发此方法。
     *
     * @param loginType 登录类型
     * @param loginId 登录的用户 ID
     * @param tokenValue 注销的 token 值
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        // 可在此添加注销后的业务逻辑，如清理缓存等
    }

    /**
     * 每次被踢下线时触发此方法。
     *
     * @param loginType 登录类型
     * @param loginId 登录的用户 ID
     * @param tokenValue 被踢下线的 token 值
     */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        // 可在此添加被踢下线后的业务逻辑，如通知用户等
    }

    /**
     * 每次被顶下线时触发此方法。
     *
     * @param loginType 登录类型
     * @param loginId 登录的用户 ID
     * @param tokenValue 被顶下线的 token 值
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        // 可在此添加被顶下线后的业务逻辑，如提醒用户等
    }

    /**
     * 每次被封禁时触发此方法。
     *
     * @param loginType 登录类型
     * @param loginId 登录的用户 ID
     * @param service 服务标识
     * @param level 封禁级别
     * @param disableTime 封禁时长
     */
    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {
        // 可在此添加被封禁后的业务逻辑，如记录封禁信息等
    }

    /**
     * 每次被解封时触发此方法。
     *
     * @param loginType 登录类型
     * @param loginId 登录的用户 ID
     * @param service 服务标识
     */
    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {
        // 可在此添加被解封后的业务逻辑，如通知用户等
    }

    /**
     * 每次二级认证时触发此方法。
     *
     * @param loginType 登录类型
     * @param tokenValue 认证的 token 值
     * @param service 服务标识
     * @param safeTime 二级认证的有效时长
     */
    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {
        // 可在此添加二级认证成功后的业务逻辑，如记录认证信息等
    }

    /**
     * 每次退出二级认证时触发此方法。
     *
     * @param loginType 登录类型
     * @param tokenValue 退出认证的 token 值
     * @param service 服务标识
     */
    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {
        // 可在此添加退出二级认证后的业务逻辑，如清理认证相关缓存等
    }

    /**
     * 每次创建 Session 时触发此方法。
     *
     * @param id Session 的 ID
     */
    @Override
    public void doCreateSession(String id) {
        // 可在此添加创建 Session 后的业务逻辑，如记录 Session 创建信息等
    }

    /**
     * 每次注销 Session 时触发此方法。
     *
     * @param id 注销的 Session 的 ID
     */
    @Override
    public void doLogoutSession(String id) {
        // 可在此添加注销 Session 后的业务逻辑，如清理 Session 相关数据等
    }

    /**
     * 每次 Token 续期时触发此方法。
     *
     * @param tokenValue 续期的 token 值
     * @param loginId 登录的用户 ID
     * @param timeout 续期后的过期时长
     */
    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {
        // 可在此添加 Token 续期后的业务逻辑，如记录续期信息等
    }
}
