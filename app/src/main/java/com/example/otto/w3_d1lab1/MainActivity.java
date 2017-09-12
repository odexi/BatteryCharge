package com.example.otto.w3_d1lab1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView mBatteryLevelText;
    private ProgressBar mBatteryLevelProgress;
    private BroadcastReceiver mReceiver;
    private TextView mBatteryTemperature;
    private TextView mBatteryStatus;
    private TextView mBatteryVoltage;
    private TextView mBatteryHealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBatteryLevelText = (TextView) findViewById(R.id.batterylevel);
        mBatteryLevelProgress = (ProgressBar) findViewById(R.id.progressBar);
        mBatteryTemperature = (TextView) findViewById(R.id.batterytemperature);
        mBatteryStatus = (TextView) findViewById(R.id.status);
        mBatteryVoltage = (TextView) findViewById(R.id.voltage);
        mBatteryHealth = (TextView) findViewById(R.id.health);
        mReceiver = new MyReceiver();
    }

    @Override
    protected void onStart() {
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mReceiver);
        super.onStop();
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            float temperature = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0))/10;
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
            float voltage = (intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0))/1000;
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            mBatteryLevelText.setText(getString(R.string.battery_level) +
             " " + level);
            mBatteryLevelProgress.setProgress(level);
            mBatteryTemperature.setText(getString(R.string.battery_temperature) +
            " " + temperature +" "+ (char) 0x00B0 +"C");

            if(status == 2) {
                mBatteryStatus.setText(getString(R.string.battery_status) +
                        " " + getString(R.string.battery_charging));
            }else {
                mBatteryStatus.setText(getString(R.string.battery_status) +
                        " " + getString(R.string.battery_not_charging));
            }
            mBatteryVoltage.setText(getString(R.string.battery_voltage) +
            " " + voltage + "V");
            mBatteryHealth.setText(getString(R.string.battery_health) +
            " " + health);
            if(health == 7) {
                mBatteryHealth.setText(getString(R.string.battery_health) +
                        " " + getString(R.string.health_cold));
            }
            else if(health == 4){
                mBatteryHealth.setText(getString(R.string.battery_health) +
                        " " + getString(R.string.health_dead));
            }
            else if(health == 2){
                mBatteryHealth.setText(getString(R.string.battery_health) +
                        " " + getString(R.string.health_good));
            }
            else if(health == 5){
                mBatteryHealth.setText(getString(R.string.battery_health) +
                        " " + getString(R.string.health_over_voltage));

            }
            else if(health == 1){
                mBatteryHealth.setText(getString(R.string.battery_health) +
                        " " + getString(R.string.health_unknown));
            }
            else if(health == 6){
                mBatteryHealth.setText(getString(R.string.battery_health) +
                        " " + getString(R.string.health_unknown));
            }
            else if(health == 3) {
                mBatteryHealth.setText(getString(R.string.battery_health) +
                        " " + getString(R.string.health_overheat));
            }

        }
    }

}

