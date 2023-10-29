package insta.pet.instapet;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class C_Home extends AppCompatActivity {

    Button mButtonCerrarSecion;
    Button botonAdopcion;
    Button botonBuscar;
    Button botonSettings;
    Button botonPerfil;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_home_act);

        mButtonCerrarSecion = findViewById(R.id.btnCerrarSecion);
        botonAdopcion = findViewById(R.id.adopcion);
        botonBuscar = findViewById(R.id.buscar);
        botonSettings = findViewById(R.id.settings);
        botonPerfil = findViewById(R.id.irPerfil);


        mAuth = FirebaseAuth.getInstance();

        mButtonCerrarSecion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                irMain();
            }
        });

        botonAdopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_Home.this, avisosAdopcion.class);
                startActivity(intent);
            }
        });
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_Home.this, J_BuscarPerfil.class);
                startActivity(intent);
            }
        });
        botonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_Home.this, K_Ajustes.class);
                startActivity(intent);
            }
        });
        botonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_Home.this, Perfil_activity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            irMain();
        }
    }
    private void irMain() {
        Intent intent = new Intent(C_Home.this, A_Login.class);
        startActivity(intent);
        finish();
    }
}