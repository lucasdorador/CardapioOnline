package lucas.cardapioonline.Classes;

import android.app.Activity;

import lucas.cardapioonline.Activity.AtualizaDadosActivity;
import lucas.cardapioonline.Helper.Preferencias;

public class clRegrasPersistencia {

    private String mensagemConexao = "", telaChamada = "", PerguntaConexao = "";;
    private Activity activity;
    private clUtil util;
    private Preferencias preferencias;
    private AtualizaDadosActivity atualizaDadosActivity;
    private boolean resultPreferencias = false, vlBDadosmoveis = false;

    public clRegrasPersistencia(Activity a, String telaChamada) {
        activity = a;
        atualizaDadosActivity = (AtualizaDadosActivity) activity;
        this.telaChamada = telaChamada;
        util = new clUtil(a);
        preferencias = new Preferencias(activity, "app.InternetDadosMoveis");
    }

    public boolean validaAtualizacao_Wifi_DadosMoveis(clConfiguracoes c) {
        boolean resultado = true;
        resultPreferencias = true;
        mensagemConexao = "";

        if (!c.dadosWifi() && !c.dadosMoveis()) {
            resultado = false;
            mensagemConexao = "Nenhuma opção para atualização está configurada, verifique nas opção" +
                    "de configuração do App no menu configurações!";
        }
        if ((c.dadosWifi() && c.dadosMoveis()) && util.isConected(activity)) {
            resultado = true;
            mensagemConexao = "";
        } else if ((c.dadosWifi()) && (!util.conexaoWifi(activity))) {
            resultado = false;
            mensagemConexao = "Atualização somente por Wifi. Conecte-se para poder atualizar";
        } else if ((c.dadosMoveis()) && (!util.conexaoDadosMoveis(activity))) {
            resultado = false;
            mensagemConexao = "Atualização somente por Dados Móveis. Conecte-se para poder atualizar";
        } else if ((c.dadosMoveis()) && (util.conexaoDadosMoveis(activity))) {
            String pref = preferencias.getSecaoPreferencias("DadosMoveis");
            if (!pref.equals("SIM")) {
                vlBDadosmoveis = true;
                resultado = false;
                PerguntaConexao = "Atualização por Dados Móveis poderá ser cobrado de sua franquia de internet," +
                        "deseja continuar?";
            } else {
                resultado = true;
                mensagemConexao = "";
            }
        }


        if ((telaChamada.equals("AtualizaDados")) && (atualizaDadosActivity != null)){
            atualizaDadosActivity.setDadosTela(resultPreferencias, vlBDadosmoveis, mensagemConexao, PerguntaConexao);
        }

        return resultado;

    }

}
