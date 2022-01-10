package br.edu.ifsp.arq.dmo.ifitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsp.arq.dmo.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmo.ifitness.viewmodel.UsuarioViewModel;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private Button btnCadastre_se;
    private Button btnEntrar;
    private TextInputEditText txtEmail;
    private TextInputEditText txtSenha;
    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.txtTituloToolbar);
        txtTitulo.setText(R.string.txtTituloLogin);

        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioViewModel.login(txtEmail.getText().toString(), txtSenha.getText().toString())
                        .observe(LoginActivity.this, new Observer<Usuario>() {
                            @Override
                            public void onChanged(Usuario usuario) {
                                if (usuario == null) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.msg_login), Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

        btnCadastre_se = findViewById(R.id.btnCadastre_se);
        btnCadastre_se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}