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

package cn.tycoding.langchat.common.auth.event;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义 Log 事件类，继承自 Spring 的 ApplicationEvent 类。
 * 该类用于在应用程序中发布与日志相关的事件，以便其他组件可以监听并做出相应的处理。
 *
 * @author tycoding
 * @since 2024/1/5
 */
public class LogEvent extends ApplicationEvent {

    /**
     * 构造函数，用于创建 LogEvent 实例。
     *
     * @param source 事件源对象，通常是触发该事件的对象。
     *               在 Spring 事件机制中，source 是事件发生的源头，可通过该对象获取事件相关的上下文信息。
     */
    public LogEvent(Object source) {
        // 调用父类 ApplicationEvent 的构造函数，将事件源对象传递给父类进行初始化
        super(source);
    }
}
