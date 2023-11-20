package insta.pet.instapet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class L_PerfilDueno extends AppCompatActivity {

    private TextView nombreperfil1;
    private ImageView imagenperfil1;
    ImageButton volverPerfilDueno;
    private Button botonSeguirPerfil;
    private DatabaseReference followReference;
    private DatabaseReference userReference;
    private FirebaseUser currentUser;

    public class User {
        private int followers;
        private int following;


        public User(int followers, int following) {
            this.followers = followers;
            this.following = following;
        }

        public int getFollowers() {
            return followers;
        }

        public void setFollowers(int followers) {
            this.followers = followers;
            setFollowers(10);
        }

        public int getFollowing() {
            return following;
        }

        public void setFollowing(int following) {
            this.following = following;
            setFollowing(5);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_perfildueno_act);

        nombreperfil1 = findViewById(R.id.textViewUsername);
        imagenperfil1 = findViewById(R.id.imageView3);
        botonSeguirPerfil = findViewById(R.id.botonSeguirPerfil);
        volverPerfilDueno = findViewById(R.id.volverPerfilDueno);

        volverPerfilDueno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(L_PerfilDueno.this, J_BuscarPerfil.class);
                startActivity(intent);
            }
        });

        followReference = FirebaseDatabase.getInstance().getReference().child("follows");
        userReference = FirebaseDatabase.getInstance().getReference().child("users");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        botonSeguirPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFollowing = botonSeguirPerfil.getText().toString().equals("Seguir");

                if (isFollowing) {
                    followUser();
                } else {
                    unfollowUser();
                }
            }
        });

        checkFollowingStatus();
        displayFollowCounts();
        loadUserData();
    }

    private void followUser() {
        followReference.child(currentUser.getUid()).child("following").setValue(true);
        updateFollowCounts(1);
        Toast.makeText(L_PerfilDueno.this, "Dejando de seguir al usuario", Toast.LENGTH_SHORT).show();
    }

    private void unfollowUser() {
        followReference.child(currentUser.getUid()).child("following").setValue(false);
        updateFollowCounts(-1);
        Toast.makeText(L_PerfilDueno.this, "Siguiendo al usuario", Toast.LENGTH_SHORT).show();
    }

    private void updateFollowCounts(final int increment) {
        userReference.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    user.setFollowers(user.getFollowers() + increment);
                    user.setFollowing(user.getFollowing() + increment);

                    userReference.child(currentUser.getUid()).setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores si es necesario
            }
        });
    }

    private void checkFollowingStatus() {
        followReference.child(currentUser.getUid()).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean following = dataSnapshot.getValue(Boolean.class);

                if (following != null) {
                    botonSeguirPerfil.setText(following ? "Dejar de seguir" : "Seguir");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores si es necesario
            }
        });
    }

    private void displayFollowCounts() {
        userReference.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores si es necesario
            }
        });
    }

    private void loadUserData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DatosUsuario");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombreUsuario = dataSnapshot.child("nombre").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imagenPerfil").getValue(String.class);

                    Picasso.get().load(imageUrl).into(imagenperfil1);
                    nombreperfil1.setText(nombreUsuario);

                    List<String> usuarios = new ArrayList<>();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de lectura de la base de datos si es necesario
            }
        });
    }
}
