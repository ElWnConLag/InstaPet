package insta.pet.instapet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.google.firebase.database.ChildEventListener;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import insta.pet.instapet.adapter.AdapterImgMascotas;
import insta.pet.instapet.pojo.ImagenMascotas;

public class Perfil_activity extends AppCompatActivity {

    private DatabaseReference myRef;
    private DatabaseReference refImg;
    private ImageView imagenmascota1;
    private TextView nombreMascota1;
    private ImageView imagenperfil1;
    private TextView nombreperfil1;

    ArrayList<ImagenMascotas>  list;
    RecyclerView rv_img;
    AdapterImgMascotas  adapter;
    LinearLayoutManager  lm_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_main);

        refImg = FirebaseDatabase.getInstance().getReference().child("Mascotas");
        rv_img = findViewById(R.id.rv_img);

        // Configuración del LinearLayoutManager para que sea horizontal
        lm_img = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_img.setLayoutManager(lm_img);

        list = new ArrayList<>();
        adapter = new AdapterImgMascotas(list);
        rv_img.setAdapter(adapter);

        refImg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()){
                    for (DataSnapshot snapshot  : datasnapshot.getChildren()){
                        ImagenMascotas  im  = snapshot.getValue(ImagenMascotas.class);
                        list.add(im);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();

        Button editarPerfilButton = findViewById(R.id.editarperfil_bt);
        editarPerfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil_activity.this, editar_perfil_j.class);
                startActivity(intent);
            }
        });

        //imagenmascota1 = findViewById(R.id.imagenmascota1);
        nombreMascota1 = findViewById(R.id.nombrehilomascota11);
        imagenperfil1 = findViewById(R.id.imageView4);
        nombreperfil1 = findViewById(R.id.textViewUsername);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("DatosUsuario");

        myRef.child(uid).addValueEventListener(new ValueEventListener() {
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
                // Manejar errores si es necesario
            }
        });

        DatabaseReference mascotaRef = database.getReference("Mascotas").child(uid);

        mascotaRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String nombreMascota = dataSnapshot.child("nombre").getValue(String.class);
                String imagenMascota = dataSnapshot.child("imagenUrl").getValue(String.class);

                // Actualiza la interfaz de usuario con la última mascota
                Picasso.get().load(imagenMascota).into(imagenmascota1);
                nombreMascota1.setText(nombreMascota);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Este método se llama cuando hay cambios en las mascotas
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // Este método se llama cuando se elimina una mascota
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Este método se llama cuando una mascota cambia de posición
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores si es necesario
            }
        });

        Button botonHiloMascota = findViewById(R.id.boton_hilomascota);
        botonHiloMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil_activity.this, parte2j.class);
                startActivity(intent);
            }
        });
    }
}
