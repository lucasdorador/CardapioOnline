package lucas.cardapioonline.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO;
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    public Preferencias(Context contextoParametros, String nome_arquivo){
        context = contextoParametros;
        NOME_ARQUIVO = nome_arquivo;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void salvarPreferencias(String dadosGravar, String nomeSecao){
        editor.putString(nomeSecao, dadosGravar);
        editor.commit();
    }

    public String getSecaoPreferencias(String nomeSecao){
        return preferences.getString(nomeSecao, "");
    }

    public void limparPreferencias(){
        editor.clear();
        editor.commit();
    }
}
