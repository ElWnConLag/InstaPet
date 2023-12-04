package insta.pet.instapet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class publi_mascotas extends AppCompatActivity {

    private EditText editTextTitulo;
    private EditText editTextDescripcion;
    private ImageView imagenMascota;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicacion_mascotas);

        editTextTitulo = findViewById(R.id.nombrehilomascotaAAAA);
        editTextDescripcion = findViewById(R.id.nombrehilomascotaAAAAYYYY);
        imagenMascota = findViewById(R.id.imagenmascota2);

        Button seleccionarImagenButton = findViewById(R.id.imagen_bt_hilomascotaaaa);
        seleccionarImagenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarImagen();
            }
        });

        Button guardarPublicacionButton = findViewById(R.id.button);
        guardarPublicacionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarPublicacionFirebase();
            }
        });
    }

    private void seleccionarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imagenUri = data.getData();

            imagenMascota.setImageURI(imagenUri);
        }
    }

    private void guardarPublicacionFirebase() {

        String titulo = editTextTitulo.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();


        if (titulo.isEmpty() || descripcion.isEmpty()) {

            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }


        DatabaseReference publicacionesRef = FirebaseDatabase.getInstance().getReference("Publicaciones");


        String nuevaPublicacionId = publicacionesRef.push().getKey();


        Publicacion nuevaPublicacion = new Publicacion(nuevaPublicacionId, titulo, descripcion);


        publicacionesRef.child(nuevaPublicacionId).setValue(nuevaPublicacion);


        Toast.makeText(this, "Publicación guardada exitosamente", Toast.LENGTH_SHORT).show();


        finish();
    }
}

