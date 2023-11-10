package insta.pet.instapet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class L_PerfilDueno extends AppCompatActivity {

    private int followersCount = 0;
    private Button botonSeguirPerfil;
    private TextView buttonseguidos;

    @SuppressLint("WrongViewCast")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_perfildueno_act);

        botonSeguirPerfil = findViewById(R.id.botonSeguirPerfil);
        buttonseguidos = findViewById(R.id.buttonseguidos);

        botonSeguirPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Incrementa el contador de seguidores al hacer clic en el botón
                followersCount++;
                // Actualiza el texto del TextView con el nuevo número de seguidores
                buttonseguidos.setText("Seguidores: " + followersCount);
            }
        });
    }
}