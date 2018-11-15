package lucas.cardapioonline.Activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lucas.cardapioonline.Classes.clConfiguracoes;
import lucas.cardapioonline.Classes.clFuncoesPersistencia;
import lucas.cardapioonline.Classes.clGravaDadosFirebaseSQLite;
import lucas.cardapioonline.Classes.clPersistenciaDados_Firebase_SQLIte;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.Classes.clRegrasPersistencia;
import lucas.cardapioonline.Helper.Preferencias;
import lucas.cardapioonline.R;

public class AtualizaDadosActivity extends AppCompatActivity {

    private ImageView imgLoading;
    private TextView txtAguarde;

    private clPersistenciaDados_Firebase_SQLIte firebase_sqLite;
    private clGravaDadosFirebaseSQLite dadosFirebaseSQLite;
    private clUtil util;
    private clConfiguracoes configuracoes;
    private String mensagemConexao = "", PerguntaConexao = "";
    private Preferencias preferencias;
    private boolean resultPreferencias = false, vlBDadosmoveis = false;
    private Dialog dialog;
    private clRegrasPersistencia clRegrasPersistencia;
    private clFuncoesPersistencia funcoesPersistencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_dados);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imgLoading = findViewById(R.id.imgLoading);
        txtAguarde = findViewById(R.id.txtAguarde);

        dadosFirebaseSQLite = new clGravaDadosFirebaseSQLite(this);
        util = new clUtil(AtualizaDadosActivity.this);
        preferencias = new Preferencias(this, "app.InternetDadosMoveis");
        dialog = new Dialog(this);
        clRegrasPersistencia = new clRegrasPersistencia(AtualizaDadosActivity.this, "AtualizaDados");
        funcoesPersistencia = new clFuncoesPersistencia(AtualizaDadosActivity.this);

        RequestOptions options = new RequestOptions()
                .fitCenter()
                .override(90, 90);

        Glide.with(this)
                .load(R.mipmap.loading)
                .apply(options)
                .into(imgLoading);

        configuracoes = clConfiguracoes.getInstance(this);

        if (usuarioLogado()) {
            if (util.isConected(this)) {
                if ((clRegrasPersistencia.validaAtualizacao_Wifi_DadosMoveis(configuracoes)) && resultPreferencias) {
                    firebase_sqLite = new clPersistenciaDados_Firebase_SQLIte(AtualizaDadosActivity.this, txtAguarde);
                    firebase_sqLite.execute("");
                } else if (vlBDadosmoveis) {

                    if (!PerguntaConexao.equals("")) {
                        abrirDialogPersonalizado3Botoes(PerguntaConexao);
                    }

                } else if (!vlBDadosmoveis) {

                    if (!mensagemConexao.equals("")) {
                        util.MensagemRapida(mensagemConexao);
                    }
                    //Criar um pergunta, se o usuário estiver conectado pelo Wifi, mas
                    //no cadastro estiver marcado para atualizar somente pelos dados móveis
                    //se ele deseja atualizar desse vez pelo Wifi.
                    //persisisteDadosFirebase_SQLite firebase_sqLite = new persisisteDadosFirebase_SQLite();
                    //firebase_sqLite.execute("Empresa e Itens", "Usuários");
                    //mostrarLogin();
                }
            } else {
                util.MensagemRapida("Sem conexão com a internet");
                String res = funcoesPersistencia.mostrarLogin();
                if (!res.equals("")) {
                    abrirDialogPersonalizado1Botoes(res);
                }
            }
        } else {
            String res = funcoesPersistencia.mostrarLogin();
            if (!res.equals("")) {
                abrirDialogPersonalizado1Botoes(res);
            }
        }
    }

    public void setDadosTela(boolean resultPreferencias, boolean vlBDadosmoveis, String mensagemConexao, String PerguntaConexao) {
        this.resultPreferencias = resultPreferencias;
        this.vlBDadosmoveis = vlBDadosmoveis;
        this.mensagemConexao = mensagemConexao;
        this.PerguntaConexao = PerguntaConexao;
    }

    public Boolean usuarioLogado() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return (user != null);
    }

    private void abrirDialogPersonalizado3Botoes(String mensagem) {
        if (!mensagem.equals("")) {
            dialog.setContentView(R.layout.alert_personalizado_3botoes);
            dialog.setCancelable(false);

            final TextView txtMensagem;
            final BootstrapButton btn1, btn2, btn3;

            txtMensagem = dialog.findViewById(R.id.txtTexto);
            txtMensagem.setText(mensagem);

            btn1 = dialog.findViewById(R.id.btn1);
            btn1.setText("SIM");
            btn1.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);

            btn2 = dialog.findViewById(R.id.btn2);
            btn2.setText("NÃO");
            btn2.setBootstrapBrand(DefaultBootstrapBrand.DANGER);

            btn3 = dialog.findViewById(R.id.btn3);
            btn3.setText("LEMBRAR DEPOIS");
            btn3.setBootstrapBrand(DefaultBootstrapBrand.INFO);

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resultPreferencias = true;
                    preferencias.salvarPreferencias("SIM", "DadosMoveis");
                    dialog.dismiss();
                    firebase_sqLite = new clPersistenciaDados_Firebase_SQLIte(AtualizaDadosActivity.this, txtAguarde);
                    firebase_sqLite.execute("Empresa e Itens", "Usuários");
                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resultPreferencias = false;
                    preferencias.salvarPreferencias("NAO", "DadosMoveis");
                    dialog.dismiss();
                    finish();
                }
            });

            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resultPreferencias = true;
                    preferencias.salvarPreferencias("LEMBRAR", "DadosMoveis");
                    dialog.dismiss();
                    firebase_sqLite = new clPersistenciaDados_Firebase_SQLIte(AtualizaDadosActivity.this, txtAguarde);
                    firebase_sqLite.execute("Empresa e Itens", "Usuários");
                }
            });

            dialog.show();

        } else {
            resultPreferencias = true;
        }
    }

    private void abrirDialogPersonalizado1Botoes(String mensagem) {
        if (!mensagem.equals("")) {
            dialog.setContentView(R.layout.alert_personalizado_3botoes);
            dialog.setCancelable(false);

            final TextView txtMensagem;
            final BootstrapButton btn1, btn2, btn3;

            txtMensagem = dialog.findViewById(R.id.txtTexto);
            txtMensagem.setText(mensagem);

            btn1 = dialog.findViewById(R.id.btn1);
            btn1.setVisibility(View.INVISIBLE);

            btn2 = dialog.findViewById(R.id.btn2);
            btn2.setVisibility(View.INVISIBLE);

            btn3 = dialog.findViewById(R.id.btn3);
            btn3.setText("OK");
            btn3.setBootstrapBrand(DefaultBootstrapBrand.INFO);

            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            dialog.show();
        }
    }

}
