package br.edu.ifsp.arq.dmo.ifitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.dmo.ifitness.controler.AtividadeController;
import br.edu.ifsp.arq.dmo.ifitness.model.Atividade;
import br.edu.ifsp.arq.dmo.ifitness.model.AtividadeAdapter;

public class MainActivity extends AppCompatActivity {

    private Button novaAtividade;
    private List<Atividade> atividades = new ArrayList<>();
    private AtividadeAdapter adapter;
    private ListView listView;
    private TextView status;

    AtividadeController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        novaAtividade = findViewById(R.id.btnNovaAtividade);
        listView      = findViewById(R.id.lstAtividades);
        status        = findViewById(R.id.txtAtividades);

        controller = new AtividadeController(getApplicationContext());

        adapter= new AtividadeAdapter(getApplicationContext(), controller.getAtividades());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Atividade atividade = atividades.get(position);
            }
        });

        novaAtividade.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent it = new Intent(getApplicationContext(), EditorAtividadeActivity.class);
                it.putExtra("Atividades", (Serializable) atividades);
                startActivity(it);

            }
        });

        Intent intent = getIntent();
        Atividade atividade = (Atividade) intent.getSerializableExtra("atividade");

        if(atividade != null) {
            controller.adicioarAtividade(atividade);

            status.setText(controller.qntAtividade() + " atividade(s) - " + controller.getTotalMinutos() + " minutos - " + controller.getTotalKM() + " KM - " + controller.getTotalCalorias() + " calorias");
        }
    }
}