package com.example.push_notification_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.NCMB;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBInstallation;
import com.nifcloud.mbaas.core.NCMBObject;
import com.nifcloud.mbaas.core.NCMBPush;
import com.nifcloud.mbaas.core.NCMBQuery;
import com.nifcloud.mbaas.core.NCMBFirebaseMessagingService;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    final String APP_KEY = "2bfb444423219ff54256bbe41ff270c5d8c3e81eaa3121c18603363e99b0b673";
    final String CLIENT_KEY = "2e0167555ae06b73a73a8b2ef1ea9614d566b17cb7c0d191da80797221088bf2";
    Button push,pushToChannel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        NCMB.initialize(this.getApplicationContext(), APP_KEY, CLIENT_KEY);
        setContentView(R.layout.activity_main);
        push = (Button) findViewById(R.id.push);
        pushToChannel = (Button) findViewById(R.id.push_channel);

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendPush();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        pushToChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChannel();
                testPushWithSearchCondition();
            }
        });
    }


    private void setChannel(){
        NCMBInstallation installation = NCMBInstallation.getCurrentInstallation();
        JSONArray channels = new JSONArray();
        channels.put("Ch1");
        installation.setChannels(channels);
        installation.saveInBackground(new DoneCallback() {
            @Override
            public void done(NCMBException e) {
                if (e != null) {
                    //???????????????????????????
                } else {
                    //??????????????????
                }
            }
        });
    }
    private void testPushWithSearchCondition(){
        NCMBPush push = new NCMBPush();
        NCMBQuery<NCMBInstallation> query = new NCMBQuery<>("installation");
        query.whereEqualTo("channels", "Ch1");
        push.setSearchCondition(query);
        push.setMessage("test SearchCondition");
        push.sendInBackground();
    }
    private void sendPush() throws JSONException {
        NCMBPush push = new NCMBPush();
        push.setAction("com.example.push_notification_demo");
        push.setTitle("test title");
        push.setMessage("send push!");
        push.setTarget(new JSONArray("[android]"));
        push.setDialog(true);
        push.sendInBackground(new DoneCallback() {
            @Override
            public void done(NCMBException e) {
                if (e != null) {
                    // ???????????????
                } else {
                    // ????????????????????????????????????

                }
            }

        });
    }

}