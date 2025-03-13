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

package cn.tycoding.langchat.common.ai.dto;

/**
 * 该接口定义了一系列与提示相关的常量，用于向语言模型提供不同类型的指令或模板。
 * 这些常量可以帮助构建适用于不同场景的提示信息。
 *
 * @author tycoding
 * @since 2024/3/1
 */
public interface PromptConst {

    /**
     * 表示问题的键名，用于在模板中替换具体的问题内容。
     */
    String QUESTION = "question";

    /**
     * 空提示模板，包含一个占位符 {{question}}，用于插入具体的问题。
     * 前后的分隔线有助于清晰区分问题。
     */
    String EMPTY = """
            
            ------
            {{question}}
            ------
            """;

    /**
     * 文档分析提示，告知语言模型擅长分析文档，并要求根据给定的文档分析问题。
     * 其中 [{{question}}] 是问题的占位符，[docs] 表示文档部分，需要在使用时替换为具体内容。
     */
    String DOCUMENT = "You are good at analyzing documents. Please analyze my questions according to the following documents, question: [{{question}}], [docs]";

    /**
     * 思维导图生成提示，定义了语言模型作为 Markdown 大纲格式工程师的角色、技能和限制。
     * 角色：专注于回答用户问题，将问题转换为精炼的 Markdown 大纲标题并细化具体细节。
     * 技能：识别用户问题意图、转换为 Markdown 大纲、返回优化后的大纲。
     * 限制：只返回组织好的 Markdown 格式内容，用用户使用的语言回答，保持主标题简洁并细化副标题的步骤信息。
     */
    String MINDMAP = """
            # Role
            You are a Markdown outline format engineer who focuses on answering user questions. You can quickly and accurately convert user questions into refined Markdown outline titles, and refine the specific details of each title.
            
            ## Skills
            ### Skill 1: Identify user question intent
            - Accurately understand the specific content and needs of user questions.
            ### Skill 2: Convert to Markdown outline
            - Simplify user questions into Markdown outline-style titles.
            ### Skill 3: Return to user
            - Return the optimized outline to the user.
            
            ## Constraints
            - Only return the organized Markdown format content, without other explanation information
            - Answer the question in the language used by the user.
            - Return the answer in Markdown style, keep the main title as concise as possible; and refine the specific step information of each main title in the subtitle.
            """;

    /**
     * 文案撰写提示，定义了语言模型作为专业文案撰写师的角色、技能和限制。
     * 角色：擅长运用行业领域知识，以专业视角为用户生成 Markdown 文档。
     * 技能：提取用户输入的主题和关键信息、应用专业知识、遵循 Markdown 格式。
     * 限制：只讨论写作相关话题，以用户输入信息为主题撰写内容。
     */
    String WRITE = """
            # 角色
            你是一名专业文案撰写师。你擅长运用行业领域相关知识，以专业的视角为用户生成Markdown文档。
            
            ## 技能
            ### 技能 1: 写作
            - 提取用户输入的主题和关键信息。
            
            ### 技能 2: 专业知识应用
            - 了解相关行业的相关知识。
            - 在撰写内容时，运用专业的语言和视角。
            
             ### 技能 3: 遵循Markdown格式
            - 拆分文档内容，以Markdown大纲格式分段内容，更易于用户阅读
            
            ## 限制
            - 只讨论写作相关的话题，不要返回其他任何内容和解释。
            - 始终以用户输入的信息为主题，撰写内容。
            """;

    /**
     * 图像生成提示，要求根据给定的要求生成相应的图片。
     */
    String IMAGE = """
            Please generate the corresponding pictures according to the following requirements.
            """;
}
