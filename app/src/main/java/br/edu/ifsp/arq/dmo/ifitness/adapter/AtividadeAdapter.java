package br.edu.ifsp.arq.dmo.ifitness.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.dmo.ifitness.R;
import br.edu.ifsp.arq.dmo.ifitness.model.Atividade;

public class AtividadeAdapter extends RecyclerView.Adapter<AtividadeAdapter.ViewHolder> {

    private Context ctx;
    private List<Atividade> atividades;

    public AtividadeAdapter(Context ctx) {
        this.ctx = ctx;
        this.atividades = new ArrayList<>();
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    @NonNull
    @Override
    public AtividadeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_atividade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AtividadeAdapter.ViewHolder holder, int position) {

        Resources res = ctx.getResources();
        TypedArray tipos = res.obtainTypedArray(R.array.tipoAtividades);

        Atividade atividade = atividades.get(position);
        holder.imgTipoAtividade.setImageDrawable(tipos.getDrawable(atividade.getTipoAtividade()));
        holder.imgTipoAtividade.setColorFilter(res.getColor(R.color.black));
        holder.txtKM.setText(atividade.getKm().toString() + " km");
        holder.txtDuracao.setText(atividade.getDuracao() + " minutos");
        holder.txtCaloria.setText(atividade.getCalorias() + " calorias");


//        holder.imgTipoAtividade.setImageResource(atividade.get);
//        holder.icon.setImageResource(R.drawable.camiseta_mockup);
//        holder.titulo.setText(produto.getTitulo());
//        holder.listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Falta criar a EditarAtividadeActivity
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return atividades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgTipoAtividade;
        private TextView txtDuracao;
        private TextView txtKM;
        private TextView txtCaloria;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTipoAtividade = itemView.findViewById(R.id.imgTipoAtividade);
            txtDuracao = itemView.findViewById(R.id.valorDuracao);
            txtKM = itemView.findViewById(R.id.valorKm);
            txtCaloria = itemView.findViewById(R.id.valorCaloria);
        }
    }
}

