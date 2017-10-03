package com.example.readid2test;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.card.bean.ID2Data;
import com.sdses.readcardservice.IReadCardService;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView et_name, et_nation, et_bir, et_address, et_id2Num,
            et_issnue, et_time, et_sex;
    private ImageView mIVHead;

    private TextView mTVHint;

    private String TAG = "MainActivity";
    boolean bOpenReadCard = false;
    private CardInfoReciver mCardInfoReciver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        String pn = IReadCardService.class.getName();
        mTVHint.setText(pn);
        // 运行服务
        if (!checkServerRunning(this, "com.example.readid2test",
                "com.example.readid2test.ReadCardService")) {
            startService(new Intent(this, ReadCardService.class));
        }

        mCardInfoReciver = new CardInfoReciver();
        IntentFilter filterID2 = new IntentFilter();// 创建IntentFilter对象
        // 注册一个广播，用于接收Activity传送过来的命令，控制Service的行为，如：发送数据，停止服务等
        // filterID2.addAction(ClientVars.receivefromserver);
        filterID2.addAction("com.example.readid2test");
        registerReceiver(mCardInfoReciver, filterID2);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        unregisterReceiver(mCardInfoReciver);
        super.onDestroy();
    }

    /**
     * @throws
     * @Title: initWidgets
     * @author xc
     * @Description: TODO(初始化)
     * @param:
     * @return: void
     */
    private void initWidgets() {
        et_name = (TextView) findViewById(R.id.et_XM);
        et_sex = (TextView) findViewById(R.id.et_sex);
        et_nation = (TextView) findViewById(R.id.et_MZ);
        et_bir = (TextView) findViewById(R.id.et_CSRQ);
        et_address = (TextView) findViewById(R.id.et_HJDXZ);
        et_id2Num = (TextView) findViewById(R.id.et_GMSFHM);
        et_issnue = (TextView) findViewById(R.id.et_issnue);
        et_time = (TextView) findViewById(R.id.et_time);
        mIVHead = (ImageView) findViewById(R.id.iv_idCardFace);
        mTVHint = (TextView) findViewById(R.id.mTVHint);

    }

    private void clearInfo() {
        this.et_name.setText(""); // 姓名
        this.et_sex.setText(""); // 性别
        this.et_nation.setText(""); // 民族
        // this.mTVBirthDay.setText(""); // 出生日期
        this.et_bir.setText("");
        this.et_address.setText(""); // 住址
        this.et_id2Num.setText(""); // 公民身份号码
        this.et_issnue.setText(""); // 签发机关
        this.et_time.setText(""); // 有效期限
        this.mIVHead.setImageResource(R.drawable.idblank);
        this.mTVHint.setText("");
    }


    private void myToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    /**
     * @throws
     * @Title: clearID2Info
     * @author xc
     * @Description: TODO(清除二代证信息)
     * @param:
     * @return: void
     */

    private void showID2Info(String info) {
        try {
            JSONObject obj = new JSONObject(info);
            this.et_name.setText(obj.optString("xm")); // 姓名
            et_sex.setText(obj.optString("sex"));
            this.et_nation.setText(obj.optString("nation")); // 民族
            et_bir.setText(obj.optString("bir"));
            this.et_address.setText(obj.optString("address")); // 住址
            this.et_id2Num.setText(obj.optString("id2Num")); // 公民身份号码
            this.et_issnue.setText(obj.optString("issnue")); // 签发机关
            this.et_time.setText(obj.optString("time")); // 有效期限
            Bitmap bm = BitmapFactory.decodeByteArray(Base64.decode(obj.optString("pic"),
                    Base64.DEFAULT), 0, 38862);
            mIVHead.setImageBitmap(bm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkServerRunning(Context context, String packName,
                                             String serverName) {
        boolean isRunning = false;
        // 检测服务是否在运行
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = activityManager.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo ri : list) {
            if (TextUtils.equals(ri.service.getPackageName(), packName)
                    && TextUtils.equals(ri.service.getClassName(), serverName)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public class CardInfoReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (intent.getAction().equals("com.example.readid2test")) {
                String info = bundle.getString("card");
                showID2Info(info);
            }
        }
    }

}
