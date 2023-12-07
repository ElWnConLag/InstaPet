package insta.pet.instapet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class I_AgregarPublicacion extends AppCompatActivity {
    private DatabaseReference publicacionesRef;
    private StorageReference storageRef;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    private MqttHandler mqttHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.i_agregarpublicacion_act);

        publicacionesRef = FirebaseDatabase.getInstance().getReference().child("Publicaciones");
        storageRef = FirebaseStorage.getInstance().getReference().child("imagenes_publicaciones");

        ImageButton botonPublicacion = findViewById(R.id.volverPublicacion);
        Button guardarPublicacionButton = findViewById(R.id.button);
        Button seleccionarImagenButton = findViewById(R.id.imagen_bt_hilomascotaaaa);
        ImageView imagenMascota = findViewById(R.id.imagenmascota2);

        mqttHandler = new MqttHandler();

        botonPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(I_AgregarPublicacion.this, C_Home.class);
                startActivity(intent);
            }
        });

        guardarPublicacionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPublicacionEnFirebase();
                mqttHandler.connect("mqtt://prueba-mqtt-ust.cloud.shiftr.io:1883", "TN285V8kWXcveB0a");
                String titulo = ((EditText) findViewById(R.id.nombrehilomascotaAAAA)).getText().toString();
                mqttHandler.publish("Guardado Correctamente", titulo);

                // Mostrar un Toast indicando que se ha guardado correctamente
                Toast.makeText(getApplicationContext(), "Guardado Correctamente", Toast.LENGTH_SHORT).show();

            }
        });


        seleccionarImagenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();
            }
        });
    }

    private void seleccionarImagen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ImageView imageView = findViewById(R.id.imagenmascota2);
            imageView.setImageURI(imageUri);
        }
    }

    private void guardarPublicacionEnFirebase() {
        if (publicacionesRef != null && imageUri != null) {
            String publicacionId = publicacionesRef.push().getKey();
            String titulo = ((EditText) findViewById(R.id.nombrehilomascotaAAAA)).getText().toString();
            String descripcion = ((EditText) findViewById(R.id.nombrehilomascotaAAAAYYYY)).getText().toString();

            if (!titulo.isEmpty() && !descripcion.isEmpty()) {
                StorageReference fileReference = storageRef.child(publicacionId + ".jpg");
                fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        Map<String, Object> publicacionMap = new HashMap<>();
                        publicacionMap.put("id", publicacionId);
                        publicacionMap.put("titulo", titulo);
                        publicacionMap.put("descripcion", descripcion);
                        publicacionMap.put("imagenUrl", uri.toString());

                        publicacionesRef.child(publicacionId).setValue(publicacionMap);

                        Toast.makeText(I_AgregarPublicacion.this, "Publicación guardada exitosamente", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(I_AgregarPublicacion.this, C_Home.class);
                        startActivity(intent);
                        finish();
                    });
                });
            } else {
                Toast.makeText(I_AgregarPublicacion.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
