package insta.pet.instapet;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

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
    ArrayList<Mascota> list;
    ListView listView;

    AdapterMascota adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador_mascotas);

        ref = FirebaseDatabase.getInstance().getReference().child("Mascotas");
        listView = findViewById(R.id.listView);

        list = new ArrayList<>();
        adapter = new AdapterMascota(this, list);
        listView.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Mascota ms = snapshot.getValue(Mascota.class);
                        list.add(ms);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
