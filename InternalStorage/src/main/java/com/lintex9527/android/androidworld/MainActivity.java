package com.lintex9527.android.androidworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;

/**
 * Android Internal Storage
 * https://www.journaldev.com/9383/android-internal-storage-example-tutorial
 *
 * 应用私有的存储空间。文件只针对本应用可见。而且应用删除后这些文件也被清空。
 *
 * 私有文件的存放路径：
 * /data/data/packagename/files/yourfilename
 *
 * openFileInput()    打开文件来读取内容
 * openFIleOutput()   创建和保存文件
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    // 本地文件的名字
    private static final String FILE_NAME = "internal.txt";
    // 一次读取文本的长度最长为100个字符
    private static final int READ_BLOCK_SIZE = 100;

    private EditText editText = null;
    private Button btnWrite = null;
    private Button btnRead = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        btnWrite = findViewById(R.id.btn_write);
        btnRead = findViewById(R.id.btn_read);

        btnWrite.setOnClickListener(this);
        btnRead.setOnClickListener(this);
    }


    /**
     * 按钮单击时的回调方法。
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_write:
                // 把EditText中的内容保存到internal storage中的一个文本文件中。
                try {
                    FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
                    String inputtext = editText.getText().toString();
                    writer.write(inputtext);
                    writer.flush();
                    writer.close();
                    showMessage("成功写入到本地文件，文本长度：" + inputtext.length());
                    // 写入之后清空编辑框
                    editText.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_read:
                // 从internal storage的一个文本文件读取文件内容，显示到EditText中。
                try {
                    FileInputStream fileInputStream = openFileInput(FILE_NAME);
                    InputStreamReader reader = new InputStreamReader(fileInputStream);
                    char[] inputBuffer = new char[READ_BLOCK_SIZE];
                    StringBuilder sb = new StringBuilder();
                    int charRead = -1; // 代表读取的字符个数
                    while ((charRead = reader.read(inputBuffer)) != -1) { // 文本内容读取到缓冲区 inputBuffer 中
                        Log.d(TAG, "读取的字符个数：" + charRead);
                        // 将char转换为string
                        String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                        sb.append(readstring);
                    }
                    editText.setText(sb.toString());
                    showMessage("读取文本的长度为：" + sb.length());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    /**
     * 弹出消息
     * @param msg 消息内容
     */
    private void showMessage(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
