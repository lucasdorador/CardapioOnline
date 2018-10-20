package lucas.cardapioonline.SQLite;

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
        criaTabelaConfiguracoes(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        dropaTabelas(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }

    private void criaTabelaUsuarios(SQLiteDatabase db) {
        String sql = "create table usuarios (  " +
                "_id	integer not null primary key autoincrement, " +
                "nome	text not null, " +
                "tipoUsuario	text not null, " +
                "endereco	text, " +
                "numero	text, " +
                "bairro	text, " +
                "cidade	text, " +
                "cep	text, " +
                "celular	text, " +
                "email	text, " +
                "genero	text, " +
                "idade	text, " +
                "keyUsuario	text, " +
                "uriFotoPerfil	text)";

        db.execSQL(sql);
        db.execSQL("create index usuariosA on usuarios ( email asc )");
        db.execSQL("create index usuariosB on usuarios ( keyUsuario asc )");
    }

    private void criaTabelaEmpresa(SQLiteDatabase db) {
        String sql = "create table empresa ( _id INTEGER not null primary key autoincrement, " +
                "nome text not null, " +
                "logradouro text, " +
                "numero text, " +
                "bairro text, " +
                "cep text, " +
                "cidade text, " +
                "resumo text, " +
                "horario_funcionamento text, " +
                "key_empresa text, " +
                "telefone text, " +
                "url_logo text )";

        db.execSQL(sql);
        db.execSQL("create index empresaA on empresa ( key_empresa asc )");
    }

    private void criaTabelaCardapio_Itens(SQLiteDatabase db) {
        String sql = "create table cardapio_itens ( _id INTEGER not null primary key autoincrement, " +
                "descricao text not null, " +
                "complemento text, " +
                "grupo text, " +
                "key_produto text, " +
                "key_empresa text, " +
                "valor_inteira real, " +
                "valor_meia real )";

        db.execSQL(sql);
        db.execSQL("create index cardapio_itensA on cardapio_itens ( key_empresa asc )");
        db.execSQL("create index cardapio_itensB on cardapio_itens ( key_produto asc, key_empresa asc )");
    }

    private void criaTabelaConfiguracoes(SQLiteDatabase db) {
        String sql = "create table configuracoes (_id integer not null primary key autoincrement," +
                "configapp_redemoveis text," +
                "configapp_redewifi text," +
                "configapp_armaz_externo text);";

        db.execSQL(sql);
        db.execSQL("create index configuracoesA on configuracoes ( configapp_redemoveis asc )");
        db.execSQL("create index configuracoesB on configuracoes ( configapp_redewifi asc )");
        db.execSQL("create index configuracoesC on configuracoes ( configapp_armaz_externo asc )");
    }

    private void dropaTabelas(SQLiteDatabase db) {
        db.execSQL("drop table if exists usuarios");
        db.execSQL("drop table if exists empresa");
        db.execSQL("drop table if exists cardapio_itens");
        db.execSQL("drop table if exists configuracoes");
    }
}
