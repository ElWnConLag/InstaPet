package insta.pet.instapet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import insta.pet.instapet.R;
import insta.pet.instapet.pojo.Publicaciones;

public class AdapterPublicaciones extends  RecyclerView.Adapter<AdapterPublicaciones.viewholderpublicaciones>{

    List<Publicaciones> publicacionesList;

    public AdapterPublicaciones(List<Publicaciones> publicacionesList) {
        this.publicacionesList = publicacionesList;
    }

    @NonNull
    @Override
    public viewholderpublicaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_publicaciones,parent,false);
        viewholderpublicaciones  holder = new viewholderpublicaciones(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderpublicaciones holder, int position) {
        Publicaciones pb  = publicacionesList.get(position);

        holder.tv_titulo.setText(pb.getTitulo());
        holder.tv_descripcion.setText(pb.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return publicacionesList.size();
    }

    public class viewholderpublicaciones extends RecyclerView.ViewHolder {

        TextView tv_titulo,tv_descripcion;
        public viewholderpublicaciones(@NonNull View itemView) {
            super(itemView);

            tv_titulo = itemView.findViewById(R.id.tv_titulo);
            tv_descripcion = itemView.findViewById(R.id.tv_descripcion);
        }
    }
}
