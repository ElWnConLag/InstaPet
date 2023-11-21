package insta.pet.instapet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import insta.pet.instapet.Mascota;
import insta.pet.instapet.R;

public class AdapterMascota extends BaseAdapter {

    private List<Mascota> mascotaList;
    private LayoutInflater inflater;

    public AdapterMascota(Context context, List<Mascota> mascotaList) {
        this.mascotaList = mascotaList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mascotaList.size();
    }

    @Override
    public Object getItem(int position) {
        return mascotaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_mascotas, parent, false);

            holder = new ViewHolder();

            holder.tv_nombre = convertView.findViewById(R.id.tv_nombre);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Mascota ms = mascotaList.get(position);


        holder.tv_nombre.setText(ms.getNombre());


        return convertView;
    }

    static class ViewHolder {
        TextView tv_nombre;
    }
}
