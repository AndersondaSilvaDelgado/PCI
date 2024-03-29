package br.com.usinasantafe.pci.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.usinasantafe.pci.PCIContext;
import br.com.usinasantafe.pci.R;
import br.com.usinasantafe.pci.model.bean.variavel.RespItemBean;

public class DescricaoQuestaoActivity extends ActivityGeneric {

    private PCIContext pciContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao_questao);

        pciContext = (PCIContext) getApplication();

        TextView textViewDescQuestao = findViewById(R.id.textViewDescQuestao);
        Button buttonCancDescrQuestao = findViewById(R.id.buttonCancDescrQuestao);
        Button buttonEditarDescrQuestao = findViewById(R.id.buttonEditarDescrQuestao);

        RespItemBean respItemBean = pciContext.getCheckListCTR().getRespItem();

        String questao = "";

        questao = "QUESTÃO " + pciContext.getCheckListCTR().getItemBean().getSeqItem() + "\n";
        questao = questao + "DESCR: " + pciContext.getCheckListCTR().getServico(pciContext.getCheckListCTR().getItemBean().getIdServicoItem()).getDescrServico() + "\n";
        if(respItemBean.getOpcaoRespItem() == 1){
            questao = questao + "INCONFORME\n";
        }
        else{
            questao = questao + "CONFORME\n";
        }

        if(respItemBean.getObsRespItem().equals("null")){
            questao = questao + "\nOBS.:";
        }
        else{
            questao = questao + "\nOBS.:" + respItemBean.getObsRespItem();
        }

        textViewDescQuestao.setText(questao);

        buttonEditarDescrQuestao.setOnClickListener(v -> {
            Intent it = new Intent( DescricaoQuestaoActivity.this, QuestaoActivity.class);
            startActivity(it);
            finish();
        });

        buttonCancDescrQuestao.setOnClickListener(v -> {
            Intent it = new Intent(DescricaoQuestaoActivity.this, ListaQuestaoActivity.class);
            startActivity(it);
            finish();
        });

    }

    public void onBackPressed()  {
    }

}