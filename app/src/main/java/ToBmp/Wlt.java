package ToBmp;

import java.io.Serializable;

public class Wlt
        implements Serializable {
    private static final long serialVersionUID = 1L;

    static {
        try {
            System.loadLibrary("SDSES_Wlt_1.2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Wlt(){

    }

    public native int GetBmpByBuffNoLic(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

    public native int GetBmpByBuff(byte[] paramArrayOfByte1, String paramString1, String paramString2,
                                   byte[] paramArrayOfByte2);
}
