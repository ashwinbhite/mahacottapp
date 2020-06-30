package com.cotton.mahacott.util;

import android.os.Environment;

public class CheckForSdCard {

	public boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(
 
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}
