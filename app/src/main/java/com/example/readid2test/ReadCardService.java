package com.example.readid2test;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.card.bean.ID2Data;
import com.sdses.readcardservice.IReadCardService;

import org.json.JSONObject;

import java.util.Arrays;

public class ReadCardService extends Service {

    private String TAG = "ReadCardService";
    private IReadCardService mIReadCardService = null;
    byte[] ID2Bytes = new byte[1280];
    /**
     * 二代证读卡服务接收器
     */
    private ID2DataReceiver mID2DataReceiver;
    private boolean mBindReadServiceIsOk = false;

    private MediaPlayer m_soundSucess;

    public ReadCardService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startReadID2Service();
        initRecevier();
        m_soundSucess = MediaPlayer.create(getApplicationContext(),
                R.raw.success);
    }

    /**
     * @throws
     * @Title: startReadID2Service
     * @author xc
     * @Description: TODO(打开二代证读卡服务)
     * @param:
     * @return: void
     */
    public void startReadID2Service() {
        Intent intent = new Intent("com.sdses.readcardservice.IReadCardService");
        intent.setPackage("com.sdses.readcardservice");
        Log.e(TAG, IReadCardService.class.getName());
        if (bindService(intent,
                readCardCon, Context.BIND_AUTO_CREATE)) {
            if (readCardCon != null) {
                mBindReadServiceIsOk = true;
                myToast("绑定读卡服务成功");
            }
        } else {
            myToast("绑定读卡服务不成功，请确认是否安装并启动服务");
        }
    }

    private void myToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    /**
     * @Fields readCardCon : TODO(读卡连接)
     */
    public ServiceConnection readCardCon = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            Log.w(TAG, "读卡服务连接不成功");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            mIReadCardService = IReadCardService.Stub.asInterface(service);
            if (mIReadCardService != null) {
                Log.w(TAG, "读卡服务连接成功");
                try {
                    mIReadCardService.startReadCard();
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    };

    private void initRecevier() {
        // 二代证
        mID2DataReceiver = new ReadCardService.ID2DataReceiver();
        IntentFilter filterID2 = new IntentFilter();// 创建IntentFilter对象
        // 注册一个广播，用于接收Activity传送过来的命令，控制Service的行为，如：发送数据，停止服务等
        // filterID2.addAction(ClientVars.receivefromserver);
        filterID2.addAction("com.sdses.readercontrol");
        registerReceiver(mID2DataReceiver, filterID2);
    }

    public class ID2DataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // log.info("ID2DataReceiver接收消息" + intent.getAction());
            Bundle bundle = intent.getExtras();
            if (intent.getAction().equals("com.sdses.readercontrol")) {
                Log.w(TAG, "开启读卡成功");
                Log.w(TAG, "readcard value is" + bundle.getInt(ClientVars.para));
                switch (bundle.getInt(ClientVars.para)) {
                    case ClientVars.deviceConnect_OK:
                        // 启动读卡服务成功
                        break;
                    case ClientVars.deviceDisConnect:
                        // 断开设备连接成功
                        break;
                    case ClientVars.receivefromcardOk:
                        Log.w(TAG, "寻卡成功...");
                        // tv_readCardStatus.setText("正在读卡...");
                        //mTVHint.setText("");
                        //clearInfo();
                        break;
                    case ClientVars.readAllInfoOk:
                        Log.w(TAG, "读卡成功");
                        m_soundSucess.start();
                        Arrays.fill(ID2Bytes, (byte) 0x00);
                        ID2Bytes = bundle.getByteArray("dataSrc");
                        Log.w(TAG, "id2 length" + ID2Bytes.length);
                        showID2Info(ID2Bytes);
                        break;
                    case ClientVars.readAddressAdd:
                        // 追加地址。建议不要读取追加地址，很少有这种证件。
                        //
                        break;
                    case ClientVars.readSAMSN:
                        Log.w(TAG, "receive samsn message");
                        // setWarningInfo("" + bundle.getString("samsn"));
                        break;

                    case ClientVars.deviceConnect_ERROR:
                        // F0 连接读卡设备失败原因
                        // setWarningInfo(bundle.getString(ClientVars.extra));
                        // mButtonReader.setChecked(false);
                        break;
                    case ClientVars.deviceDisConnect_ERROR:
                        // F3断开读卡设备失败原因
                        // setWarningInfo(bundle.getString(ClientVars.extra));
                        // mButtonReader.setAccessibilityDelegate(delegate);
                        break;
                    default: // 命令字 信息提示
                        // tv_readCardStatus.setText("读卡失败");
                        Log.w(TAG, "未知错误");
                        // setWarningInfo(bundle.getString(ClientVars.extra));
                        // mButtonReader.setChecked(false);
                        break;
                }
            }
        }
    }

    /**
     * @throws
     * @Title: clearID2Info
     * @author xc
     * @Description: TODO(清除二代证信息)
     * @param:
     * @return: void
     */

    private void showID2Info(byte[] data) {
        try {
            ID2Data _id2Data = new ID2Data();
            _id2Data.decode_debug(data);
            _id2Data.rePackage();
            Log.w(TAG, "sex" + _id2Data.getmID2Txt().getmGender().trim());
            String name = _id2Data.getmID2Txt().getmName().trim(); // 姓名
            String sex = _id2Data.getmID2Txt().getmGender().trim();
            String nation = _id2Data.getmID2Txt().getmNational().trim(); // 民族
            String bir = _id2Data.getmID2Txt().getmBirthYear().trim() + "年"
                    + _id2Data.getmID2Txt().getmBirthMonth().trim() + "月"
                    + _id2Data.getmID2Txt().getmBirthDay().trim() + "日";
            String address = _id2Data.getmID2Txt().getmAddress().trim(); // 住址
            String id2Num = _id2Data.getmID2Txt().getmID2Num().trim(); // 公民身份号码
            String issnue = _id2Data.getmID2Txt().getmIssue().trim(); // 签发机关
            String time = _id2Data.getmID2Txt().getmBegin().trim()
                    + "--" + _id2Data.getmID2Txt().getmEnd().trim(); // 有效期限
            byte[] b = _id2Data.getmID2Pic().getHeadFromCard();
            String pic = Base64.encodeToString(b, Base64.NO_WRAP);
            JSONObject obj = new JSONObject();
            obj.put("xm",name);
            obj.put("sex",sex);
            obj.put("nation",nation);
            obj.put("bir",bir);
            obj.put("address",address);
            obj.put("id2Num",id2Num);
            obj.put("issnue",issnue);
            obj.put("time",time);
            obj.put("pic",pic);
            //Bitmap bm = BitmapFactory.decodeByteArray(_id2Data.getmID2Pic()
            //       .getHeadFromCard(), 0, 38862);
            //mIVHead.setImageBitmap(bm);
            Intent intent = new Intent("com.example.readid2test");
            intent.putExtra("card", obj.toString());
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
