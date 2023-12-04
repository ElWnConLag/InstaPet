package insta.pet.instapet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import insta.pet.instapet.adapter.AdapterMascota;
import insta.pet.instapet.adapter.AdapterPublicaciones;
import insta.pet.instapet.pojo.Publicaciones;

public class C_Home extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<Publicaciones> listP;
    ListView listView;
    AdapterPublicaciones adapter;

    ImageButton botonAdopcion;
    ImageButton botonBuscar;
    ImageButton botonSettings;
    ImageButton botonPublicacion;
    ImageButton botonPerfil;


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_home_act);

        ref = FirebaseDatabase.getInstance().getReference().child("Publicaciones");
        listView = findViewById(R.id.listViewP);
        listP = new ArrayList<>();
        adapter = new AdapterPublicaciones(this, listP);
        listView.setAdapter(adapter);

        botonAdopcion = findViewById(R.id.btnAdoptar);
        botonBuscar = findViewById(R.id.btnBuscar);
        botonSettings = findViewById(R.id.btnAjustes);
        botonPerfil = findViewById(R.id.perfil);
        botonPublicacion = findViewById(R.id.AgregarPublicacion);



        mAuth = FirebaseAuth.getInstance();



        botonAdopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_Home.this, avisosAdopcion.class);
                startActivity(intent);
            }
        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_Home.this, BuscadorMascotas.class);
                startActivity(intent);
            }
        });
        botonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_Home.this, K_Ajustes.class);
                startActivity(intent);
            }
        });
        botonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_Home.this, Perfil_activity.class);
                startActivity(intent);
            }
        });
        botonPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_Home.this, I_AgregarPublicacion.class);
                startActivity(intent);
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Mascotas ms = snapshot.getValue(Mascotas.class);
                        listP.add(ms);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            irMain();
        }
    }
    private void irMain() {
        Intent intent = new Intent(C_Home.this, A_Login.class);
        startActivity(intent);
        finish();
    }
    public void redirectToSecondLayout(View view) {
        Intent intent = new Intent(this, Perfil_activity.class);
        startActivity(intent);
    }
}