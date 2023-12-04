package insta.pet.instapet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import android.widget.ImageView;

public class parte2j extends AppCompatActivity {

    private DatabaseReference myRef;
    private EditText nombreMascota;
    private EditText razaMascota;
    private EditText sexoMascota;
    private EditText tamañoMascota;
    private ImageView imageViewProfile;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parte2j);

        // Inicializa la referencia a Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Mascotas");

        // Inicializa la referencia a Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        // Inicializa los EditText
        nombreMascota = findViewById(R.id.nombrehilomascota);
        razaMascota = findViewById(R.id.razahilomascota);
        sexoMascota = findViewById(R.id.sexohilomascota);
        tamañoMascota = findViewById(R.id.tamañohilomascota);

        // Inicializa el ImageView para mostrar la imagen seleccionada
        imageViewProfile = findViewById(R.id.imageViewProfile222);

        // Botón "Seleccionar Imagen"
        Button seleccionarImagenButton = findViewById(R.id.imagen_bt_hilomascota);
        seleccionarImagenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea un Intent para abrir la galería de imágenes
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
            }
        });

        // Botón "Guardar"
        Button guardarButton = findViewById(R.id.buttonhilomascota);
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtiene los datos de la mascota
                String nombre = nombreMascota.getText().toString();
                String raza = razaMascota.getText().toString();
                String sexo = sexoMascota.getText().toString();
                String tamaño = tamañoMascota.getText().toString();

                // Crea una instancia de la clase Mascota con los datos
                Mascotas nuevaMascotas = new Mascotas(nombre, raza, sexo, tamaño);

                // Sube la imagen a Firebase Storage y guarda la URL en la mascota
                if (imageUri != null) {
                    StorageReference imageRef = storageRef.child("nombre_de_la_imagen.png"); // Cambia el nombre de la imagen
                    UploadTask uploadTask = imageRef.putFile(imageUri);

                    // Maneja la subida de la imagen a Firebase Storage
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Asigna la URL de la imagen a nuevaMascota
                                    nuevaMascotas.setImagenUrl(uri.toString());

                                    // Guarda la mascota en Firebase Realtime Database, incluyendo la URL de la imagen
                                    DatabaseReference newMascotaRef = myRef.push();
                                    newMascotaRef.setValue(nuevaMascotas);

                                    // Regresa a la actividad anterior o realiza otras acciones si es necesario
                                    Intent intent = new Intent(parte2j.this, Perfil_activity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }
        });
    }

    // Manejo del resultado de la selección de imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Muestra la imagen seleccionada en el ImageView
            Picasso.get().load(imageUri).into(imageViewProfile);
        }
    }
}
