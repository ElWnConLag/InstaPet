package insta.pet.instapet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class avisosAdopcion extends AppCompatActivity {

    private ImageView imagenPerfilPerro;

    private TextView descripcionPerro;

    private DatabaseReference mDataBase;
    Button Volver;
    Button agregarAvisoo;

    Button detalles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos_adopcion);

        Volver = findViewById(R.id.volverAdopcion);
        agregarAvisoo = findViewById(R.id.agregarAvisoo);
        imagenPerfilPerro = (ImageView) findViewById(R.id.perroImagen);
        descripcionPerro = (TextView)findViewById(R.id.perroDescripcion);
        mDataBase = FirebaseDatabase.getInstance().getReference("DatosPerro");


        mDataBase.child("imagenPerfil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String imagenUrl = dataSnapshot.getValue().toString();

                    // Carga la imagen utilizando Picasso y muestra la imagen en el ImageView
                    Picasso.get().load(imagenUrl).into(imagenPerfilPerro);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Maneja el error si es necesario
            }
        });


        mDataBase.child("descripcion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String descripcion = dataSnapshot.getValue().toString();
                    descripcionPerro.setText("descripcion: " + descripcion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(avisosAdopcion.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        agregarAvisoo.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(avisosAdopcion.this, agregarAviso.class);
                                                 startActivity(intent);
                                            }
                                         }


        );

        detalles = findViewById(R.id.detalles);

        detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(avisosAdopcion.this, mascotaDatos.class);
                startActivity(intent);
            }
        });




    }


}


