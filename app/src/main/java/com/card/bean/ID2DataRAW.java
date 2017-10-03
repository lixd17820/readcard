package com.card.bean;

import android.util.Log;

import java.io.Serializable;
import java.util.Arrays;

public class ID2DataRAW
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String TAG = ID2DataRAW.class.getSimpleName();
    private byte[] mID2TxtRAW = new byte[256];
    private byte[] mID2PicRAW = new byte[1024];
    private byte[] mID2FPRAW = new byte[1024];
    private byte[] mID2AddRAW = new byte[73];

    public ID2DataRAW() {
        Log.i(TAG, "ID2DataRAW 构造函数");
    }

    public void decode(byte[] _raw) {
        if ((_raw[0] == 1) && (_raw[1] == 0)) {
            System.arraycopy(_raw, 6, this.mID2TxtRAW, 0, 256);
            Log.i(TAG, "文字数据");
        }
        if ((_raw[2] == 4) && (_raw[3] == 0)) {
            System.arraycopy(_raw, 262, this.mID2PicRAW, 0, 1024);
            Log.i(TAG, "照片数据");
        }
        if ((_raw[4] == 4) && (_raw[5] == 0) && (_raw[1286] == 67)) {
            System.arraycopy(_raw, 1286, this.mID2FPRAW, 0, 1024);
            Log.i(TAG, "指纹数据");
        }
        if ((_raw[2310] == 0) &&
                (_raw[2311] == 0) &&
                (_raw[2312] == -112)) {
            System.arraycopy(_raw, 2310, this.mID2AddRAW, 0, 73);
            Log.i(TAG, "追加地址数据");
        }
    }

    public void decode_debug(byte[] _raw) {
        System.arraycopy(_raw, 0, this.mID2TxtRAW, 0, 256);

        System.arraycopy(_raw, 256, this.mID2PicRAW, 0, 1024);
    }

    public void clearID2DataRAW() {
        Arrays.fill(this.mID2TxtRAW, (byte) 0);
        Arrays.fill(this.mID2PicRAW, (byte) 0);
        Arrays.fill(this.mID2FPRAW, (byte) 0);
        Arrays.fill(this.mID2AddRAW, (byte) 0);
    }

    public byte[] getID2FPRAW() {
        if (this.mID2FPRAW[0] == 67) {
            Log.i(TAG, "有指纹数据");
            return this.mID2FPRAW;
        }
        Log.i(TAG, "未发现指纹数据");
        return null;
    }

    public byte[] getID2AddRAW() {
        if ((this.mID2AddRAW[0] == 0) &&
                (this.mID2AddRAW[1] == 0) &&
                (this.mID2AddRAW[2] == -112)) {
            return this.mID2AddRAW;
        }
        return null;
    }

    public byte[] getID2TxtRAW() {
        return this.mID2TxtRAW;
    }

    public byte[] getID2PicRAW() {
        return this.mID2PicRAW;
    }
}
