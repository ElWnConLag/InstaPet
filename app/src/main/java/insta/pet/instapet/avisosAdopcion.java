package insta.pet.instapet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class avisosAdopcion extends AppCompatActivity {
    Button Volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos_adopcion);

        Volver = findViewById(R.id.volverAdopcion);

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(avisosAdopcion.this, C_Home.class);
                startActivity(intent);
            }
        });

    }
}