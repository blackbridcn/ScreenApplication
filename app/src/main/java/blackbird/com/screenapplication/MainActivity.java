package blackbird.com.screenapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import blackbird.com.screenapplication.service.AppService;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.adb_cmd).setOnClickListener(this);
        this.findViewById(R.id.DevicePolicyManager).setOnClickListener(this);
        Intent intent = new Intent();
        intent.setClass(this, AppService.class);
        startService(intent);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.adb_cmd) {
            Intent intent = new Intent(this, AdbScreenActivity.class);
            startActivity(intent);

        } else if (id == R.id.DevicePolicyManager) {
            Intent intent = new Intent(this, DevicePolicyManagerActivity.class);
            startActivity(intent);
        }

    }


}
