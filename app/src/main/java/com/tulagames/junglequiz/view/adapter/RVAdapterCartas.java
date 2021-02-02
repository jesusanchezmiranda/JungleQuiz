package com.tulagames.junglequiz.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Carta;
import com.tulagames.junglequiz.model.room.pojo.Respuesta;
import com.tulagames.junglequiz.viewmodel.ViewModel;

import java.util.List;

public class RVAdapterCartas extends RecyclerView.Adapter<RVAdapterCartas.ViewHolder> {

    private List<Carta> listaCartas;
    private NavController navCAux;
    private Activity activity;
    private ViewModel viewModel;
    private Context context;

    public RVAdapterCartas(List<Carta> listaCartas, NavController navCAux, Activity activity, Context context) {
        this.listaCartas = listaCartas;
        this.navCAux = navCAux;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public RVAdapterCartas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carta, parent, false);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);
        ViewHolder holder = new ViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterCartas.ViewHolder holder, int position) {
        holder.nombreCarta.setText(listaCartas.get(position).getNombreAnimal());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carta carta = listaCartas.get(position);
                viewModel.setCarta(carta);
                navCAux.navigate(R.id.ac_listaCartas_to_cartaElegida);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCartas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreCarta;
        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.layouCartas);
            nombreCarta = itemView.findViewById(R.id.tvNombreCarta);
        }
    }
}
