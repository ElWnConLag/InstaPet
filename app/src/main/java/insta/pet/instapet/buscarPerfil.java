package insta.pet.instapet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class buscarPerfil extends AppCompatActivity {
    Button Volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_perfil);

        Volver = findViewById(R.id.volverPerfil);

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buscarPerfil.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}