package insta.pet.instapet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import insta.pet.instapet.R;
import insta.pet.instapet.pojo.PetsHome;

public class AdapterPetHome extends RecyclerView.Adapter<AdapterPetHome.viewholderpetshome>{
    List<PetsHome> petsHomeList;

    public AdapterPetHome(List<PetsHome> petsHomeList) {
        this.petsHomeList = petsHomeList;
    }

    @NonNull
    @Override
    public viewholderpetshome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pets_home,parent,false);
        viewholderpetshome holder = new viewholderpetshome(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderpetshome holder, int position) {
        PetsHome ph = petsHomeList.get(position);
        Picasso.get().load(ph.getImagenUrl()).into(holder.iv_mascotashome);
    }

    @Override
    public int getItemCount() {
        return petsHomeList.size();
    }

    public class viewholderpetshome extends RecyclerView.ViewHolder {
        ImageView iv_mascotashome;
        public viewholderpetshome(@NonNull View itemView) {
            super(itemView);
            iv_mascotashome = itemView.findViewById(R.id.iv_mascotashome);
        }
    }
}
