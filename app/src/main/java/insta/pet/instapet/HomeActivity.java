package insta.pet.instapet;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    Button mButtonCerrarSecion;
    Button botonAdopcion;
    Button botonBuscar;
    Button botonAjustes;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mButtonCerrarSecion = findViewById(R.id.btnCerrarSecion);
        botonAdopcion = findViewById(R.id.adopcion);
        botonBuscar = findViewById(R.id.buscar);
        botonAjustes =findViewById(R.id.ajustes);

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
                Intent intent = new Intent(HomeActivity.this, avisosAdopcion.class);
                startActivity(intent);
            }
        });
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, buscarPerfil.class);
                startActivity(intent);
            }
        });
        botonAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Ajustes.class);
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
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}