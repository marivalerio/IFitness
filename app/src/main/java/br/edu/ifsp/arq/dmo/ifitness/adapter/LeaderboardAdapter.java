package br.edu.ifsp.arq.dmo.ifitness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.dmo.ifitness.R;
import br.edu.ifsp.arq.dmo.ifitness.model.Usuario;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private Context context;
    private List<Usuario> usuarios;

    public LeaderboardAdapter(Context context) {
        this.context = context;
        this.usuarios = new ArrayList<>();
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_leaderboard, parent, false);
        return new LeaderboardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.ViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);

        String s = Integer.toString(position + 1);
        holder.txtPosicao.setText(s);
        holder.txtNome.setText(usuario.getNome());
        holder.txtPontuacao.setText(usuario.getPontuacao().toString());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPosicao;
        private TextView txtNome;
        private TextView txtPontuacao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPosicao = itemView.findViewById(R.id.txtPosicao);
            txtNome = itemView.findViewById(R.id.txtNomeAdapter);
            txtPontuacao = itemView.findViewById(R.id.txtPontuacao);
        }
    }
}
