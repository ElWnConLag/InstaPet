package insta.pet.instapet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class A_Login extends AppCompatActivity {


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
        setContentView(R.layout.a_login_act);

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
                Intent intent = new Intent(A_Login.this, B_Register.class);
                startActivity(intent);
            }
        });

        mButtonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEditTextEmail.getText().toString().trim();
                pass = mEditTextPass.getText().toString().trim();

                if (email.isEmpty() || pass.isEmpty()) {
                    mostrarRespuesta("Ingrese el email y la contraseña", Color.RED);
                } else {
                    if (emailValido(email)) {
                        iniciarSesion(email, pass);
                    } else {
                        mostrarRespuesta("Email invalido", Color.RED);
                    }
                }
            }
        });
    }

    private void iniciarSesion(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mostrarRespuesta("CORRECTO", Color.GREEN);
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        irHome(currentUser.getUid());
                    }
                } else {
                    mostrarRespuesta("CREDENCIALES INCORRECTAS", Color.RED);
                }
            }
        });
    }

    private void irHome(String userId) {
        Intent intent = new Intent(A_Login.this, C_Home.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    private void mostrarRespuesta(String mensaje, int color) {
        mTextViewRespuesta.setText(mensaje);
        mTextViewRespuesta.setTextColor(color);
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





