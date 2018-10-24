package lucas.cardapioonline.Controller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import lucas.cardapioonline.Classes.clEmpresa;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.DAO.ConfiguracaoFirebase;
import lucas.cardapioonline.SQLite.clCardapioOnline;

public class clEmpresaController {

    private SQLiteDatabase db;
    private clCardapioOnline banco;
    private StorageReference storageReference;
    private clUtil util;
    private Activity activity;

    public clEmpresaController(Context context) {
        banco = new clCardapioOnline(context);
        storageReference = ConfiguracaoFirebase.getReferenciaStorage();
        activity = (Activity) context;
        util = new clUtil(activity);

    }

    public boolean insereDadosEmpresa(final clEmpresa empresa) {
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

        if (!empresa.getUrl_logo().equals("")) {
            StorageReference storageRef = storageReference.child("fotoLogoEmpresas/" + empresa.getKey_empresa() + ".png");
            final long ONE_MEGABYTE = 1024 * 1024;
            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    util.gravarImagemArmazenamento(empresa.getKey_empresa().toString(), bytes);
                }
            });
        }

        resultado = !(db.insert("empresa", null, valores) == -1);
        db.close();

        return resultado;
    }

    public boolean alteraDadosEmpresa(final clEmpresa empresa) {
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

        if (!empresa.getUrl_logo().equals("")) {
            StorageReference storageRef = storageReference.child("fotoLogoEmpresas/" + empresa.getKey_empresa() + ".png");
            final long ONE_MEGABYTE = 1024 * 1024;
            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    util.gravarImagemArmazenamento(empresa.getKey_empresa().toString(), bytes);
                }
            });
        }

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

    public List<clEmpresa> retornaListaClasseEmpresaSQLite() {
        List<clEmpresa> empresalist = new ArrayList<>();


        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM empresa", null);

        if (cursor.moveToFirst()) {
            do {
                clEmpresa empresa = new clEmpresa();
                empresa.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                empresa.setCep(cursor.getString(cursor.getColumnIndex("cep")));
                empresa.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
                empresa.setHorario_funcionamento(cursor.getString(cursor.getColumnIndex("horario_funcionamento")));
                empresa.setLogradouro(cursor.getString(cursor.getColumnIndex("logradouro")));
                empresa.setKey_empresa(cursor.getString(cursor.getColumnIndex("key_empresa")));
                empresa.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                empresa.setNumero(cursor.getString(cursor.getColumnIndex("numero")));
                empresa.setResumo(cursor.getString(cursor.getColumnIndex("resumo")));
                empresa.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
                empresa.setUrl_logo(cursor.getString(cursor.getColumnIndex("url_logo")));

                empresalist.add(empresa);

            }
            while (cursor.moveToNext());
        }

        return empresalist;

    }

    public clEmpresa retornaClasseEmpresaSQLite(String keyEmpresa) {
        clEmpresa empresa = new clEmpresa();
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM empresa WHERE key_empresa = ?", new String[]{keyEmpresa});

        if (cursor.moveToFirst()) {
            do {
                empresa.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                empresa.setCep(cursor.getString(cursor.getColumnIndex("cep")));
                empresa.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
                empresa.setHorario_funcionamento(cursor.getString(cursor.getColumnIndex("horario_funcionamento")));
                empresa.setLogradouro(cursor.getString(cursor.getColumnIndex("logradouro")));
                empresa.setKey_empresa(cursor.getString(cursor.getColumnIndex("key_empresa")));
                empresa.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                empresa.setNumero(cursor.getString(cursor.getColumnIndex("numero")));
                empresa.setResumo(cursor.getString(cursor.getColumnIndex("resumo")));
                empresa.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
                empresa.setUrl_logo(cursor.getString(cursor.getColumnIndex("url_logo")));
            }
            while (cursor.moveToNext());
        }

        return empresa;

    }
}
