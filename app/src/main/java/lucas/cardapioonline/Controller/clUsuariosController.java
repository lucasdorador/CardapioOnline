package lucas.cardapioonline.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import lucas.cardapioonline.Classes.clUsuarios;
import lucas.cardapioonline.SQLite.clCardapioOnline;

public class clUsuariosController {

    private SQLiteDatabase db;
    private clCardapioOnline banco;

    public clUsuariosController(Context context) {
        banco = new clCardapioOnline(context);
    }

    public boolean insereDadosUsuarios(clUsuarios usuarios){
        ContentValues valores;
        boolean resultado = true;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put("nome", usuarios.getNome());
        valores.put("tipoUsuario", usuarios.getTipoUsuario());
        valores.put("endereco", usuarios.getEndereco());
        valores.put("numero", usuarios.getNumero());
        valores.put("bairro", usuarios.getBairro());
        valores.put("cidade", usuarios.getCidade());
        valores.put("cep", usuarios.getCep());
        valores.put("celular", usuarios.getCelular());
        valores.put("email", usuarios.getEmail());
        valores.put("genero", usuarios.getGenero());
        valores.put("idade", usuarios.getIdade());
        valores.put("keyUsuario", usuarios.getKeyUsuario());
        valores.put("uriFotoPerfil", usuarios.getUriFotoPerfil());

        resultado = !(db.insert("usuarios", null, valores) == -1);
        db.close();

        return resultado;
    }

    public boolean alteraDadosUsuarios(clUsuarios usuarios){
        ContentValues valores;
        boolean resultado = true;

        db = banco.getWritableDatabase();

        String where = "keyUsuario = '" + usuarios.getKeyUsuario() + "'";

        valores = new ContentValues();
        valores.put("nome", usuarios.getNome());
        valores.put("tipoUsuario", usuarios.getTipoUsuario());
        valores.put("endereco", usuarios.getEndereco());
        valores.put("numero", usuarios.getNumero());
        valores.put("bairro", usuarios.getBairro());
        valores.put("cidade", usuarios.getCidade());
        valores.put("cep", usuarios.getCep());
        valores.put("celular", usuarios.getCelular());
        valores.put("email", usuarios.getEmail());
        valores.put("genero", usuarios.getGenero());
        valores.put("idade", usuarios.getIdade());
        valores.put("uriFotoPerfil", usuarios.getUriFotoPerfil());;

        resultado = !(db.update("usuarios", valores, where, null) == -1);
        db.close();

        return resultado;
    }

    public boolean deletaDadosUsuarios(String keyUsuarios){
        boolean resultado = true;

        String where = "keyUsuario = '" + keyUsuarios + "'";
        db = banco.getReadableDatabase();
        resultado = !(db.delete("usuarios", where, null) == -1);
        db.close();

        return resultado;
    }

    public boolean existeDadosCadastrados(String keyUsuario) {
        boolean resultado = true;

        db = banco.getReadableDatabase();
        String where = "keyUsuario = '" + keyUsuario + "'";
        long numOfEntries = DatabaseUtils.queryNumEntries(db, "usuarios", where);

        if (numOfEntries == 0l) {
            resultado = false;
        }

        return resultado;
    }

    public String retornaConsultaUsuarioByEmail(String campo, String email){
        String resultado = "";
        Cursor cursor;
        String[] campos =  {campo};
        String where = "email = '" + email + "'";
        db = banco.getReadableDatabase();
        cursor = db.query("usuarios", campos, where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
            resultado = cursor.getString(0);
        }

        db.close();

        return resultado;
    }
}
