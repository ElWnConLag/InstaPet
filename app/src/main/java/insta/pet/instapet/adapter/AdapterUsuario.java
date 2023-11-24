package insta.pet.instapet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import insta.pet.instapet.R;
import insta.pet.instapet.pojo.Usuario;

public class AdapterUsuario extends RecyclerView.Adapter<AdapterUsuario.viewholderusuarios> {
    List<Usuario> usuarioList;

    public AdapterUsuario(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @NonNull
    @Override
    public viewholderusuarios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_usuarios,parent,false);
        viewholderusuarios holder = new viewholderusuarios(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderusuarios holder, int position) {
        Usuario user = usuarioList.get(position);

        holder.tv_nombre.setText(user.getNombre());
    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    public class viewholderusuarios extends RecyclerView.ViewHolder {
        TextView tv_nombre;
        public viewholderusuarios(@NonNull View itemView) {
            super(itemView);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
        }
    }
}

//19:41
