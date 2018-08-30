package com.lintex9527.android.externalstorage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lintex9527@yeah.net
 * @date   2018/08/24 10:25
 * 在External Storage 上读写文件操作
 *
 * 其实External Storage上可以保存应用私有和全局的文件，但是这里的测试重点在于分享全局的文件。
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext = null;
    private EditText editInput = null;
    private Button btnWriteExternalPublic = null;
    private Button btnReadExternalPublic = null;

    // 保存到external storage 的公共文档的路径和文件名称
    private static final String PUBLIC_FILENAME = "userinput_public.txt";

    // 动态申请权限，注意这里的权限必须要先在AndroidManifest.xml文件中静态申请一遍。
    private static final String[] REQUIRED_PERMISSION_LIST = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private static final int REQUEST_PERMISSION_CODE = 0x12345;

    private List<String> missingPermissions = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 立刻申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();
        }


        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        editInput = findViewById(R.id.edit_userinput);
        btnWriteExternalPublic = findViewById(R.id.btn_write_public);
        btnReadExternalPublic = findViewById(R.id.btn_read_public);

        btnWriteExternalPublic.setOnClickListener(this);
        btnReadExternalPublic.setOnClickListener(this);
        findViewById(R.id.btn_copy_photo).setOnClickListener(this);
        findViewById(R.id.btn_delete_photo).setOnClickListener(this);
        findViewById(R.id.btn_write_private).setOnClickListener(this);
        findViewById(R.id.btn_read_private).setOnClickListener(this);
    }


    /**
     * 按钮单击监听器
     * @param v
     */
    @Override
    public void onClick(View v) {
        String userinput = editInput.getText().toString();
        switch (v.getId()) {
            case R.id.btn_write_public:
                // 保存到external storage 的公共文档目录
                if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) { // external storage 存在且可用
                    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                    File file = new File(dir, PUBLIC_FILENAME);
                    Log.d(TAG, "操作的文件的绝对路径是：" + file.getAbsolutePath());
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
                        writer.write(userinput);
                        writer.flush();
                        writer.close();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showMessage("外部存储不可用，写入到文件失败！！！");
                }
                editInput.setText("");
                break;
            case R.id.btn_read_public:
                // 从 external storage的公共文档目录读文件
                if (isExternalStorageAvailable()) {
                    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                    File file = new File(dir, PUBLIC_FILENAME);
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] buf = new byte[fileInputStream.available()];
                        int readbytes = fileInputStream.read(buf);
                        Log.d(TAG, "读取的字节个数是：" + readbytes);
                        String text = new String(buf);
                        editInput.setText(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.btn_copy_photo:
                // 复制程序包中的图片到 DIRECTORY_PICTURES 目录
                if (isExternalStorageAvailable()) {
                    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    File file = new File(dir, "copy-cat.jpg");
                    try {
                        InputStream inputStream = getResources().openRawResource(R.raw.cat);
                        OutputStream outputStream = new FileOutputStream(file);
                        byte[] data = new byte[inputStream.available()];
                        inputStream.read(data);
                        outputStream.write(data);
                        inputStream.close();
                        outputStream.close();
                        showMessage("复制图片成功");
                        // 让扫描器扫描文件变更
                        MediaScannerConnection.scanFile(mContext, new String[]{file.getPath()}, null,
                                new MediaScannerConnection.OnScanCompletedListener() {
                                    @Override
                                    public void onScanCompleted(String path, Uri uri) {
                                        Log.d(TAG, "成功扫描到文件：" + path);
                                        Log.d(TAG, "对应的Uri: " + uri);
                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                        showMessage("复制图片失败");
                    }
                }
                break;
            case R.id.btn_delete_photo:
                // 删除图片
                if (isExternalStorageAvailable()) {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "copy-cat.jpg");
                    file.delete();
                }
                break;
            case R.id.btn_write_private:
                // 写入到外部私有空间的文件
                if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
                    File path = getExternalFilesDir("private-data");
                    File file = new File(path, "userinput_private.txt");
                    Log.d(TAG, "此文件的路径是：" + file.getAbsolutePath());
                    try {
                        OutputStream outputStream = new FileOutputStream(file);
                        outputStream.write(userinput.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        showMessage("写入到外部私有空间成功");
                    } catch (IOException e) {
                        e.printStackTrace();
                        showMessage("写入到外部私有空间失败");
                    }
                }
                editInput.setText("");
                break;
            case R.id.btn_read_private:
                // 读取外部私有空间的文件
                if (isExternalStorageAvailable()) {
                    File file = new File(getExternalFilesDir("private-data"), "userinput_private.txt");
                    Log.d(TAG, "此文件的路径是：" + file.getAbsolutePath());
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] data = new byte[fileInputStream.available()];
                        fileInputStream.read(data);
                        String text = new String(data);
                        editInput.setText(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }


    /**
     * 检查External Storage的状态，是否可用
     * @return 返回true表示挂载了external storage
     */
    public static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    /**
     * 检查External Storage的状态是否只读
     * @return 返回true表示只读
     */
    public static boolean isExternalStorageReadOnly() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    private void showMessage(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    private void checkAndRequestPermissions() {
        for (String eachPermission : REQUIRED_PERMISSION_LIST) {
            if (checkSelfPermission(eachPermission) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "此权限 " + eachPermission + "已满足");
            } else {
                Log.d(TAG, "此权限 " + eachPermission + "未满足！！！");
                missingPermissions.add(eachPermission);
            }
        }

        if (!missingPermissions.isEmpty()) {
            requestPermissions(missingPermissions.toArray(new String[missingPermissions.size()]), REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = grantResults.length - 1; i >= 0 ; i--) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    missingPermissions.remove(i);
                }
            }

            if (missingPermissions.isEmpty()) {
                Log.d(TAG, "所有的权限都满足了");
            }
        }
    }
}
