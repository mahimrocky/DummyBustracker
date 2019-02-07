package msttech.com.justbustracker.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import msttech.com.justbustracker.R;
import msttech.com.justbustracker.data.helper.BusName;
import msttech.com.justbustracker.data.helper.IntentKeys;
import msttech.com.justbustracker.data.local.model.Driver;
import msttech.com.justbustracker.data.local.model.Student;
import msttech.com.justbustracker.ui.driver.DriverActivity;
import msttech.com.justbustracker.ui.student.StudentActivity;

public class HomeActivity extends AppCompatActivity {

    //TODO Every class or Design you have to polishing.

    Button buttonDriver, buttonStudent;

    private String userId, userName;

    private String mBusName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonDriver = findViewById(R.id.button_bus);
        buttonStudent = findViewById(R.id.button_student);

        parseIntent();

        buttonDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBusDialog();
            }
        });


        buttonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(HomeActivity.this, StudentActivity.class));
            finish();
            }
        });

    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(IntentKeys.USER_ID)) {
            userId = intent.getStringExtra(IntentKeys.USER_ID);
        }

        if (intent.hasExtra(IntentKeys.USER_NAME)) {
            userName = intent.getStringExtra(IntentKeys.USER_NAME);
        }
    }

    private void openBusDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_bus_type, null, false);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        RadioGroup radioGroup  = view.findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio_button_bus_1:
                        mBusName = BusName.BUS_1;
                        break;
                    case R.id.radio_button_bus_2:
                        mBusName = BusName.BUS_2;
                        break;
                    case R.id.radio_button_bus_3:
                        mBusName = BusName.BUS_3;
                        break;
                    case R.id.radio_button_bus_4:
                        mBusName = BusName.BUS_4;
                        break;
                }

                dialog.dismiss();


                Driver driver = new Driver();
                driver.setDriverName(userName);
                driver.setDriverId(userId);
                driver.setBusName(mBusName);

                Intent intent = new Intent(HomeActivity.this, DriverActivity.class);
                intent.putExtra(IntentKeys.DRIVER,driver);
                startActivity(intent);
                finish();


            }
        });

        dialog.show();
    }
}
