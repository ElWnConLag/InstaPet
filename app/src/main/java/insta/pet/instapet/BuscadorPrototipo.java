package insta.pet.instapet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import insta.pet.instapet.adapter.AdapterUsuario;
import insta.pet.instapet.pojo.Usuario;


public class BuscadorPrototipo extends AppCompatActivity {

    DatabaseReference ref;
    ArrayList<Usuario> list;
    RecyclerView rv;
    SearchView searchView;
    AdapterUsuario adapter;

    LinearLayoutManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador_prototipo);

        ref = FirebaseDatabase.getInstance().getReference().child("DatosUsuario");
        rv = findViewById(R.id.rv);
        searchView = findViewById(R.id.search);
        lm= new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        list = new ArrayList<>();
        adapter = new AdapterUsuario(list);
        rv.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Usuario ms = snapshot.getValue(Usuario.class);
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

    private void buscar(String s) {
        ArrayList<Usuario>milista = new ArrayList<>();
        for (Usuario obj: list) {
            if(obj.getNombre().toLowerCase().contains(s.toLowerCase()));
            milista.add(obj);
        }
        AdapterUsuario adapter = new AdapterUsuario(milista);
        rv.setAdapter(adapter);
    }
}

//31:00