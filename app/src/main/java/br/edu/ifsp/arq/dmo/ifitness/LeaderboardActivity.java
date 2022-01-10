package br.edu.ifsp.arq.dmo.ifitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.arq.dmo.ifitness.adapter.LeaderboardAdapter;
import br.edu.ifsp.arq.dmo.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmo.ifitness.viewmodel.UsuarioViewModel;

public class LeaderboardActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private RecyclerView rvLeaderboard;
    private LeaderboardAdapter leaderboardAdapter;
    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.txtTituloToolbar);
        txtTitulo.setText(R.string.txtTituloLeaderboard);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        rvLeaderboard = findViewById(R.id.rvLeaderboard);
        leaderboardAdapter = new LeaderboardAdapter(this);

        usuarioViewModel.allUsuarios().observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                leaderboardAdapter.setUsuarios(usuarios);
                leaderboardAdapter.notifyDataSetChanged();
            }
        });
        rvLeaderboard.setAdapter(leaderboardAdapter);
        rvLeaderboard.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}