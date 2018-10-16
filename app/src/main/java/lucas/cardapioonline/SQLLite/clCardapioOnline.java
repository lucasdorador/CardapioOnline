package lucas.cardapioonline.SQLLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class clCardapioOnline extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "cardapioonline.db";
    private static final int VERSAO = 1;


    public clCardapioOnline(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        criaTabelaUsuarios(sqLiteDatabase);
        criaTabelaEmpresa(sqLiteDatabase);
        criaTabelaCardapio_Itens(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        dropaTabelas(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }

    private void criaTabelaUsuarios(SQLiteDatabase db) {
        String sql = "CREATE TABLE usuarios (  " +
                "_id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "nome	TEXT NOT NULL, " +
                "tipoUsuario	TEXT NOT NULL, " +
                "endereco	TEXT, " +
                "numero	TEXT, " +
                "bairro	TEXT, " +
                "cidade	TEXT, " +
                "cep	TEXT, " +
                "celular	TEXT, " +
                "email	TEXT, " +
                "genero	TEXT, " +
                "idade	TEXT, " +
                "keyUsuario	TEXT, " +
                "uriFotoPerfil	TEXT";

        db.execSQL(sql);
        db.execSQL("CREATE UNIQUE INDEX cardapio_itensA ON cardapio_itens ( key_produto ASC )");
        db.close();
    }

    private void criaTabelaEmpresa(SQLiteDatabase db) {
        String sql = "CREATE TABLE empresa ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "logradouro TEXT, " +
                "numero TEXT, " +
                "bairro TEXT, " +
                "cep TEXT, " +
                "cidade TEXT, " +
                "resumo TEXT, " +
                "horario_funcionamento TEXT, " +
                "key_empresa TEXT, " +
                "telefone TEXT, " +
                "url_logo TEXT )";

        db.execSQL(sql);
        db.execSQL("CREATE INDEX empresaA ON empresa ( key_empresa ASC )");
        db.close();
    }

    private void criaTabelaCardapio_Itens(SQLiteDatabase db) {
        String sql = "CREATE TABLE cardapio_itens ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "descricao TEXT, " +
                "complemento TEXT, " +
                "grupo TEXT, " +
                "key_produto TEXT, " +
                "valor_inteira REAL, " +
                "valor_meia REAL )";

        db.execSQL(sql);
        db.execSQL("CREATE INDEX usuariosA ON usuarios ( keyUsuario ASC )");
        db.close();
    }

    private void dropaTabelas(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS empresa");
        db.execSQL("DROP TABLE IF EXISTS cardapio_itens");
    }
}
