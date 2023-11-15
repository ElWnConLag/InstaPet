package insta.pet.instapet;

import android.os.Bundle;
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

public class L_PerfilDueno extends AppCompatActivity {

    private TextView nombreperfil1;
    private ImageView imagenperfil1;
    private Button botonSeguirPerfil;
    private TextView buttonseguidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_perfildueno_act);

        nombreperfil1 = findViewById(R.id.textViewUsername);
        imagenperfil1 = findViewById(R.id.imageView3);

        // Inicializa la referencia a Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DatosUsuario");

        // Lee los datos del nombre de usuario y la URL de la imagen desde la base de datos
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombreUsuario = dataSnapshot.child("nombre").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imagenPerfil").getValue(String.class);

                    // Configura la imagen y el nombre de usuario en los elementos ImageView y TextView
                    Picasso.get().load(imageUrl).into(imagenperfil1);
                    nombreperfil1.setText(nombreUsuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de lectura de la base de datos si es necesario
            }

            class secondActivity extends AppCompatActivity {
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.l_perfildueno_act);
                }
            }
        });

    }
}
