package lucas.cardapioonline.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

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
}
