package insta.pet.instapet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mascotaDatos extends AppCompatActivity {

    private ImageView imagenPerfilPerro;
    private TextView editTextNombrePerro, raza_id, sexoPerro, ubicacionPerro;

    private DatabaseReference mDataBase;
    private ImageButton volverDetalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota_datos);

        volverDetalles = findViewById(R.id.volverDetalles);

        volverDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mascotaDatos.this, avisosAdopcion.class);
                startActivity(intent);
            }
        });

        ubicacionPerro = findViewById(R.id.ubicacionContacto);
        sexoPerro = findViewById(R.id.sexoContacto);
        raza_id = findViewById(R.id.razaContacto);
        imagenPerfilPerro = findViewById(R.id.imagenPerfilContacto);
        editTextNombrePerro = findViewById(R.id.nombreContacto);
        mDataBase = FirebaseDatabase.getInstance().getReference("DatosPerro");

        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String imagenUrl = dataSnapshot.child("imagenPerfil").getValue(String.class);
                    String nombrePerro = dataSnapshot.child("nombrePerro").getValue(String.class);
                    String raza = dataSnapshot.child("raza").getValue(String.class);
                    String sexo = dataSnapshot.child("sexo").getValue(String.class);
                    String ubicacion = dataSnapshot.child("ubicacion").getValue(String.class);

                    // Carga la imagen utilizando Picasso y muestra la imagen en el ImageView
                    Picasso.get().load(imagenUrl).into(imagenPerfilPerro);

                    // Muestra los datos en los TextViews
                    editTextNombrePerro.setText("" + nombrePerro);
                    raza_id.setText("" + raza);
                    sexoPerro.setText("" + sexo);
                    ubicacionPerro.setText(" " + ubicacion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Maneja el error si es necesario
            }
        });
    }
}