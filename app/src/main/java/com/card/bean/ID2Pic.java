package com.card.bean;

import ToBmp.Wlt;

import java.io.Serializable;

public class ID2Pic
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte[] mHeadFromCard = new byte[38862];

    public int decodeNoLic(byte[] _picBuff) {
        Wlt w = new Wlt();
        return w.GetBmpByBuffNoLic(_picBuff, this.mHeadFromCard);
    }

    public byte[] getHeadFromCard() {
        return this.mHeadFromCard;
    }
}
