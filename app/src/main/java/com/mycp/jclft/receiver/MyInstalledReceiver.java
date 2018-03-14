package com.mycp.jclft.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mycp.jclft.R;
import com.mycp.jclft.utils.CacheActivity;

public class MyInstalledReceiver extends BroadcastReceiver {

    private String bagname;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            bagname = context.getResources().getString(R.string.bagname);
            if (packageName.equals("package:"+bagname)){
                Uri packageUri = Uri.parse("package:"+context.getPackageName());
                Intent i = new Intent(Intent.ACTION_DELETE,packageUri);
                context.startActivity(i);
                CacheActivity.finishActivity();
            }
        }
    }
}
