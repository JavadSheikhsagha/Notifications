package jvd.ir.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static jvd.ir.notifications.NotificationHelper.displayNotification;

public class MainActivity extends AppCompatActivity {


    //1. Notification Channel
    //2. Notification Builder
    //3. Notification Manager
    private static final String ChannelId = "simplified_coding";
    private static final String ChannelName = "Simplified Coding";
    private static final String ChannelDescription = "all four types of notifications";

    private EditText edtEmail,edtPassword;
    private Button btnRegister;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(ChannelId,ChannelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(ChannelDescription);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNotification(MainActivity.this, "Titleeeeeeeee", "Bodyyyyyy", R.drawable.ic_baseline_accessibility_24);
                createUser(edtEmail.getText().toString().trim(),edtPassword.getText().toString().trim());
            }
        });


    }

    private void createUser(final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //user Register
                    Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else  {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        //user Login
                        mAuth.signInWithEmailAndPassword(email,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Toast.makeText(MainActivity.this, "Hey Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                });
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    /* private void displayNotification(){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this,ChannelId)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Big Title")
                    .setContentText("Content Text")
                    .setSubText("SubText")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,mBuilder.build());
    }

     */
}