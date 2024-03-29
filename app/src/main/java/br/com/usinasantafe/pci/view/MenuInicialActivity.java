package br.com.usinasantafe.pci.view;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.usinasantafe.pci.BuildConfig;
import br.com.usinasantafe.pci.PCIContext;
import br.com.usinasantafe.pci.R;
import br.com.usinasantafe.pci.ReceberAlarme;
import br.com.usinasantafe.pci.util.ConexaoWeb;

public class MenuInicialActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private TextView textViewProcesso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        pciContext = (PCIContext) getApplication();
        textViewProcesso = findViewById(R.id.textViewProcesso);
        TextView textViewPrincipal = findViewById(R.id.textViewPrincipal);

        textViewPrincipal.setText("PRINCIPAL - V " + BuildConfig.VERSION_NAME);

        if(!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, PERMISSIONS, 112);
        }

        startTimer();
        verifEnvio();

        ArrayList<String> itens = new ArrayList<>();
        itens.add("CHECKLIST");
        itens.add("REENVIO DE DADOS");
        itens.add("CONFIGURAÇÃO");
        itens.add("RELAÇÃO DE OS");
        itens.add("SAIR");

        AdapterList adapterList = new AdapterList(this, itens);
        ListView listView = findViewById(R.id.listaMenuInicial);
        listView.setAdapter(adapterList);

        listView.setOnItemClickListener((l, v, position, id) -> {

            TextView textView = v.findViewById(R.id.textViewItemList);
            String text = textView.getText().toString();

            Intent it;
            switch (text) {
                case "CHECKLIST": {
                    if (pciContext.getCheckListCTR().hasElementFunc()) {
                        it = new Intent(MenuInicialActivity.this, FuncionarioActivity.class);
                        startActivity(it);
                        finish();
                    }
                    break;
                }
                case "REENVIO DE DADOS": {
                    it = new Intent(MenuInicialActivity.this, EnvioDadosActivity.class);
                    startActivity(it);
                    finish();
                    break;
                }
                case "CONFIGURAÇÃO": {
                    it = new Intent(MenuInicialActivity.this, ConfigActivity.class);
                    startActivity(it);
                    finish();
                    break;
                }
                case "RELAÇÃO DE OS": {
                    it = new Intent(MenuInicialActivity.this, FuncVerOSActivity.class);
                    startActivity(it);
                    finish();
                    break;
                }
                case "SAIR": {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
            }

        });

    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public void onBackPressed()  {
    }

    public void startTimer() {

        Intent intent = new Intent(this, ReceberAlarme.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND, 1);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 60000, pendingIntent);

    }

    public void verifEnvio(){
        if (pciContext.getCheckListCTR().verDadosEnvio()) {
            textViewProcesso.setTextColor(Color.RED);
            textViewProcesso.setText("Existem Dados para serem Enviados");
        } else {
            textViewProcesso.setTextColor(Color.GREEN);
            textViewProcesso.setText("Todos os Dados já foram Enviados");
        }
    }

}