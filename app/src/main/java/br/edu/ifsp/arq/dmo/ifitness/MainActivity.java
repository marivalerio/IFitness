package br.edu.ifsp.arq.dmo.ifitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;

import br.edu.ifsp.arq.dmo.ifitness.model.UsuarioComAtividade;
import br.edu.ifsp.arq.dmo.ifitness.viewmodel.UsuarioViewModel;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView txtTitulo;
    private TextView txtLogin;

    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        txtTitulo = findViewById(R.id.txtTituloToolbar);
        txtTitulo.setText(getString(R.string.app_name));

        drawerLayout = findViewById(R.id.nav_drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.toggle_open, R.string.toggle_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_account:
                        intent = new Intent(MainActivity.this, PerfilUsuarioActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_atividade:
                        intent = new Intent(MainActivity.this, AtividadeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_estatistica:
                        intent = new Intent(MainActivity.this, EstatisticaActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_leaderboard:
                        intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        usuarioViewModel.logout();
                        finish();
                        startActivity(getIntent());
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        txtLogin = navigationView.getHeaderView(0).findViewById(R.id.txtHeaderProfileName);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        usuarioViewModel.isLogged().observe(this, new Observer<UsuarioComAtividade>() {
            @Override
            public void onChanged(UsuarioComAtividade usuarioComAtividade) {
                if (usuarioComAtividade != null) {
                    txtLogin.setText(usuarioComAtividade.getUsuario().getNome());
                }
            }
        });
    }
}