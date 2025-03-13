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

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * 用于处理服务器发送事件（SSE）的工具类
 * 该类封装了 SseEmitter 的操作，提供了流式传输、发送数据、完成传输和处理错误等功能
 * 
 * @author tycoding
 * @since 2024/1/30
 */
public class StreamEmitter {

    /**
     * 用于服务器发送事件的 SseEmitter 实例
     */
    private final SseEmitter emitter;

    /**
     * 构造函数，初始化 SseEmitter 实例
     * 设置 SseEmitter 的超时时间为 5 分钟
     */
    public StreamEmitter() {
        // 初始化 SseEmitter，设置超时时间为 5 分钟
        emitter = new SseEmitter(5 * 60 * 1000L);
    }

    /**
     * 获取 SseEmitter 实例
     * 
     * @return SseEmitter 实例
     */
    public SseEmitter get() {
        return emitter;
    }

    /**
     * 开始流式传输数据
     * 该方法会在后台线程中执行给定的任务，并处理 SseEmitter 的完成、错误和超时事件
     * 
     * @param executor 用于执行任务的线程池
     * @param func 要执行的任务
     * @return SseEmitter 实例
     */
    public SseEmitter streaming(final ExecutorService executor, Runnable func) {
        // 设置 SseEmitter 完成时的回调，关闭线程池
        emitter.onCompletion(() -> {
            System.out.println("SseEmitter 完成");
            executor.shutdownNow();
        });

        // 设置 SseEmitter 出错时的回调，关闭线程池
        emitter.onError((e) -> {
            System.out.println("SseEmitter 出现错误: " + e.getMessage());
            executor.shutdownNow();
        });

        // 设置 SseEmitter 超时时的回调，完成传输并关闭线程池
        emitter.onTimeout(() -> {
            System.out.println("SseEmitter 超时");
            emitter.complete();
            executor.shutdownNow();
        });

        // 在线程池中执行任务
        executor.execute(() -> {
            try {
                // 执行任务
                func.run();
            } catch (Exception e) {
                // 捕获异常，完成传输并抛出错误
                System.out.println("捕获到异常: " + e.getMessage());
                emitter.completeWithError(e);
                Thread.currentThread().interrupt();
            } finally {
                // 确保线程池被关闭
                if (!executor.isShutdown()) {
                    executor.shutdownNow();
                }
            }
        });

        return emitter;
    }

    /**
     * 发送数据到客户端
     * 
     * @param obj 要发送的数据对象
     */
    public void send(Object obj) {
        try {
            // 发送数据
            emitter.send(obj);
        } catch (IOException e) {
            // 处理发送异常
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 完成流式传输
     */
    public void complete() {
        // 完成传输
        emitter.complete();
    }

    /**
     * 发送错误信息并完成传输
     * 
     * @param message 错误信息
     */
    public void error(String message) {
        try {
            // 发送错误信息
            emitter.send("Error: " + message);
            // 完成传输
            emitter.complete();
        } catch (IOException e) {
            // 处理发送异常
            throw new RuntimeException(e.getMessage());
        }
    }
}
