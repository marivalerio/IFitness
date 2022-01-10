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
import br.edu.ifsp.arq.dmo.ifitness.model.Trofeu;

public class EmblemaAdapter extends RecyclerView.Adapter<EmblemaAdapter.ViewHolder> {

    private Context context;
    private List<Trofeu> emblemas;

    public EmblemaAdapter(Context context) {
        this.context = context;
        this.emblemas = new ArrayList<>();
    }

    public void setEmblemas(List<Trofeu> emblemas) {
        this.emblemas = emblemas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_emblema, parent, false);
        return new EmblemaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trofeu trofeu = emblemas.get(position);

        holder.txtTituloEmblema.setText(trofeu.name());
    }

    @Override
    public int getItemCount() {
        return emblemas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTituloEmblema;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTituloEmblema = itemView.findViewById(R.id.txtTituloEmblema);
        }
    }
}
