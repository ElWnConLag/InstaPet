package insta.pet.instapet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class I_AgregarPublicacion extends AppCompatActivity {
    ImageButton botonPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.i_agregarpublicacion_act);

        //botonPublicacion = findViewById(R.id.volverPublicacion);

        botonPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(I_AgregarPublicacion.this, C_Home.class);
                startActivity(intent);
            }
        });
    }
}