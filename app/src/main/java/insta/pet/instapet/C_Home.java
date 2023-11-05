package insta.pet.instapet;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class C_Home extends AppCompatActivity {

    ImageButton botonAdopcion;
    ImageButton botonBuscar;
    ImageButton botonSettings;
    ImageButton botonPublicacion;
    ImageButton botonPerfil;


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_home_act);

        botonAdopcion = findViewById(R.id.btnAdoptar);
        botonBuscar = findViewById(R.id.btnBuscar);
        botonSettings = findViewById(R.id.btnAjustes);
        botonPerfil = findViewById(R.id.perfil);
        botonPublicacion = findViewById(R.id.AgregarPublicacion);



        mAuth = FirebaseAuth.getInstance();



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
        botonPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_Home.this, I_AgregarPublicacion.class);
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