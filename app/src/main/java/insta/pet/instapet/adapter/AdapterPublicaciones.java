package insta.pet.instapet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import insta.pet.instapet.Mascotas;
import insta.pet.instapet.R;
import insta.pet.instapet.pojo.Publicaciones;

public class AdapterPublicaciones extends BaseAdapter {

    private List<Publicaciones> publicacionesList;
    private LayoutInflater inflater;

    public AdapterPublicaciones(Context context, List<Mascotas> mascotasList) {
        this.publicacionesList = publicacionesList;
        this.inflater = LayoutInflater.from(context);
    }

    public AdapterPublicaciones(ArrayList<Publicaciones> milista) {
    }

    @Override
    public int getCount() {
        return publicacionesList.size();
    }

    @Override
    public Object getItem(int position) {
        return publicacionesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterPublicaciones.ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_publicaciones, parent, false);

            holder = new ViewHolder();

            holder.tv_titulo = convertView.findViewById(R.id.tv_titulo);

            convertView.setTag(holder);
        } else {
            holder = (AdapterPublicaciones.ViewHolder) convertView.getTag();
        }

        Publicaciones ms = publicacionesList.get(position);

        holder.tv_titulo.setText(ms.getTitulo());
        holder.tv_descripcion.setText(ms.getDescripcion());

        return convertView;
    }
    static class ViewHolder {
        TextView tv_titulo;
        TextView tv_descripcion;
    }
}
