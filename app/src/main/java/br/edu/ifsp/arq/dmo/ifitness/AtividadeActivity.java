package br.edu.ifsp.arq.dmo.ifitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifsp.arq.dmo.ifitness.adapter.AtividadeAdapter;
import br.edu.ifsp.arq.dmo.ifitness.model.UsuarioComAtividade;
import br.edu.ifsp.arq.dmo.ifitness.viewmodel.UsuarioViewModel;

public class AtividadeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private RecyclerView rvAtividade;
    private AtividadeAdapter atividadeAdapter;
    private UsuarioViewModel usuarioViewModel;
    private Button btnNovaAtividade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.txtTituloToolbar);
        txtTitulo.setText(R.string.txtTituloAtividade);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        rvAtividade = findViewById(R.id.rvAtividade);
        atividadeAdapter = new AtividadeAdapter(this);

        usuarioViewModel.isLogged().observe(this, new Observer<UsuarioComAtividade>() {
            @Override
            public void onChanged(UsuarioComAtividade usuarioComAtividade) {
                atividadeAdapter.setAtividades(usuarioComAtividade.getAtividades());
                atividadeAdapter.notifyDataSetChanged();
            }
        });
        rvAtividade.setAdapter(atividadeAdapter);
        rvAtividade.setLayoutManager(new LinearLayoutManager(this));

        btnNovaAtividade = findViewById(R.id.btnNovaAtividade);
        btnNovaAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AtividadeActivity.this, CadastroAtividadeActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
