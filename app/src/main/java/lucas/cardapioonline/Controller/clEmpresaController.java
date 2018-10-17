package lucas.cardapioonline.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import lucas.cardapioonline.Classes.clEmpresa;
import lucas.cardapioonline.SQLite.clCardapioOnline;

public class clEmpresaController {

    private SQLiteDatabase db;
    private clCardapioOnline banco;

    public clEmpresaController(Context context) {
        banco = new clCardapioOnline(context);
    }

    public boolean insereDadosEmpresa(clEmpresa empresa) {
        ContentValues valores;
        boolean resultado = true;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put("nome", empresa.getNome());
        valores.put("logradouro", empresa.getLogradouro());
        valores.put("numero", empresa.getNumero());
        valores.put("bairro", empresa.getBairro());
        valores.put("cep", empresa.getCep());
        valores.put("cidade", empresa.getCidade());
        valores.put("resumo", empresa.getResumo());
        valores.put("horario_funcionamento", empresa.getHorario_funcionamento());
        valores.put("key_empresa", empresa.getKey_empresa());
        valores.put("telefone", empresa.getTelefone());
        valores.put("url_logo", empresa.getUrl_logo());

        resultado = !(db.insert("empresa", null, valores) == -1);
        db.close();

        return resultado;
    }

    public boolean alteraDadosEmpresa(clEmpresa empresa) {
        ContentValues valores;
        boolean resultado = true;

        db = banco.getWritableDatabase();

        String where = "key_empresa = '" + empresa.getKey_empresa().toString() + "'";

        valores = new ContentValues();
        valores.put("nome", empresa.getNome());
        valores.put("logradouro", empresa.getLogradouro());
        valores.put("numero", empresa.getNumero());
        valores.put("bairro", empresa.getBairro());
        valores.put("cep", empresa.getCep());
        valores.put("cidade", empresa.getCidade());
        valores.put("resumo", empresa.getResumo());
        valores.put("horario_funcionamento", empresa.getHorario_funcionamento());
        valores.put("telefone", empresa.getTelefone());
        valores.put("url_logo", empresa.getUrl_logo());

        resultado = !(db.update("empresa", valores, where, null) == -1);
        db.close();

        return resultado;
    }

    public boolean deletaDadosEmpresa(String keyEmpresa) {
        boolean resultado = true;

        String where = "key_empresa = '" + keyEmpresa + "'";
        db = banco.getReadableDatabase();
        resultado = !(db.delete("empresa", where, null) == -1);
        db.close();

        return resultado;
    }

    public boolean existeDadosCadastrados(String keyEmpresa) {
        boolean resultado = true;

        db = banco.getReadableDatabase();
        String where = "key_empresa = '" + keyEmpresa + "'";
        long numOfEntries = DatabaseUtils.queryNumEntries(db, "empresa", where);

        if (numOfEntries == 0l) {
            resultado = false;
        }

        return resultado;
    }
}
