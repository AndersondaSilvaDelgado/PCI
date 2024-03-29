package br.com.usinasantafe.pci.model.dao;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.ItemBean;
import br.com.usinasantafe.pci.model.bean.estatica.OSBean;
import br.com.usinasantafe.pci.util.Tempo;
import br.com.usinasantafe.pci.util.VerifDadosServ;

public class OSDAO {

    public OSDAO() {
    }

    public OSBean getOS(Long idOS){
        List<OSBean> osList = osList(idOS);
        OSBean osBean = osList.get(0);
        osList.clear();
        return osBean;
    }

    public List<OSBean> osList(Long idOS){
        OSBean osBean = new OSBean();
        return osBean.get("idOS", idOS);
    }

    public List<OSBean> osList(){
        OSBean osBean = new OSBean();
        return osBean.all();
    }

    public ArrayList<Long> idPlantaOSList(){
        List osList = osList();
        ArrayList<Long> idPlantaList = new ArrayList<>();
        for(int i = 0; i < osList.size(); i++){
            OSBean osBean = (OSBean) osList.get(i);
            idPlantaList.add(osBean.getIdPlantaOS());
        }
        osList.clear();
        return idPlantaList;
    }

    public void verOS(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        VerifDadosServ.getInstance().verDados(dado, "OS", telaAtual, telaProx, progressDialog);
    }

    public void recDadosOS(JSONArray jsonArray) throws JSONException {

        OSBean osBean = new OSBean();
        osBean.deleteAll();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject objeto = jsonArray.getJSONObject(i);
            Gson gson = new Gson();
            osBean = gson.fromJson(objeto.toString(), OSBean.class);
            osBean.insert();

        }

    }

    public void deleteOS(){
        List<OSBean> osList = osList();
        for(OSBean osBean : osList){
            if(Tempo.getInstance().dthrStringToLong(osBean.getDthrPrevTerm()) < Tempo.getInstance().dthrStringToLong(Tempo.getInstance().dataCHora())){
                osBean.delete();
            }
        }
    }


}
