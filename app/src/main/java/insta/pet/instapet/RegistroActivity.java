package insta.pet.instapet;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.graphics.Color;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    private EditText mEditTextEmail;
    private EditText mEditTextPass;
    private EditText mEditTextConfirmarPass;
    private Button mButtonRegistrar;
    private TextView mTextViewRespuestaR;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mEditTextEmail = findViewById(R.id.editTextEmail);
        mEditTextPass = findViewById(R.id.editTextPass);
        mEditTextConfirmarPass = findViewById(R.id.editConfirmarPass);
        mButtonRegistrar = findViewById(R.id.btnRegistrar);
        mTextViewRespuestaR = findViewById(R.id.textViewRespuestaR);

        mAuth = FirebaseAuth.getInstance();

        mButtonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEditTextEmail.getText().toString().trim();
                String pass = mEditTextPass.getText().toString().trim();
                String conPass = mEditTextConfirmarPass.getText().toString().trim();

                if (email.isEmpty() || pass.isEmpty() || conPass.isEmpty()) {
                    mostrarMensaje("Ingrese todos los datos.");
                } else if (!emailValido(email)) {
                    mostrarMensaje("Email inválido.");
                } else if (!pass.equals(conPass)) {
                    mostrarMensaje("Las contraseñas no coinciden.");
                } else if (pass.length() < 6) {
                    mostrarMensaje("La contraseña debe tener al menos 6 caracteres.");
                } else {
                    registrarUsuario(email, pass);
                }
            }
        });
    }

    private void registrarUsuario(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mostrarMensaje("Cuenta creada con éxito.");
                    mTextViewRespuestaR.setTextColor(Color.GREEN);
                } else {
                    mostrarMensaje("Error: " + task.getException().getMessage());
                    mTextViewRespuestaR.setTextColor(Color.RED);
                }
            }
        });
    }

    private boolean emailValido(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void mostrarMensaje(String mensaje) {
        mTextViewRespuestaR.setText(mensaje);
    }
}