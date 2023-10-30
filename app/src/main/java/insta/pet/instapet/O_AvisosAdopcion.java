package insta.pet.instapet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class O_AvisosAdopcion extends AppCompatActivity {

    private TextView editTextNombrePerro;

    private TextView sexoPerro;

    private TextView ubicacionPerro;

    private DatabaseReference mDataBase;
    Button Volver;
    Button agregarAvisoo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.o_avisosadopcion_act);

        Volver = findViewById(R.id.volverAdopcion);
        agregarAvisoo = findViewById(R.id.agregarAvisoo);
        editTextNombrePerro = (TextView) findViewById(R.id.perroNombre);
        sexoPerro = (TextView)findViewById(R.id.sexoPerroAviso);
        ubicacionPerro = (TextView)findViewById(R.id.ubicacionPerro);
        mDataBase = FirebaseDatabase.getInstance().getReference("DatosPerro");


        mDataBase.child("nombrePerro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombre = dataSnapshot.getValue().toString();
                    editTextNombrePerro.setText("El nombre es: " + nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDataBase.child("sexo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String sexo = dataSnapshot.getValue().toString();
                    sexoPerro.setText("El sexo es: " + sexo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDataBase.child("ubicacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String ubicacion = dataSnapshot.getValue().toString();
                    ubicacionPerro.setText("Ubicacion: " + ubicacion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(O_AvisosAdopcion.this, C_Home.class);
                startActivity(intent);
            }
        });
        agregarAvisoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(O_AvisosAdopcion.this, P_AgregarAviso.class);
                startActivity(intent);
                    }
                }


        );

    }


}
