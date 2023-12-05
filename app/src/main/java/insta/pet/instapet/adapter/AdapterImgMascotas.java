package insta.pet.instapet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import insta.pet.instapet.R;
import insta.pet.instapet.pojo.ImagenMascotas;

public class AdapterImgMascotas extends RecyclerView.Adapter<AdapterImgMascotas.viewholderimagenmascotas>{

    List<ImagenMascotas> imagenMascotasList;

    public AdapterImgMascotas(List<ImagenMascotas> imagenMascotasList) {
        this.imagenMascotasList = imagenMascotasList;
    }

    @NonNull
    @Override
    public viewholderimagenmascotas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_imagen_mascotas,parent,false);
        viewholderimagenmascotas holder = new viewholderimagenmascotas(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderimagenmascotas holder, int position) {
        ImagenMascotas im = imagenMascotasList.get(position);
        Picasso.get().load(im.getImagenUrl()).into(holder.iv_mascotas);

    }

    @Override
    public int getItemCount() {
        return imagenMascotasList.size();
    }

    public class viewholderimagenmascotas extends RecyclerView.ViewHolder {
        ImageView  iv_mascotas;
        public viewholderimagenmascotas(@NonNull View itemView) {
            super(itemView);
            iv_mascotas = itemView.findViewById(R.id.iv_mascotas);
        }
    }
}
