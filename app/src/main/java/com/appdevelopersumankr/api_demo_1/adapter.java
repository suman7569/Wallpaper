package com.appdevelopersumankr.api_demo_1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {

    private Context context;
    private List<wallpaper_model> wallpaper_models;

    public adapter(Context context, List<wallpaper_model> wallpaper_models) {
        this.context = context;
        this.wallpaper_models = wallpaper_models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from ( context).inflate ( R.layout.blueprint,parent,false );
        ViewHolder viewHolder=new ViewHolder ( v );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with ( context ).load ( wallpaper_models.get ( position ).getMediumurl ()).into ( holder.imageView);
        holder.imageView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                context.startActivity ( new Intent (context,Fullscreenwallpaper.class)
                .putExtra ( "originalurl",wallpaper_models.get ( position ).getOriginalurl () ));
            }
        } );

    }

    @Override
    public int getItemCount() {
        return wallpaper_models.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super ( itemView );

            imageView=itemView.findViewById ( R.id.imageviewid);
        }
    }
}
