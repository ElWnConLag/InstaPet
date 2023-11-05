package insta.pet.instapet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class J_BuscarPerfil extends AppCompatActivity {
    ImageButton Volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_buscarperfil_act);

        Volver = findViewById(R.id.volverBuscarPerfil);

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(J_BuscarPerfil.this, C_Home.class);
                startActivity(intent);
            }
        });
    }
}