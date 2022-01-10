package br.edu.ifsp.arq.dmo.ifitness;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsp.arq.dmo.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmo.ifitness.model.UsuarioComAtividade;
import br.edu.ifsp.arq.dmo.ifitness.viewmodel.UsuarioViewModel;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private TextInputEditText txtNome;
    private TextInputEditText txtDataNascimento;
    private TextInputEditText txtSexo;
    private TextInputEditText txtTelefone;
    private TextInputEditText txtEmail;
    private UsuarioViewModel usuarioViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.txtTituloToolbar);
        txtTitulo.setText(R.string.txtTituloPerfilUsuario);

        txtNome = findViewById(R.id.txtNome);
        txtDataNascimento = findViewById(R.id.txtDataNascimento);
        txtSexo = findViewById(R.id.txtSexo);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtEmail = findViewById(R.id.txtEmail);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.isLogged().observe(this, new Observer<UsuarioComAtividade>() {
            @Override
            public void onChanged(UsuarioComAtividade usuarioComAtividade) {
                if (usuarioComAtividade != null) {
                    txtNome.setText(usuarioComAtividade.getUsuario().getNome());
                    txtDataNascimento.setText(usuarioComAtividade.getUsuario().getDataNascimento());
                    txtSexo.setText(usuarioComAtividade.getUsuario().getSexo());
                    txtTelefone.setText(usuarioComAtividade.getUsuario().getTelefone());
                    txtEmail.setText(usuarioComAtividade.getUsuario().getEmail());
                } else {
                    startActivity(new Intent(PerfilUsuarioActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
