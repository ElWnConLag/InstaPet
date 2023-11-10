package insta.pet.instapet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Perfil_activity extends AppCompatActivity {

    private DatabaseReference myRef;
    private ImageView imagenmascota1;
    private TextView nombreMascota1;
    private ImageView imagenperfil1;
    private TextView nombreperfil1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_main);


        Button editarPerfilButton = findViewById(R.id.editarperfil_bt);
        editarPerfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea un Intent para abrir la actividad "nuevaMascota"
                Intent intent = new Intent(Perfil_activity.this, nuevaMascota.class);
                startActivity(intent); // Inicia la nueva actividad
            }
        });


        Button botonHiloMascota = findViewById(R.id.boton_hilomascota);
        botonHiloMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea un Intent para abrir la actividad "parte2jerson"
                Intent intent = new Intent(Perfil_activity.this, parte2j.class);
                startActivity(intent); // Inicia la nueva actividad
            }
        });

        // Obtiene la referencia a los elementos de la mascota en activity_perfil_main.xml
        imagenmascota1 = findViewById(R.id.imagenmascota1);
        nombreMascota1 = findViewById(R.id.nombrehilomascota11);
        imagenperfil1 = findViewById(R.id.imageView4);
        nombreperfil1 = findViewById(R.id.textViewUsername);

        // Inicializa la referencia a Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("DatosUsuario");

        // Lee los datos del nombre de usuario y la URL de la imagen desde la base de datos
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombreUsuario = dataSnapshot.child("nombre").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imagenPerfil").getValue(String.class);
                    Picasso.get().load(imageUrl).into(imagenperfil1);
                    nombreperfil1.setText(nombreUsuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        
        // Lee los datos de las mascotas
        DatabaseReference mascotaRef = database.getReference("Mascotas");

        mascotaRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot mascotaSnapshot : dataSnapshot.getChildren()) {
                        String nombreMascota = mascotaSnapshot.child("nombre").getValue(String.class);
                        String imagenMascota = mascotaSnapshot.child("imagenUrl").getValue(String.class);
                        Picasso.get().load(imagenMascota).into(imagenmascota1);
                        nombreMascota1.setText(nombreMascota);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
