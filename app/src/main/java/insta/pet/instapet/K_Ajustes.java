package insta.pet.instapet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.Toast;

public class K_Ajustes extends AppCompatActivity {

    Button Volver;
    private FirebaseAuth mAuth;
    private Button deleteAccountButton;
    private Button botonDesactivarNotificaciones;

    Button mButtonCerrarSecion;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String NOTIFICATIONS_ENABLED_KEY = "notifications_enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.k_ajustes_act);

        Volver = findViewById(R.id.volverSettings);
        botonDesactivarNotificaciones = findViewById(R.id.botonDesactivarNotificaciones);
        mButtonCerrarSecion = findViewById(R.id.CerrarSesion);


        //boton de volver
        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(K_Ajustes.this, C_Home.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        deleteAccountButton = findViewById(R.id.deleteAccountButton);

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Cuenta eliminada con éxito
                                        Toast.makeText(K_Ajustes.this, "La cuenta se ha eliminado con éxito.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Error al eliminar la cuenta
                                        Toast.makeText(K_Ajustes.this, "Error al eliminar la cuenta.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        botonDesactivarNotificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar el estado de las notificaciones en las Preferencias Compartidas
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                boolean notificationsEnabled = settings.getBoolean(NOTIFICATIONS_ENABLED_KEY, true);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(NOTIFICATIONS_ENABLED_KEY, !notificationsEnabled);
                editor.apply();

                if (notificationsEnabled) {
                    Toast.makeText(K_Ajustes.this, "Notificaciones desactivadas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(K_Ajustes.this, "Notificaciones habilitadas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonCerrarSecion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                irMain();
            }
        });


    }
    private void irMain() {
        Intent intent = new Intent(K_Ajustes.this, A_Login.class);
        startActivity(intent);
        finish();
    }
}
