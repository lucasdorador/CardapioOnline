package lucas.cardapioonline.Controller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lucas.cardapioonline.Classes.clGrupos;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.SQLite.clCardapioOnline;

public class clGrupoController {

    private SQLiteDatabase db;
    private clCardapioOnline banco;
    private clUtil util;
    private Activity activity;

    public clGrupoController(Context context) {
        banco = new clCardapioOnline(context);
        activity = (Activity) context;
        util = new clUtil(activity);

    }

    public boolean insereDadosGrupos(final clGrupos empresa) {
        ContentValues valores;
        boolean resultado = true;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put("key_grupo", empresa.getKey_grupo());
        valores.put("descricao", empresa.getDescricao());
        resultado = !(db.insert("grupos", null, valores) == -1);
        db.close();

        return resultado;
    }

    public boolean alteraDadosGrupos(final clGrupos empresa) {
        ContentValues valores;
        boolean resultado = true;

        db = banco.getWritableDatabase();

        String where = "key_grupo = '" + empresa.getKey_grupo() + "'";

        valores = new ContentValues();
        valores.put("descricao", empresa.getDescricao());
        resultado = !(db.update("grupos", valores, where, null) == -1);
        db.close();

        return resultado;
    }

    public boolean deletaDadosGrupos(String keyGrupo) {
        boolean resultado = true;

        String where = "key_grupo = '" + keyGrupo + "'";
        db = banco.getReadableDatabase();
        resultado = !(db.delete("grupos", where, null) == -1);
        db.close();

        return resultado;
    }

    public boolean existeDadosCadastrados(String keyGrupo) {
        boolean resultado = true;
        long numOfEntries;

        db = banco.getReadableDatabase();
        if (!keyGrupo.equals("")) {
            String where = "key_grupo = '" + keyGrupo + "'";
            numOfEntries = DatabaseUtils.queryNumEntries(db, "grupos", where);
        } else {
            numOfEntries = DatabaseUtils.queryNumEntries(db, "grupos", null);
        }

        if (numOfEntries == 0l) {
            resultado = false;
        }

        return resultado;
    }

    public List<clGrupos> retornaListaClasseGruposSQLite() {
        List<clGrupos> gruposList = new ArrayList<>();


        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM grupos", null);

        if (cursor.moveToFirst()) {
            do {
                clGrupos grupos = new clGrupos();
                grupos.setKey_grupo(cursor.getString(cursor.getColumnIndex("key_grupo")));
                grupos.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));

                gruposList.add(grupos);

            }
            while (cursor.moveToNext());
        }

        return gruposList;

    }

    public clGrupos retornaClasseGruposSQLite(String keyGrupo) {
        clGrupos empresa = new clGrupos();
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM grupos WHERE key_grupo = ?", new String[]{keyGrupo});

        if (cursor.moveToFirst()) {
            do {
                empresa.setKey_grupo(cursor.getString(cursor.getColumnIndex("key_grupo")));
                empresa.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            }
            while (cursor.moveToNext());
        }

        return empresa;

    }
}

