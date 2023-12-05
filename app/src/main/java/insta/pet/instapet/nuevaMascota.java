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

public class nuevaMascota extends AppCompatActivity {

    private EditText editTextNombreUsuario;
    private EditText correo_id;
    private EditText editTextNacionalidad;
    private DatabaseReference myRef;
    private ImageView imageViewProfile;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_perfil_j_act);

        View volverNuevaMascota = findViewById(R.id.volverNuevaMascota);

        volverNuevaMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nuevaMascota.this, Perfil_activity.class);
                startActivity(intent);
            }
        });

        // Inicializa los EditText donde el usuario ingresa los datos a guardar
        editTextNombreUsuario = findViewById(R.id.editTextNombreUsuario);
        correo_id = findViewById(R.id.correo_id);
        editTextNacionalidad = findViewById(R.id.editTextNacionalidad);
        imageViewProfile = findViewById(R.id.imageViewProfile);

        // Inicializa la referencia a Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("DatosUsuario");

        // Inicializa la referencia a Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("profile_images");

        // Botón "Guardar"
        Button guardarButton = findViewById(R.id.button2);
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtiene los textos ingresados en los EditText
                String nuevoNombreUsuario = editTextNombreUsuario.getText().toString();
                String nuevoCorreo = correo_id.getText().toString();
                String nuevaNacionalidad = editTextNacionalidad.getText().toString();

                // Crea un mapa para almacenar los datos
                Map<String, Object> userData = new HashMap<>();
                userData.put("nombre", nuevoNombreUsuario);
                userData.put("correo", nuevoCorreo);
                userData.put("nacionalidad", nuevaNacionalidad);

                // Actualiza los valores en la base de datos
                myRef.updateChildren(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Sube la imagen de perfil si está seleccionada
                            if (imageUri != null) {
                                uploadProfileImage();
                            } else {
                                // Muestra un mensaje o realiza otras acciones si es necesario
                                showToastAndNavigate("Cambios guardados correctamente");
                            }
                        } else {
                            // Error al actualizar los datos en la base de datos
                            showToast("Error al guardar los cambios");
                        }
                    }
                });
            }
        });

        // Botón "Seleccionar Imagen de Perfil"
        Button selectImageBtn = findViewById(R.id.selectImageBtn);
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });
    }

    // Método para abrir el selector de imágenes
    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar una imagen"), PICK_IMAGE_REQUEST);
    }

    // Método para manejar el resultado de la selección de imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            try {
                // Convierte la imagen seleccionada a un objeto Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                // Muestra la imagen en el ImageView
                imageViewProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para cargar la imagen de perfil en Firebase Storage
    private void uploadProfileImage() {
        if (imageUri != null) {
            StorageReference imageRef = storageReference.child("profile_image.jpg");

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
                        // La imagen se cargó exitosamente, obtén la URL de descarga
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();

                                // Actualiza la URL de la imagen en la base de datos
                                // Actualiza la URL de la imagen en la base de datos
                                myRef.child("imagenPerfil").setValue(imageUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Carga y muestra la imagen de perfil desde la URL de Firebase Storage
                                            Picasso.get().load(imageUrl).into(imageViewProfile);

                                            // Muestra un mensaje o realiza otras acciones si es necesario
                                            showToastAndNavigate("Cambios guardados correctamente");
                                        } else {
                                            // Error al actualizar la URL de la imagen en la base de datos
                                            showToast("Error al guardar la imagen");
                                        }
                                    }
                                });

                            }
                        });
                    } else {
                        // Error al cargar la imagen
                        showToast("Error al subir la imagen");
                    }
                }
            });
        }
    }

    // Método para mostrar un mensaje Toast y navegar a la actividad anterior
    private void showToastAndNavigate(String message) {
        Toast.makeText(nuevaMascota.this, message, Toast.LENGTH_SHORT).show();

        // Regresa a la actividad anterior (activity_perfil_main.xml)
        Intent intent = new Intent(nuevaMascota.this, Perfil_activity.class);
        startActivity(intent);
        finish(); // Cierra la actividad actual
    }

    // Método para mostrar un mensaje Toast
    private void showToast(String message) {
        Toast.makeText(nuevaMascota.this, message, Toast.LENGTH_SHORT).show();
    }
}





