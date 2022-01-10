package br.edu.ifsp.arq.dmo.ifitness;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsp.arq.dmo.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmo.ifitness.viewmodel.UsuarioViewModel;

public class CadastroUsuarioActivity extends AppCompatActivity {
    private Button btnCadastrar;
    private TextInputEditText txtNome;
    private TextInputEditText txtDataNascimento;
    private TextInputEditText txtSexo;
    private TextInputEditText txtTelefone;
    private TextInputEditText txtEmail;
    private TextInputEditText txtSenha;

    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        txtNome = findViewById(R.id.txtNome);
        txtDataNascimento = findViewById(R.id.txtDataNascimento);
        txtSexo = findViewById(R.id.txtSexo);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario(
                        txtNome.getText().toString(),
                        txtDataNascimento.getText().toString(),
                        txtSexo.getText().toString(),
                        txtTelefone.getText().toString(),
                        txtEmail.getText().toString(),
                        txtSenha.getText().toString()
                );

                usuarioViewModel.createUsuario(usuario);

                usuarioViewModel.login(usuario.getEmail(), usuario.getSenha())
                        .observe(CadastroUsuarioActivity.this, new Observer<Usuario>() {
                            @Override
                            public void onChanged(Usuario usuario) {
                                finish();
                            }
                        });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

