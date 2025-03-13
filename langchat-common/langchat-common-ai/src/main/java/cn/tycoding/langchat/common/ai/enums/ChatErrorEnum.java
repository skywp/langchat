package cn.tycoding.langchat.common.ai.enums;

import lombok.AllArgsConstructor;

/**
 * 聊天错误枚举类，定义了在使用聊天模型时可能出现的错误情况。
 * 每个枚举值包含一个错误码和对应的错误描述，用于在出现问题时向用户提供明确的反馈。
 *
 * @author GB
 * @desc 定义聊天模型相关的错误信息
 * @since 2024-08-21
 */
@AllArgsConstructor
public enum ChatErrorEnum {
    /**
     * 当模型的 API Key 为空时的错误情况。
     * 错误码为 1000，错误描述提示用户检查模型的 API Key 配置。
     */
    API_KEY_IS_NULL(1000, "模型 %s %s api key 为空，请检查配置"),
    /**
     * 当模型的 Base URL 为空时的错误情况。
     * 错误码为 1003，错误描述提示用户检查模型的 Base URL 配置。
     */
    BASE_URL_IS_NULL(1003, "模型 %s %s base url 为空，请检查配置"),
    /**
     * 当模型的 Secret Key 为空时的错误情况。
     * 错误码为 1005，错误描述提示用户检查模型的 Secret Key 配置。
     */
    SECRET_KEY_IS_NULL(1005, "模型 %s %s base secret Key 为空，请检查配置"),
    ;

    /**
     * 错误码，用于唯一标识不同的错误情况。
     */
    private int errorCode;
    /**
     * 错误描述，用于向用户展示具体的错误信息，包含占位符 %s 用于填充模型名称和类型。
     */
    private String errorDesc;

    /**
     * 获取错误码。
     *
     * @return 错误码
     */
    public int getErrorCode() {
        return this.errorCode;
    }

    /**
     * 根据模型名称和类型获取格式化后的错误描述。
     *
     * @param modelName 模型名称
     * @param type 模型类型
     * @return 格式化后的错误描述
     */
    public String getErrorDesc(String modelName, String type) {
        return this.errorDesc.formatted(modelName, type);
    }

}
