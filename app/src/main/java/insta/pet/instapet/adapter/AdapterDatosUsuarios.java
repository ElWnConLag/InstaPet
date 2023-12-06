package insta.pet.instapet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import insta.pet.instapet.R;
import insta.pet.instapet.pojo.DatosUsuarios;

public class AdapterDatosUsuarios extends RecyclerView.Adapter<AdapterDatosUsuarios.viewholderdatosusuarios> {
    List<DatosUsuarios> datosUsuariosList;

    public AdapterDatosUsuarios(List<DatosUsuarios> datosUsuariosList) {
        this.datosUsuariosList = datosUsuariosList;
    }

    @NonNull
    @Override
    public viewholderdatosusuarios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_datos_usuarios,parent,false);
        viewholderdatosusuarios  holder = new viewholderdatosusuarios(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderdatosusuarios holder, int position) {
        DatosUsuarios du = datosUsuariosList.get(position);
        holder.tv_datosNombres.setText(du.getNombre());
    }

    @Override
    public int getItemCount() {
        return datosUsuariosList.size() ;
    }

    public class viewholderdatosusuarios extends RecyclerView.ViewHolder {
        TextView tv_datosNombres;

        public viewholderdatosusuarios(@NonNull View itemView) {
            super(itemView);
            tv_datosNombres = itemView.findViewById(R.id.tv_datosNombres);
        }
    }
}
