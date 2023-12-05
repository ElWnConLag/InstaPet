package insta.pet.instapet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

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
    private String uid; // Asegúrate de obtener el UID adecuadamente

    private MqttHandler mqttHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parte2j);

        // Asegúrate de obtener el UID del usuario actual de alguna manera (puede ser similar a cómo lo obtuviste en otras partes del código)
        uid = obtenerUidUsuario(); // Reemplaza esto con tu lógica para obtener el UID

        // Inicializa la referencia a Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Mascotas");

        mqttHandler = new MqttHandler();

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
                String nombre = nombreMascota.getText().toString();
                String raza = razaMascota.getText().toString();
                String sexo = sexoMascota.getText().toString();
                String tamaño = tamañoMascota.getText().toString();

                if (nombre.isEmpty() || raza.isEmpty() || sexo.isEmpty() || tamaño.isEmpty()) {
                    // Asegurémonos de que todos los campos estén llenos
                    Toast.makeText(parte2j.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                mqttHandler.connect("mqtt://prueba-mqtt-ust.cloud.shiftr.io:1883", "TN285V8kWXcveB0a");
                mqttHandler.publish("Jerson", raza);

                Mascota nuevaMascota = new Mascota(nombre, raza, sexo, tamaño);

                if (imageUri != null) {
                    StorageReference imageRef = storageRef.child(uid).child("nombre_de_la_imagen.png");

                    UploadTask uploadTask = imageRef.putFile(imageUri);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    nuevaMascota.setImagenUrl(uri.toString());

                                    myRef.push().setValue(nuevaMascota).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(parte2j.this, "Mascota guardada con éxito", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(parte2j.this, Perfil_activity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(parte2j.this, "Error al guardar la mascota: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(parte2j.this, "Error al subir la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private String obtenerUidUsuario() {
        // Agrega aquí la lógica para obtener el UID del usuario actual
        // Puedes obtenerlo de FirebaseAuth, por ejemplo
        return "uid_del_usuario_actual";
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
