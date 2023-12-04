package insta.pet.instapet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    private static final String BROKER_URL = "tcp://your-broker-url:1883";
    private static final String CLIENT_ID = "your_client_id";

    private MqttHandler mqttHandler;

    EditText mEditTextEmail;
    EditText mEditTextPass;
    Button mButtonInicio;
    TextView mTextViewRespuesta;
    TextView mTextViewIrRegistrar;

    FirebaseAuth mAuth;

    String email;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextEmail = findViewById(R.id.editTextEmail);
        mEditTextPass = findViewById(R.id.editTextPass);
        mButtonInicio = findViewById(R.id.btnInicio);
        mTextViewRespuesta = findViewById(R.id.textViewRespuesta);
        mTextViewIrRegistrar = findViewById(R.id.textViewIrRegistrar);

            mqttHandler = new MqttHandler();
            mqttHandler.connect(BROKER_URL, CLIENT_ID); //MQTT
            publishMessage("user", "Usuario agregado al registro");

        mAuth = FirebaseAuth.getInstance();


        mTextViewIrRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        mButtonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEditTextEmail.getText().toString().trim();
                pass = mEditTextPass.getText().toString().trim();

                if (email.isEmpty() || pass.isEmpty()) {
                    mTextViewRespuesta.setText("Ingrese el email y la contraseña");
                    mTextViewRespuesta.setTextColor(Color.RED);

                } else {
                    if (emailValido(email)) {
                        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mTextViewRespuesta.setText("CORRECTO");
                                    mTextViewRespuesta.setTextColor(Color.GREEN);
                                    irHome();
                                } else {
                                    mTextViewRespuesta.setText("CREDENCIALES INCORRECTAS");
                                    mTextViewRespuesta.setTextColor(Color.RED);
                                }
                            }
                        });
                    } else {
                        mTextViewRespuesta.setText("Email invalido");
                        mTextViewRespuesta.setTextColor(Color.RED);
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            irHome();
        }
    }

    private void irHome(){
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    private boolean emailValido(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Override                           //MQTT
    protected void onDestroy() {
        mqttHandler.disconnect();
        super.onDestroy();


    }
    private void publishMessage(String topic, String messege){

        Toast.makeText(this, "Mensaje a Publicar:"+messege, Toast.LENGTH_SHORT).show();
        mqttHandler.publish(topic,messege);
    }

    private void subscribeTopic(String topic){
        Toast.makeText(this, "Subscribing topic" + topic, Toast.LENGTH_SHORT).show();
        mqttHandler.subscribe(topic);


    }

}