package br.edu.ifsp.arq.dmo.ifitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.ifsp.arq.dmo.ifitness.controler.AtividadeController;
import br.edu.ifsp.arq.dmo.ifitness.model.Atividade;

public class EditorAtividadeActivity extends AppCompatActivity {

    private Button salvar;
    private EditText duracao;
    private EditText km;
    private EditText caloria;
    private RadioGroup tipoAtividade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atividade);

        Button salvar = findViewById(R.id.btnAddAtividade);
        EditText duracao = findViewById(R.id.txtDuracao);
        EditText km = findViewById(R.id.txtKM);
        EditText caloria = findViewById(R.id.txtCaloria);
        RadioGroup rgtipoAtividade = findViewById(R.id.rgTipoAtividade);

        salvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int idRadioSelecionado = rgtipoAtividade.getCheckedRadioButtonId();
                RadioButton radio = findViewById(idRadioSelecionado);

                int tipoAtividade = 0;

                switch (radio.getText().toString()){
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
                        tipoAtividade,
                        Integer.parseInt(duracao.getText().toString()),
                        Double.parseDouble(km.getText().toString()),
                        Integer.parseInt(caloria.getText().toString())
                );

                AtividadeController controller = new AtividadeController(getApplicationContext());
                controller.adicioarAtividade(atividade);

                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                it.putExtra("atividade", atividade);
                startActivity(it);
            }
        });
    }
}

