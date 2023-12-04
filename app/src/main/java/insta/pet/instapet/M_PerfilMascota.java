package insta.pet.instapet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class M_PerfilMascota extends AppCompatActivity {

    private Button buttonSeguidores;
    private TextView numeroDeSeguidores;

    private int followersCount = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_perfildueno_act);

        buttonSeguidores = findViewById(R.id.btnMostrarSeguidores);
        numeroDeSeguidores = findViewById(R.id.tvSeguidores);

        // Configura el OnClickListener para el botón
        buttonSeguidores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recupera la información de los seguidores desde tu servicio backend
                // Puedes hacerlo utilizando Retrofit, Volley, etc.
                obtenerSeguidoresDesdeBackend("nombreDeUsuario");

                // Puedes llamar a esta función cada vez que quieras actualizar la lista de seguidores
            }
        });
    }

    private void obtenerSeguidoresDesdeBackend(String nombreDeUsuario) {
        // Implementa la lógica para obtener los seguidores desde tu servicio backend
        String listaSeguidores = obtenerListaSeguidoresDesdeBackend(nombreDeUsuario);
        actualizarVistaSeguidores(listaSeguidores);
    }

    // Esta función simula obtener la lista de seguidores desde el backend
    private String obtenerListaSeguidoresDesdeBackend(String nombreDeUsuario) {
        // Implementa la lógica real para obtener la lista de seguidores desde el backend
        return "Usuario1, Usuario2, Usuario3";
    }

    // Esta función actualiza la vista con la lista de seguidores
    private void actualizarVistaSeguidores(String listaSeguidores) {
        // Incrementa el contador de seguidores al hacer clic en el botón
        followersCount++;
        // Actualiza el texto del TextView con el nuevo número de seguidores
        numeroDeSeguidores.setText("Seguidores (" + followersCount + "): " + listaSeguidores);
    }
}