package com.card.bean;

import android.util.Log;

import java.io.Serializable;
import java.util.Arrays;

public class ID2FP
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String TAG = ID2FP.class.getSimpleName();
    private byte[] mID2FPOne = new byte[256];
    private byte[] mID2FPTwo = new byte[256];

    public boolean initFP(byte[] _fingerprint) {
        if ((_fingerprint != null) && (_fingerprint.length >= 1024)) {
            Log.i(TAG, "复制指纹数据");
            System.arraycopy(_fingerprint, 0, this.mID2FPOne, 0, 512);
            System.arraycopy(_fingerprint, 512, this.mID2FPTwo, 0, 512);
            return true;
        }
        Log.i(TAG, "未发现指纹数据");
        Arrays.fill(this.mID2FPOne, (byte) 0);
        Arrays.fill(this.mID2FPTwo, (byte) 0);
        return false;
    }

    public byte[] getmID2FPOne() {
        return this.mID2FPOne;
    }

    public byte[] getmID2FPTwo() {
        return this.mID2FPTwo;
    }

    public String getFingerPosition(int _num) {
        if (_num == 1) {
            if (this.mID2FPOne[0] == 67) {
                return _getFingerPosition(this.mID2FPOne[5]);
            }
        } else if ((_num == 2) &&
                (this.mID2FPTwo[0] == 67)) {
            return _getFingerPosition(this.mID2FPTwo[5]);
        }
        return null;
    }

    private String _getFingerPosition(byte _key) {
        String back = null;
        switch (_key) {
            case 11:
                back = "右手拇指";
                break;
            case 12:
                back = "右手食指";
                break;
            case 13:
                back = "右手中指";
                break;
            case 14:
                back = "右手环指";
                break;
            case 15:
                back = "右手小指";
                break;
            case 16:
                back = "左手拇指";
                break;
            case 17:
                back = "左手食指";
                break;
            case 18:
                back = "左手中指";
                break;
            case 19:
                back = "左手环指";
                break;
            case 20:
                back = "左手小指";
                break;
            case 97:
                back = "右手不确定指位";
                break;
            case 98:
                back = "左手不确定指位";
                break;
            case 99:
                back = "其他不确定指位";
                break;
            default:
                back = null;
        }
        return back;
    }
}
