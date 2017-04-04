package com.ctis8.atoi.touchlock;

/**
 * Created by Atoi on 27.03.2017.
 */
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

public class MyHostApduService extends HostApduService {

    private int messageCounter = 0;
    private String key = "team8";

    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        if (selectAidApdu(apdu)) {
            Log.i("HCEDEMO", "");
            return getWelcomeMessage();
        }
        else {
            //String msg = new String(apdu);
            Log.i("HCEDEMO", "Received: " + new String(apdu));
			/*if(msg.equals("unlocked")){
				MainActivity.img.setImageResource(R.drawable.unlockimg);
			}
			else{
				MainActivity.img.setImageResource(R.drawable.lockimg);
			}*/
            return getNextMessage();
        }
    }

    private byte[] getWelcomeMessage() {
        return ("Hello Desktop").getBytes();
    }

    private byte[] getNextMessage() {
        return "none".getBytes();
    }

    private boolean selectAidApdu(byte[] apdu) {
        return apdu.length >= 2 && apdu[0] == (byte)0 && apdu[1] == (byte)0xa4;
    }

    @Override
    public void onDeactivated(int reason) {
        Log.i("HCEDEMO", "Deactivated: " + reason);
    }
}
