package insta.pet.instapet;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class editar_perfil_j extends AppCompatActivity {

    private EditText editTextNombreUsuario;
    private EditText correo_id;
    private EditText editTextNacionalidad;
    private DatabaseReference myRef;
    private ImageView imageViewProfile;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageReference storageReference;
    private MqttHandler mqttHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_perfil_j_act);

        editTextNombreUsuario = findViewById(R.id.editTextNombreUsuario);
        correo_id = findViewById(R.id.correo_id);
        editTextNacionalidad = findViewById(R.id.editTextNacionalidad);
        imageViewProfile = findViewById(R.id.imageViewProfile);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mqttHandler = new MqttHandler();

        if (currentUser != null) {
            String uid = currentUser.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference("DatosUsuario");

            FirebaseStorage storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference("profile_images");

            // Botón "Guardar"
            Button guardarButton = findViewById(R.id.button2);
            guardarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nuevoNombreUsuario = editTextNombreUsuario.getText().toString();
                    String nuevoCorreo = correo_id.getText().toString();
                    String nuevaNacionalidad = editTextNacionalidad.getText().toString();

                    Map<String, Object> userData = new HashMap<>();
                    userData.put("nombre", nuevoNombreUsuario);
                    userData.put("correo", nuevoCorreo);
                    userData.put("nacionalidad", nuevaNacionalidad);

                    myRef.child(uid).updateChildren(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                if (imageUri != null) {
                                    uploadProfileImage(uid);
                                } else {
                                    showToastAndNavigate("Cambios guardados correctamente");
                                }
                            } else {
                                showToast("Error al guardar los cambios");
                            }
                        }
                    });

                    // Conectar con el servidor MQTT
                    mqttHandler.connect("mqtt://prueba-mqtt-ust.cloud.shiftr.io:1883", "TN285V8kWXcveB0a");

                    // Publicar solo el nombre del perro a un tema específico en MQTT
                    mqttHandler.publish("Matias", nuevoNombreUsuario);
                }
            });

            Button selectImageBtn = findViewById(R.id.selectImageBtn);
            selectImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openImageChooser();
                }
            });
        } else {
            // Manejo del caso en el que el usuario no está autenticado.
        }
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
                imageViewProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadProfileImage(String uid) {
        if (imageUri != null) {
            StorageReference imageRef = storageReference.child(uid + "_profile_image.jpg");

            imageViewProfile.setDrawingCacheEnabled(true);
            imageViewProfile.buildDrawingCache();
            Bitmap bitmap = imageViewProfile.getDrawingCache();
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

                                myRef.child(uid).child("imagenPerfil").setValue(imageUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Picasso.get().load(imageUrl).into(imageViewProfile);
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
        Toast.makeText(editar_perfil_j.this, message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(editar_perfil_j.this, Perfil_activity.class);
        startActivity(intent);
        finish(); // Cierra la actividad actual
    }

    private void showToast(String message) {
        Toast.makeText(editar_perfil_j.this, message, Toast.LENGTH_SHORT).show();
    }
}







