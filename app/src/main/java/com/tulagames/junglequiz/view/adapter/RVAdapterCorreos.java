package com.tulagames.junglequiz.view.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Usuario;
import com.tulagames.junglequiz.viewmodel.ViewModel;

import java.util.List;


public class RVAdapterCorreos extends RecyclerView.Adapter<RVAdapterCorreos.ViewHolder>{

    private List<String> emails;
    private NavController navCAux;
    private Activity activity;
    private ViewModel viewModel;



    public RVAdapterCorreos(List<String> emails, NavController navCAux, Activity activity) {
        this.emails=emails;
        this.navCAux = navCAux;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RVAdapterCorreos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_correo, parent, false);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);
        ViewHolder holder = new ViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterCorreos.ViewHolder holder, int position) {
        holder.correo.setText(emails.get(position));


    }


    @Override
    public int getItemCount() {
        return emails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView correo;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.layouCorreos);
            correo = itemView.findViewById(R.id.tvCorreo);

        }
    }
}
