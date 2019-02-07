package msttech.com.justbustracker.ui.driver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import msttech.com.justbustracker.MainActivity;
import msttech.com.justbustracker.R;
import msttech.com.justbustracker.data.helper.GpsTracker;
import msttech.com.justbustracker.data.helper.IntentKeys;
import msttech.com.justbustracker.data.local.model.Driver;

public class DriverActivity extends AppCompatActivity {

    private Driver mDriver;

    private GpsTracker gpsTracker;
    private long tvLatitude,tvLongitude;

    private Timer timer;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        parseIntent();

       /* try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }*/

       getPermission();

       timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                getPermission();
            }
        };
        timer.schedule(timerTask,0l,1*60*1000);




}

    private void  parseIntent(){
        Intent intent = getIntent();
        if(intent.hasExtra(IntentKeys.DRIVER)){
            mDriver = intent.getParcelableExtra(IntentKeys.DRIVER);
        }
    }

    private void getPermission(){
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        getLocation();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }



    private void getLocation(){
        gpsTracker = new GpsTracker(DriverActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            Log.d("LocationTest","lat: "+latitude+" lang: "+longitude);
            if(latitude>0){
                mDriver.setLatitude(latitude);
                mDriver.setLongitude(longitude);
                updateData(mDriver);
            }
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    private void updateData(Driver driver){
        mDatabase.child("BUS").child(driver.getBusName()).setValue(driver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
        }
    }
}
