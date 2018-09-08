package com.lintex9527.androidworld.chatui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个使用 RecyclerView 的简易聊天界面
 */
public class MainActivity extends AppCompatActivity {

    private List<Msg> mMsgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMsgs();
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(mMsgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    mMsgList.add(msg);
                    adapter.notifyItemInserted(mMsgList.size() - 1);
                    msgRecyclerView.scrollToPosition(mMsgList.size() - 1);
                    inputText.setText("");
                }
            }
        });
    }


    private void initMsgs() {
        Msg msg1 = new Msg("hello world", Msg.TYPE_RECEIVED);
        mMsgList.add(msg1);
        Msg msg2 = new Msg("Hello, Who is that?", Msg.TYPE_SEND);
        mMsgList.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice to meet you.", Msg.TYPE_RECEIVED);
        mMsgList.add(msg3);
        Msg msg4 = new Msg("上海天气怎样？", Msg.TYPE_RECEIVED);
        mMsgList.add(msg4);
    }
}
