package br.edu.ifsp.arq.dmo.ifitness;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.dmo.ifitness.model.Atividade;
import br.edu.ifsp.arq.dmo.ifitness.model.Trofeu;
import br.edu.ifsp.arq.dmo.ifitness.model.UsuarioComAtividade;
import br.edu.ifsp.arq.dmo.ifitness.viewmodel.UsuarioViewModel;

public class CadastroAtividadeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private Button salvar;
    private EditText duracao;
    private EditText km;
    private EditText dataAtividade;
    private EditText caloria;
    private RadioGroup tipoAtividade;

    private UsuarioComAtividade usuarioComAtividade;

    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_atividade);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.txtTituloToolbar);
        txtTitulo.setText(R.string.txtTituloCadastroAtividade);

        salvar = findViewById(R.id.btnAddAtividade);
        duracao = findViewById(R.id.txtDuracao);
        km = findViewById(R.id.txtKM);
        dataAtividade = findViewById(R.id.txtDataAtividade);
        caloria = findViewById(R.id.txtCaloria);
        tipoAtividade = findViewById(R.id.rgTipoAtividade);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.isLogged().observe(this, new Observer<UsuarioComAtividade>() {
            @Override
            public void onChanged(UsuarioComAtividade usuarioComAtividade) {
                if (usuarioComAtividade != null) {
                    CadastroAtividadeActivity.this.usuarioComAtividade = usuarioComAtividade;
                } else {
                    startActivity(new Intent(CadastroAtividadeActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                int idRadioSelecionado = tipoAtividade.getCheckedRadioButtonId();
                RadioButton radio = findViewById(idRadioSelecionado);

                int tipoAtividade = 0;

                switch (radio.getText().toString()) {
                    case "Caminhada":
                        tipoAtividade = 0;
                        break;
                    case "Ciclismo":
                        tipoAtividade = 1;
                        break;
                    case "Corrida":
                        tipoAtividade = 2;
                        break;
                    case "Natação":
                        tipoAtividade = 3;
                        break;
                    default:
                        break;
                }

                Atividade atividade = new Atividade(
                        usuarioComAtividade.getUsuario().getId(),
                        tipoAtividade,
                        Integer.parseInt(duracao.getText().toString()),
                        Double.parseDouble(km.getText().toString()),
                        dataAtividade.getText().toString(),
                        Integer.parseInt(caloria.getText().toString())
                );

                usuarioComAtividade.getUsuario().setPontuacao(usuarioComAtividade.getUsuario().getPontuacao() + atividade.getKm().intValue());
                usuarioComAtividade.getAtividades().add(atividade);

                Integer pontuacao = usuarioComAtividade.getUsuario().getPontuacao();
                List<Trofeu> emblemas = new ArrayList<>();

                if (pontuacao >= 150) {
                    emblemas = List.of(Trofeu.INICIANTE, Trofeu.BRONZE, Trofeu.PRATA, Trofeu.OURO, Trofeu.PLATINIUM);
                } else if (pontuacao >= 100) {
                    emblemas = List.of(Trofeu.INICIANTE, Trofeu.BRONZE, Trofeu.PRATA, Trofeu.OURO);
                } else if (pontuacao >= 50) {
                    emblemas = List.of(Trofeu.INICIANTE, Trofeu.BRONZE, Trofeu.PRATA);
                } else if (pontuacao >= 25) {
                    emblemas = List.of(Trofeu.INICIANTE, Trofeu.BRONZE);
                } else if (pontuacao >= 15) {
                    emblemas = List.of(Trofeu.INICIANTE);
                }

                usuarioComAtividade.getUsuario().setEmblemas(emblemas);

                usuarioViewModel.update(usuarioComAtividade);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

