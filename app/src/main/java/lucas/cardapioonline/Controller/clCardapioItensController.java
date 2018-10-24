package lucas.cardapioonline.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lucas.cardapioonline.Classes.clCardapio_Itens;
import lucas.cardapioonline.SQLite.clCardapioOnline;

public class clCardapioItensController {

    private SQLiteDatabase db;
    private clCardapioOnline banco;

    public clCardapioItensController(Context context) {
        banco = new clCardapioOnline(context);
    }

    public boolean insereDadosCardapioItens(clCardapio_Itens itens){
        ContentValues valores;
        boolean resultado = true;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put("descricao", itens.getDescricao());
        valores.put("complemento", itens.getComplemento());
        valores.put("grupo", itens.getGrupo());
        valores.put("key_produto", itens.getKey_produto());
        valores.put("key_empresa", itens.getKey_empresa());
        valores.put("valor_inteira", itens.getValor_inteira());
        valores.put("valor_meia", itens.getValor_meia());
        
        resultado = !(db.insert("cardapio_itens", null, valores) == -1);
        db.close();

        return resultado;
    }

    public boolean alteraDadosCardapioItens(clCardapio_Itens itens){
        ContentValues valores;
        boolean resultado = true;

        db = banco.getWritableDatabase();

        String where = "key_produto = '" + itens.getKey_produto() +
                "' and key_empresa = '" + itens.getKey_empresa() + "'";

        valores = new ContentValues();
        valores.put("descricao", itens.getDescricao());
        valores.put("complemento", itens.getComplemento());
        valores.put("grupo", itens.getGrupo());
        valores.put("key_produto", itens.getKey_produto());
        valores.put("key_empresa", itens.getKey_empresa());
        valores.put("valor_inteira", itens.getValor_inteira());
        valores.put("valor_meia", itens.getValor_meia());

        resultado = !(db.update("cardapio_itens", valores, where, null) == -1);
        db.close();

        return resultado;
    }

    public boolean deletaDadosCardapioItens(String key_produto, String key_empresa){
        boolean resultado = true;

        String where = "key_produto = '" + key_produto +
                "' and key_empresa = '" + key_empresa + "'";

        db = banco.getReadableDatabase();
        resultado = !(db.delete("cardapio_itens", where, null) == -1);
        db.close();

        return resultado;
    }


    public boolean existeDadosCadastrados(String key_produto, String key_empresa) {
        boolean resultado = true;

        db = banco.getReadableDatabase();

        String where = "key_produto = '" + key_produto +
                "' and key_empresa = '" + key_empresa + "'";

        long numOfEntries = DatabaseUtils.queryNumEntries(db, "cardapio_itens", where);

        if (numOfEntries == 0l) {
            resultado = false;
        }

        return resultado;
    }

    public List<clCardapio_Itens> retornaListaClasseItensCardapio(String key_empresa) {
        List<clCardapio_Itens> cardapioItensList = new ArrayList<>();
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cardapio_itens WHERE key_empresa = ?", new String[]{key_empresa});

        if (cursor.moveToFirst()) {
            do {
                clCardapio_Itens cardapioItens = new clCardapio_Itens();

                cardapioItens.setComplemento(cursor.getString(cursor.getColumnIndex("complemento")));
                cardapioItens.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                cardapioItens.setGrupo(cursor.getString(cursor.getColumnIndex("grupo")));
                cardapioItens.setKey_empresa(cursor.getString(cursor.getColumnIndex("key_empresa")));
                cardapioItens.setKey_produto(cursor.getString(cursor.getColumnIndex("key_produto")));
                cardapioItens.setValor_inteira(cursor.getString(cursor.getColumnIndex("valor_inteira")));
                cardapioItens.setValor_meia(cursor.getString(cursor.getColumnIndex("valor_meia")));

                cardapioItensList.add(cardapioItens);

            }
            while (cursor.moveToNext());
        }

        return cardapioItensList;

    }
}
