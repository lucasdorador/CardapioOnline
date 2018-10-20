package lucas.cardapioonline.Controller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.SQLite.clCardapioOnline;

public class clConfiguracoesController {

    private SQLiteDatabase db;
    private clCardapioOnline banco;
    private clUtil util;
    private Activity activity;

    public clConfiguracoesController(Context context) {
        banco = new clCardapioOnline(context);
        activity = (Activity) context;
        util = new clUtil(activity);
        insereDadosConfigInicial();
    }

    public boolean insereDadosConfiguracoes(String ConfigMoveis, String ConfigWifi, String ConfigInterno) {
        ContentValues valores;
        boolean resultado = true;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put("configapp_redemoveis", ConfigMoveis);
        valores.put("configapp_redewifi", ConfigWifi);
        valores.put("configapp_armaz_externo", ConfigInterno);

        resultado = !(db.update("configuracoes", valores, null, null) == -1);
        db.close();

        return resultado;
    }

    private void insereDadosConfigInicial() {
        if (!existeDadosConfiguracao()) {
            ContentValues valores;

            db = banco.getWritableDatabase();
            valores = new ContentValues();
            valores.put("configapp_redemoveis", "false");
            valores.put("configapp_redewifi", "false");
            valores.put("configapp_armaz_externo", "false");

            db.insert("configuracoes", null, valores);
            db.close();
        }
    }

    public Cursor retornaConsultaConfiguracoes(){
        Cursor cursor;
        String[] campos =  {"configapp_redemoveis", "configapp_redewifi", "configapp_armaz_externo"};
        db = banco.getReadableDatabase();
        cursor = db.query("configuracoes", campos, null, null, null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }


    private boolean existeDadosConfiguracao() {
        boolean resultado = true;

        db = banco.getReadableDatabase();
        long numOfEntries = DatabaseUtils.queryNumEntries(db, "configuracoes", null);

        if (numOfEntries == 0l) {
            resultado = false;
        }

        return resultado;
    }

}
