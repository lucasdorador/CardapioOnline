package lucas.cardapioonline.Classes;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;
import lucas.cardapioonline.Activity.AtualizaDadosActivity;
import lucas.cardapioonline.Activity.PrincipalActivity;
import lucas.cardapioonline.Fragments.FragmentCardapio;

public class clPersistenciaDados_Firebase_SQLIte extends AsyncTask<String, String, Boolean> {

    String key_Empresa = "";
    private AtualizaDadosActivity atualizaDadosActivity;
    private PrincipalActivity principalActivity;
    private clFuncoesPersistencia funcoesPersistencia;
    private clGravaDadosFirebaseSQLite dadosFirebaseSQLite;
    private Activity activity;
    private clUtil util;
    private TextView txtAguarde;
    private SwipeRefreshLayout swipe;
    private FragmentCardapio fragmentCardapio;

    public clPersistenciaDados_Firebase_SQLIte(Activity a, String key_empresa,
                                               SwipeRefreshLayout swipe,
                                               FragmentCardapio fragmentCardapio){
        activity = a;
        this.key_Empresa = key_empresa;
        this.swipe = swipe;
        this.fragmentCardapio = fragmentCardapio;

        util = new clUtil(activity);
        dadosFirebaseSQLite = new clGravaDadosFirebaseSQLite(activity);
        funcoesPersistencia = new clFuncoesPersistencia(activity);
    }

    public clPersistenciaDados_Firebase_SQLIte(AtualizaDadosActivity a, TextView txtAguarde) {
        activity = a;
        atualizaDadosActivity = a;
        this.txtAguarde = txtAguarde;
        util = new clUtil(activity);
        dadosFirebaseSQLite = new clGravaDadosFirebaseSQLite(activity);
        funcoesPersistencia = new clFuncoesPersistencia(activity);
    }

    public clPersistenciaDados_Firebase_SQLIte(PrincipalActivity p, SwipeRefreshLayout swipe){
        activity = p;
        principalActivity = p;
        this.swipe = swipe;

        util = new clUtil(activity);
        dadosFirebaseSQLite = new clGravaDadosFirebaseSQLite(activity);
        funcoesPersistencia = new clFuncoesPersistencia(activity);
    }

    @Override
    protected Boolean doInBackground(String... strings) {

        for (int i = 0; i <= strings.length - 1; i++) {
            try {
                publishProgress(strings[i]);

                if (strings[i].equals("Empresa e Itens")) {
                    funcoesPersistencia.gravarDadosSQLite_Empresa_Itens();
                } else if (strings[i].equals("Itens")) {
                    funcoesPersistencia.gravarDadosSQLite_Itens(key_Empresa);
                } else {
                    funcoesPersistencia.gravarDadosSQLite_Firebase();
                }

                if (i == 0) {
                    while ((!clConstantes.passouEmpresa) && (!clConstantes.passouItens)) {
                        Thread.sleep(500);
                    }
                } else if (i == 1) {
                    while (!clConstantes.passouUsuario) {
                        Thread.sleep(500);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

        if (atualizaDadosActivity != null) {
            if ((clConstantes.passouEmpresa) && (clConstantes.passouItens) && (clConstantes.passouUsuario)) {
                clInfoAtualizacao infoAtualizacao = new clInfoAtualizacao();
                infoAtualizacao.setData_atualizacao(util.retornaDataAtual());
                infoAtualizacao.setHora_atualizacao(util.retornaHoraAtual());
                dadosFirebaseSQLite.gravaDadosInfoAtualizacao(infoAtualizacao);
            }

            funcoesPersistencia.mostrarLogin();
        } else if (fragmentCardapio != null) {
            swipe.setRefreshing(false);
            fragmentCardapio.setAtualizaDadosTela();
        } else if (principalActivity != null){
            swipe.setRefreshing(false);
            principalActivity.setAtualizaDadosTela();
        }

    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (txtAguarde != null) {
            if (values[0].equals("")) {
                txtAguarde.setText("Atualizando todas as tabelas ...");
            } else {
                txtAguarde.setText("Atualizando tabelas ... " + values[0]);
            }
        }
    }
}
