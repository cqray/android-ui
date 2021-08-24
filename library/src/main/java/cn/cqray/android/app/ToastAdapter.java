package cn.cqray.android.app;

/**
 * Toast弹窗适配器
 * @author Cqray
 */
public interface ToastAdapter {

    /**
     * 错误消息
     * @param text 消息
     * @param duration 时长
     */
    void error(String text, int duration);

    /**
     * 正常消息
     * @param text 消息
     * @param duration 时长
     */
    void info(String text, int duration);

    /**
     * 成功消息
     * @param text 消息
     * @param duration 时长
     */
    void success(String text, int duration);

    /**
     * 警告消息
     * @param text 消息
     * @param duration 时长
     */
    void warning(String text, int duration);
}
