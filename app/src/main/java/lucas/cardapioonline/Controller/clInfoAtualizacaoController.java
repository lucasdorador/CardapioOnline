package lucas.cardapioonline.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import lucas.cardapioonline.Classes.clInfoAtualizacao;
import lucas.cardapioonline.SQLite.clCardapioOnline;

public class clInfoAtualizacaoController {

    private SQLiteDatabase db;
    private clCardapioOnline banco;

    public clInfoAtualizacaoController(Context context) {
        banco = new clCardapioOnline(context);
    }

    public boolean insereDadosInfoAtualizacao(clInfoAtualizacao infoAtualizacao) {
        ContentValues valores;
        boolean resultado = true;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put("data_ultima_atualizacao", infoAtualizacao.getData_atualizacao());
        valores.put("hora_ultima_atualizacao", infoAtualizacao.getHora_atualizacao());

        resultado = !(db.insert("ultimaatualizacao", null, valores) == -1);
        db.close();

        return resultado;
    }

    public boolean alteraDadosInfoAtualizacao(clInfoAtualizacao infoAtualizacao) {
        ContentValues valores;
        boolean resultado = true;

        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("data_ultima_atualizacao", infoAtualizacao.getData_atualizacao());
        valores.put("hora_ultima_atualizacao", infoAtualizacao.getHora_atualizacao());

        resultado = !(db.update("ultimaatualizacao", valores, null, null) == -1);
        db.close();

        return resultado;
    }

    public boolean existeDadosCadastrados() {
        boolean resultado = true;

        db = banco.getReadableDatabase();
        long numOfEntries = DatabaseUtils.queryNumEntries(db, "ultimaatualizacao", null);

        if (numOfEntries == 0l) {
            resultado = false;
        }

        return resultado;
    }

    public clInfoAtualizacao retornaInfoAtualizacaoCompleto(String email) {
        clInfoAtualizacao infoAtualizacao = new clInfoAtualizacao();
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ultimaatualizacao", null);

        if (cursor.moveToFirst()) {
            do {
                infoAtualizacao.setData_atualizacao(cursor.getString(cursor.getColumnIndex("data_ultima_atualizacao")));
                infoAtualizacao.setHora_atualizacao(cursor.getString(cursor.getColumnIndex("hora_ultima_atualizacao")));
            }
            while (cursor.moveToNext());
        }

        return infoAtualizacao;
    }
}
