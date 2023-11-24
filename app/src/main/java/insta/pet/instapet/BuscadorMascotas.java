package insta.pet.instapet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import insta.pet.instapet.adapter.AdapterMascota;

public class BuscadorMascotas extends AppCompatActivity {

    DatabaseReference ref;
    ArrayList<Mascotas> list;
    ListView listView;
    AdapterMascota adapter;
    ImageButton volver;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador_mascotas);

        volver = findViewById(R.id.volverBuscar);

        ref = FirebaseDatabase.getInstance().getReference().child("Mascotas");
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.search);

        list = new ArrayList<>();
        adapter = new AdapterMascota(this, list);
        listView.setAdapter(adapter);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuscadorMascotas.this, C_Home.class);
                startActivity(intent);
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Mascotas ms = snapshot.getValue(Mascotas.class);
                        list.add(ms);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                return true;
            }
        });


    }
    private void buscar (String s){
        ArrayList<Mascotas> milista = new ArrayList<>();
        for (Mascotas obj:list){
            if(obj.getNombre().toLowerCase().contains(s.toLowerCase())){
                milista.add(obj);
            }

        }
        AdapterMascota adapter = new AdapterMascota(milista);
        listView.setAdapter(adapter);
    }
}
