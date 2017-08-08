package com.dasu.adb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.dasu.adb.R.id.btn_main_stop_abd;

public class MainActivity extends AppCompatActivity {

    private static final String[] COMMAND_START_ADB_WIFI = {
            "setprop service.adb.tcp.port 5555",
            "stop adbd",
            "start adbd"
    };
    private static final String[] COMMAND_STOP_ADD_WIFI = {
            "setprop service.adb.tcp.port -1",
            "stop adbd",
            "start adbd"
    };

    private Button mStartAdbBtn;
    private Button mStopAdbBtn;
    private TextView mResultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartAdbBtn = (Button) findViewById(R.id.btn_main_start_adb);
        mStopAdbBtn = (Button) findViewById(btn_main_stop_abd);
        mResultTv = (TextView) findViewById(R.id.tv_main_cmd_info);
        mStartAdbBtn.setOnClickListener(onStartAdbBtnClick());
        mStopAdbBtn.setOnClickListener(onStopAdbBtnClick());
    }

    private View.OnClickListener onStartAdbBtnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShellUtil.CommandResult result = ShellUtil.execCmd(COMMAND_START_ADB_WIFI, false, true);
                if (TextUtils.isEmpty(result.successMsg)) {
                    mResultTv.setText(result.errorMsg);
                } else {
                    mResultTv.setText(result.successMsg);
                }
            }
        };
    }

    private View.OnClickListener onStopAdbBtnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShellUtil.CommandResult result = ShellUtil.execCmd(COMMAND_STOP_ADD_WIFI, false, true);
                if (TextUtils.isEmpty(result.successMsg)) {
                    mResultTv.setText(result.errorMsg);
                } else {
                    mResultTv.setText(result.successMsg);
                }
            }
        };
    }
}
