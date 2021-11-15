package br.edu.ifsp.arq.dmo.ifitness.model;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ifsp.arq.dmo.ifitness.R;

import java.util.List;

public class AtividadeAdapter extends ArrayAdapter {

    private Context ctx;
    private List<Atividade> atividades;

    public AtividadeAdapter(Context ctx, List<Atividade> atividades) {
        super(ctx, R.layout.adapter_atividade, atividades);
        this.ctx = ctx;
        this.atividades = atividades;
    }
    @Override
    public int getCount() {return atividades.size();
    }
    @Override
    public Object getItem(int position) {
        return atividades.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Atividade atividade = atividades.get(position);

        ViewHolder holder = null;
        if(convertView == null){

            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_atividade, null);
            holder = new ViewHolder();
            holder.imgTipoAtividade = (ImageView) convertView.findViewById(R.id.imgTipoAtividade);
            holder.txtKM = (TextView) convertView.findViewById(R.id.valorKm);
            holder.txtDuracao = (TextView) convertView.findViewById(R.id.valorDuracao);
            holder.txtCaloria = (TextView) convertView.findViewById(R.id.valorCaloria);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Resources res = ctx.getResources();
        TypedArray tipos = res.obtainTypedArray(R.array.tipoAtividades);

        holder.imgTipoAtividade.setImageDrawable(tipos.getDrawable(atividade.getTipoAtividade()));
        holder.imgTipoAtividade.setColorFilter(getContext().getResources().getColor(R.color.black));
        holder.txtKM.setText(atividade.getKm().toString());
        holder.txtDuracao.setText(Integer.toString(atividade.getDuracao()));
        holder.txtCaloria.setText(Integer.toString(atividade.getCalorias()));

        return convertView;
    }

    static class ViewHolder{
        ImageView imgTipoAtividade;
        TextView txtDuracao;
        TextView txtKM;
        TextView txtCaloria;
    }

}

