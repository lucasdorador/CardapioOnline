package lucas.cardapioonline.Classes;

import android.content.Context;
import android.database.Cursor;

import lucas.cardapioonline.Controller.clConfiguracoesController;

public class clConfiguracoes {

    private static clConfiguracoes configuracoes;
    private static Boolean dadosMoveis, dadosWifi, armazenamentoExterno;
    private static Context context;
    private static clConfiguracoesController controller;

    public clConfiguracoes(Context c) {

        this.dadosMoveis = false;
        this.dadosWifi = false;
        this.armazenamentoExterno = false;
        this.context = c;

        controller = new clConfiguracoesController(this.context);
        Cursor cursor = controller.retornaConsultaConfiguracoes();

        this.dadosMoveis = Boolean.valueOf(cursor.getString(cursor.getColumnIndex("configapp_redemoveis")));
        this.dadosWifi = Boolean.valueOf(cursor.getString(cursor.getColumnIndex("configapp_redewifi")));
        this.armazenamentoExterno = Boolean.valueOf(cursor.getString(cursor.getColumnIndex("configapp_armaz_externo")));

    }

    public static synchronized clConfiguracoes getInstance(Context c2) {
        if (configuracoes == null) {
            configuracoes = new clConfiguracoes(c2);
        }

        return configuracoes;
    }

    public static Boolean dadosMoveis() {
        return dadosMoveis;
    }

    public static Boolean dadosWifi() {
        return dadosWifi;
    }

    public static Boolean armazenamentoExterno() {
        return armazenamentoExterno;
    }

    public static void atualizaDados(Boolean dMoveis, Boolean dWifi, Boolean aExterno) {
        dadosMoveis = dMoveis;
        dadosWifi = dWifi;
        armazenamentoExterno = aExterno;
    }
}
