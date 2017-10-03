package com.card.bean;

import android.util.Log;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class ID2Data
        extends ID2DataRAW
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String TAG = ID2Data.class.getSimpleName();
    private ID2Txt mID2Txt;
    private ID2Pic mID2Pic;
    private ID2FP mID2FP;
    private String mID2NewAddress;
    private boolean mHaveFP = false;

    public ID2Data() {
        Log.i(TAG, "ID2Data 构造函数");
        this.mID2Txt = new ID2Txt();
        this.mID2Pic = new ID2Pic();
        this.mID2FP = new ID2FP();
    }

    public int rePackage() {
        this.mID2Txt.decode(getID2TxtRAW());
        this.mID2Pic.decodeNoLic(getID2PicRAW());
        this.mHaveFP = this.mID2FP.initFP(getID2FPRAW());
        try {
            this.mID2NewAddress = (getID2AddRAW() == null ? null : new String(
                    getID2AddRAW(), 3, 70, "UTF-16LE"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public String getmID2NewAddress() {
        return this.mID2NewAddress;
    }

    public ID2Txt getmID2Txt() {
        return this.mID2Txt;
    }

    public ID2Pic getmID2Pic() {
        return this.mID2Pic;
    }

    public ID2FP getmID2FP() {
        if (this.mHaveFP) {
            return this.mID2FP;
        }
        return null;
    }
}
