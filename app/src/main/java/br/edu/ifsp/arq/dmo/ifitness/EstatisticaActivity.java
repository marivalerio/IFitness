package br.edu.ifsp.arq.dmo.ifitness;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.stream.IntStream;

import br.edu.ifsp.arq.dmo.ifitness.adapter.EmblemaAdapter;
import br.edu.ifsp.arq.dmo.ifitness.model.Atividade;
import br.edu.ifsp.arq.dmo.ifitness.model.UsuarioComAtividade;
import br.edu.ifsp.arq.dmo.ifitness.viewmodel.UsuarioViewModel;

public class EstatisticaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private TextView txtQtdAtividades;
    private TextView txtKmTotal;
    private TextView txtDuracaoTotal;
    private TextView txtCalorias;
    private UsuarioViewModel usuarioViewModel;
    private RecyclerView rvEmblema;
    private EmblemaAdapter emblemaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.txtTituloToolbar);
        txtTitulo.setText(R.string.txtTituloEstatistica);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        rvEmblema = findViewById(R.id.rvEmblema);
        emblemaAdapter = new EmblemaAdapter(this);

        txtQtdAtividades = findViewById(R.id.txtQtdAtividades);
        txtKmTotal = findViewById(R.id.txtKmTotal);
        txtDuracaoTotal = findViewById(R.id.txtDuracaoTotal);
        txtCalorias = findViewById(R.id.txtCalorias);

        usuarioViewModel.isLogged().observe(this, new Observer<UsuarioComAtividade>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(UsuarioComAtividade usuarioComAtividade) {
                txtQtdAtividades.setText(usuarioComAtividade.getAtividades().size() + " atividade(s)");
                txtKmTotal.setText(usuarioComAtividade.getAtividades().stream().mapToDouble(Atividade::getKm).sum() + " KM");
                txtDuracaoTotal.setText(usuarioComAtividade.getAtividades().stream().flatMapToInt(atividade -> IntStream.of(atividade.getDuracao())).sum() + " minutos");
                txtCalorias.setText(usuarioComAtividade.getAtividades().stream().flatMapToInt(atividade -> IntStream.of(atividade.getCalorias())).sum() + " calorias");
                emblemaAdapter.setEmblemas(usuarioComAtividade.getUsuario().getEmblemas());
                emblemaAdapter.notifyDataSetChanged();
            }
        });
        rvEmblema.setAdapter(emblemaAdapter);
        rvEmblema.setLayoutManager(new GridLayoutManager(this, 5));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}