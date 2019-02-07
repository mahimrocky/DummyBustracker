package msttech.com.justbustracker.ui.student;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import msttech.com.justbustracker.R;
import msttech.com.justbustracker.data.helper.BusName;
import msttech.com.justbustracker.data.local.model.Driver;

public class StudentActivity extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ref = database.child("BUS").child(BusName.BUS_1);

    TextView bus1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        bus1 = findViewById(R.id.text_view_bus_1);


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Driver driver = dataSnapshot.getValue(Driver.class);

                if(driver!=null){
                    Log.d("DataTEst","Lat: "+driver.getLatitude());
                    bus1.setText("Bus1: Lat: "+driver.getLatitude()+" Lang: "+driver.getLongitude());
                }else{
                    Log.d("DataTEst","Driver null");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
