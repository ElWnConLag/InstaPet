package insta.pet.instapet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class C_Home extends AppCompatActivity {

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
                Intent intent = new Intent(C_Home.this, J_BuscarPerfil.class);
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
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    Intent intent = new Intent(C_Home.this, Perfil_activity.class);
                    intent.putExtra("userId", currentUser.getUid()); // Pasar la identificación única del usuario
                    startActivity(intent);
                }
            }
        });
        botonPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    Intent intent = new Intent(C_Home.this, publi_mascotas.class);
                    intent.putExtra("userId", currentUser.getUid()); // Pasar la identificación única del usuario
                    startActivity(intent);
                }
            }
        });

        final LinearLayout linearLayout5 = findViewById(R.id.linearLayout5);
        final LinearLayout linearLayoutt = findViewById(R.id.linearLayoutt);

        mostrarUltimasPublicaciones(linearLayoutt, linearLayout5);
    }

    private void mostrarUltimasPublicaciones(final LinearLayout linearLayoutt, final LinearLayout linearLayout5) {
        DatabaseReference publicacionesRef = FirebaseDatabase.getInstance().getReference("Publicaciones");

        Query ultimasPublicacionesQuery = publicacionesRef.orderByChild("timestamp").limitToLast(2);

        ultimasPublicacionesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                linearLayoutt.removeAllViews();
                linearLayout5.removeAllViews();

                List<Publicacion> publicaciones = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Publicacion publicacion = snapshot.getValue(Publicacion.class);
                    publicaciones.add(publicacion);
                }

                Collections.reverse(publicaciones);

                if (publicaciones.size() > 0) {
                    mostrarPublicacionEnLinearLayout(publicaciones.get(0), linearLayoutt);
                }
                if (publicaciones.size() > 1) {
                    mostrarPublicacionEnLinearLayout(publicaciones.get(1), linearLayout5);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores si es necesario
            }
        });
    }

    private void mostrarPublicacionEnLinearLayout(Publicacion publicacion, LinearLayout linearLayout) {
        TextView tituloTextView = new TextView(C_Home.this);
        tituloTextView.setText(publicacion.getTitulo());

        TextView descripcionTextView = new TextView(C_Home.this);
        descripcionTextView.setText(publicacion.getDescripcion());

        linearLayout.addView(tituloTextView);
        linearLayout.addView(descripcionTextView);
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
}


