package insta.pet.instapet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    ImageButton botonVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_main);

        botonVolver = findViewById(R.id.backButton);

        // Obtener referencia a los elementos ImageView y TextView
        ImageView imageViewProfile = findViewById(R.id.imageView4);
        TextView textViewUsername = findViewById(R.id.textViewUsername);

        // Inicializa la referencia a Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("DatosUsuario");

        // Botón "Editar Perfil"
        Button editarPerfilButton = findViewById(R.id.editarperfil_bt);
        editarPerfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea un Intent para abrir la actividad nuevaMascota
                Intent intent = new Intent(Perfil_activity.this, nuevaMascota.class);
                startActivity(intent); // Inicia la nueva actividad
            }
        });

        // Leer los datos del nombre de usuario y la URL de la imagen desde la base de datos
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombreUsuario = dataSnapshot.child("nombre").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imagenPerfil").getValue(String.class);

                    // Configurar la imagen y el nombre de usuario en los elementos ImageView y TextView
                    Picasso.get().load(imageUrl).into(imageViewProfile);
                    textViewUsername.setText(nombreUsuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de lectura de la base de datos si es necesario
            }
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil_activity.this, C_Home.class);
                startActivity(intent);
            }
        });
    }
}
