package jvd.ir.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        NotificationHelper.displayNotification(this,"title","body",R.drawable.ic_baseline_accessibility_24);

        mAuth = FirebaseAuth.getInstance();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            String token = task.getResult().getToken();
                            saveToken(token);
                        }else {
                            Toast.makeText(ProfileActivity.this, "No Token", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveToken(String token) {
        
        String email = mAuth.getCurrentUser().getEmail();

        MyCustomModel model = new MyCustomModel(email,token);



    }
}