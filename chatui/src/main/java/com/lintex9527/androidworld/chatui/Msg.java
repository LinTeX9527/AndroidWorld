package com.lintex9527.androidworld.chatui;

/**
 * Created by LinTeX9527 on 2018/9/8.
 *
 * 消息的实体类
 * 构建消息实体的时候，必须要传参数表明消息是发送出去的还是接收过来的。
 */
public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;

    private String content;
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
