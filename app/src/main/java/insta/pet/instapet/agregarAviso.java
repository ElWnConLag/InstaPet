package insta.pet.instapet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import insta.pet.instapet.MqttHandler;

public class agregarAviso extends AppCompatActivity {

    private EditText editTextNombrePerro;
    private EditText raza_id;
    private EditText sexoPerro;
    private EditText ubicacionPerro;

    private ImageView imagenPerfilPerro;

    private DatabaseReference myRef;

    private StorageReference storageReference;

    private EditText descripcionPerro;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri imageUri;

    private MqttHandler mqttHandler;
    private ImageButton bt_volverAvisoN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_aviso);

        bt_volverAvisoN = findViewById(R.id.bt_volverAvisoN);

        editTextNombrePerro = findViewById(R.id.editTextNombrePerro);
        raza_id= findViewById(R.id.raza_id);
        sexoPerro = findViewById(R.id.sexoPerro);
        ubicacionPerro = findViewById(R.id.ubicacionPerro);
        imagenPerfilPerro = findViewById(R.id.imagenPerfilPerro);
        descripcionPerro = findViewById(R.id.descripcionPerro);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("DatosPerro");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("imagenAvisos");

        mqttHandler = new MqttHandler();

        bt_volverAvisoN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(agregarAviso.this, avisosAdopcion.class);
                startActivity(intent);
            }
        });

        Button guardarButton = findViewById(R.id.botonGuardar);
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nuevoNombrePerro = editTextNombrePerro.getText().toString();
                String nuevaRaza = raza_id.getText().toString();
                String nuevoSexoPerro = sexoPerro.getText().toString();
                String nuevaUbicacion = ubicacionPerro.getText().toString();
                String nuevaDescripcion = descripcionPerro.getText().toString();


                if (nuevoNombrePerro.isEmpty() || nuevaRaza.isEmpty() || nuevoSexoPerro.isEmpty() || nuevaUbicacion.isEmpty() || nuevaDescripcion.isEmpty()) {
                    showToast("Debes rellenar todos los campos");
                } else {
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("nombrePerro", nuevoNombrePerro);
                    userData.put("raza", nuevaRaza);
                    userData.put("sexo", nuevoSexoPerro);
                    userData.put("ubicacion", nuevaUbicacion);
                    userData.put("descripcion", nuevaDescripcion);

                    myRef.updateChildren(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                if (imageUri != null) {
                                    uploadProfileImage();
                                } else {
                                    showToastAndNavigate("Aviso creado correctamente");
                                }
                            } else {
                                showToast("Error al guardar los cambios");
                            }
                        }
                    });

                    // Conectar con el servidor MQTT
                    mqttHandler.connect("mqtt://prueba-mqtt-ust.cloud.shiftr.io:1883", "TN285V8kWXcveB0a");

                    // Publicar solo el nombre del perro a un tema específico en MQTT
                    mqttHandler.publish("Cesar", nuevoNombrePerro);
                }
            }
        });

        Button seleccionarImage = findViewById(R.id.seleccionarImage);
        seleccionarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar una imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imagenPerfilPerro.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadProfileImage() {
        if (imageUri != null) {
            StorageReference imageRef = storageReference.child("perros.jpg");

            imagenPerfilPerro.setDrawingCacheEnabled(true);
            imagenPerfilPerro.buildDrawingCache();
            Bitmap bitmap = imagenPerfilPerro.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = imageRef.putBytes(data);
            uploadTask.addOnCompleteListener(this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();

                                myRef.child("imagenPerfil").setValue(imageUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Picasso.get().load(imageUrl).into(imagenPerfilPerro);
                                            showToastAndNavigate("Cambios guardados correctamente");
                                        } else {
                                            showToast("Error al guardar la imagen");
                                        }
                                    }
                                });

                            }
                        });
                    } else {
                        showToast("Error al subir la imagen");
                    }
                }
            });
        }
    }

    private void showToastAndNavigate(String message) {
        Toast.makeText(agregarAviso.this, message, Toast.LENGTH_SHORT).show();
    }

    private void showToast(String message) {
        Toast.makeText(agregarAviso.this, message, Toast.LENGTH_SHORT).show();
    }
}
