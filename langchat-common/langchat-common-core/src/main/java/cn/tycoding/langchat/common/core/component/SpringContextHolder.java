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

package cn.tycoding.langchat.common.core.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

/**
 * Spring 上下文持有者类，用于在非 Spring 管理的类中获取 Spring 上下文，
 * 并提供发布事件、获取 Bean、注册和注销 Bean 等功能。
 *
 * @author tycoding
 * @since 2024/1/19
 */
@Service
public class SpringContextHolder implements ApplicationContextAware {

    /**
     * 静态的 Spring 应用上下文对象，用于存储 Spring 上下文
     */
    private static ApplicationContext applicationContext = null;

    /**
     * 发布 Spring 应用事件
     *
     * @param event 要发布的应用事件
     */
    public static void publishEvent(ApplicationEvent event) {
        // 如果应用上下文为空，则不发布事件，直接返回
        if (applicationContext == null) {
            return;
        }
        // 使用应用上下文发布事件
        applicationContext.publishEvent(event);
    }

    /**
     * 根据类型获取 Spring 容器中的 Bean
     *
     * @param <T>          Bean 的类型
     * @param requiredType Bean 的类型 Class 对象
     * @return 符合类型的 Bean 实例
     */
    public <T> T getBean(Class<T> requiredType) {
        // 从应用上下文中获取指定类型的 Bean
        return applicationContext.getBean(requiredType);
    }

    /**
     * 实现 ApplicationContextAware 接口的方法，用于设置 Spring 应用上下文
     *
     * @param applicationContext Spring 应用上下文对象
     * @throws BeansException 如果设置上下文时出现 Bean 相关异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 将传入的应用上下文赋值给静态的 applicationContext 变量
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 动态注册一个 Bean 到 Spring 容器中
     *
     * @param beanName     要注册的 Bean 的名称
     * @param beanInstance 要注册的 Bean 实例
     */
    public void registerBean(String beanName, Object beanInstance) {
        // 从应用上下文的自动装配 Bean 工厂中获取 Bean 定义注册器
        BeanDefinitionRegistry beanDefinitionRegistry =
                (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();

        // 使用 BeanDefinitionBuilder 构建 Bean 定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .genericBeanDefinition((Class<Object>) beanInstance.getClass(), () -> beanInstance);

        // 获取构建好的原始 Bean 定义
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();

        // 向 Bean 定义注册器中注册 Bean 定义
        beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
    }

    /**
     * 从 Spring 容器中注销一个 Bean
     *
     * @param beanName 要注销的 Bean 的名称
     */
    public void unregisterBean(String beanName) {
        // 从应用上下文的自动装配 Bean 工厂中获取 Bean 定义注册器
        BeanDefinitionRegistry beanDefinitionRegistry =
                (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();

        // 检查 Bean 定义注册器中是否包含指定名称的 Bean 定义
        if (beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            // 如果包含，则从注册器中移除该 Bean 定义
            beanDefinitionRegistry.removeBeanDefinition(beanName);
        }
    }
}
