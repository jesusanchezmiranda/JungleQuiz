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


public class RVAdapterUsuarios extends RecyclerView.Adapter<RVAdapterUsuarios.ViewHolder>{

    private List<Usuario> listaUsuarios;
    private NavController navCAux;
    private Activity activity;
    private ViewModel viewModel;
    private TypedArray imgAvatares;


    public RVAdapterUsuarios(List<Usuario>listaUsuarios,NavController navCAux, Activity activity, TypedArray imgAvatares) {
        this.listaUsuarios=listaUsuarios;
        this.navCAux = navCAux;
        this.activity = activity;
        this.imgAvatares = imgAvatares;
    }

    @NonNull
    @Override
    public RVAdapterUsuarios.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);
        ViewHolder holder = new ViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterUsuarios.ViewHolder holder, int position) {
        holder.nombreUsuario.setText(listaUsuarios.get(position).getNombre());
        holder.avatarUsuario.setImageDrawable(imgAvatares.getDrawable(listaUsuarios.get(position).getAvatar()));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.isBorrar()){
                    dialogoAlerta(position);
                }else{
                    Usuario usuario = listaUsuarios.get(position);
                    viewModel.setUsuario(usuario);
                    navCAux.navigate(R.id.ac_listaUsuarios_to_usuarioElegido);
                }
            }
        });
    }

    public void dialogoAlerta(int position) {
        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(activity);
        alertDialog.setTitle("Cuidado");
        alertDialog.setMessage("Estás a punto de borrar a un usuario, ¿Estás seguro?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.deleteUsuario(listaUsuarios.get(position).getId());
                Toast.makeText(activity, "Usuario borrado con exito", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreUsuario;
        ImageView avatarUsuario;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.layoutUsuarios);
            nombreUsuario = itemView.findViewById(R.id.tvUsuario);
            avatarUsuario = itemView.findViewById(R.id.avatarUsuario);
        }
    }
}
