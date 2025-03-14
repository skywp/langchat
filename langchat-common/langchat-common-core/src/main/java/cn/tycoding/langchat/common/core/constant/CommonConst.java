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
 * 通用常量接口，包含了项目中常用的一些常量定义。
 * 这些常量可在项目的各个模块中使用，以提高代码的可维护性和可读性。
 *
 * @author tycoding
 * @since 2024/1/15
 */
public interface CommonConst {

    /**
     * UTF-8 编码，用于指定字符编码格式，在处理文本数据时经常使用。
     * 例如在文件读写、网络传输等场景中，确保数据以 UTF-8 编码进行处理。
     */
    String UTF_8 = "utf-8";

    /**
     * 菜单类型：menu，表示该菜单为普通菜单类型。
     * 在菜单管理系统中，用于区分不同类型的菜单，如普通菜单和按钮菜单。
     */
    String MENU_TYPE_MENU = "menu";

    /**
     * 菜单类型：button，表示该菜单为按钮类型。
     * 在菜单管理系统中，用于区分不同类型的菜单，可用于特定的操作按钮。
     */
    String MENU_TYPE_BUTTON = "button";

    /**
     * 菜单：默认Icon图标，当菜单未指定具体图标时，使用该默认图标。
     * 在菜单展示界面中，可统一未指定图标的菜单的显示样式。
     */
    String MENU_ICON = "alert";

    /**
     * 布局相关的常量，可能用于指定页面布局的类型或名称。
     * 在前端页面布局设计中，可根据该常量进行布局的配置。
     */
    String LAYOUT = "Layout";
}
