package cn.yifan.geekgen.exception;

/**
 * @FileName ApiError
 * @Description
 * @Author yifan
 * @date 2025-01-28 17:48
 **/
public enum ApiError {

    // article错误
    ARTICLE_NOT_EXIST(404, "文章不存在"),
    ARTICLE_NOT_COLLECT(404, "文章未被收藏"),
    ARTICLE_HAD_COLLECTED(409, "文章已被收藏"),

    // word错误
    WORD_NOT_EXIST(404, "单词不存在"),
    // word没有被收藏
    WORD_NOT_COLLECT(404, "单词未被收藏"),
    WORD_HAD_COLLECTED(409, "单词已被收藏"),

    // class错误
    CLASS_HAD_EXISTED(409, "班级名称已存在"),
    CLASS_NOT_EXIST(404, "班级不存在"),
    CLASS_TEACHER_ERROR(400, "班级老师错误"),
    CLASS_TASK_NOT_EXIST(404, "班级任务不存在"),

    // chat错误
    CHAT_TASK_IS_WAITING(409, "waiting"),
    CHAT_TASK_NOT_EXIST(404, "not_exist"),
    CHAT_TASK_IS_FINISHED(409, "finished"),

    // completion错误
    COMPLETION_ERROR(500, "completion错误"),

    // agent不存在
    AGENT_NOT_EXIST(404, "agent不存在"),

    // 会话不存在
    CONVERSATION_NOT_EXIST(404, "会话不存在"),

    // 参数
    PARAM_ERROR(400, "参数错误"),

    // 系统
    SYSTEM_ERROR(500, "服务器内部错误"),

    // 权限
    NOT_LOGIN(401, "未登录"),
    COMPLETION_NOT_LOGIN(401, "SSE接口未登录"),

    // 登录
    USER_NOT_EXIST(404, "用户不存在"),
    PASSWORD_ERROR(401, "密码错误"),

    // 注册
    USER_HAD_EXISTED(409, "Internal server error");

    // 枚举项的参数
    private final Integer code;
    private final String message;

    // 构造函数
    ApiError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    // 获取枚举项的参数
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
