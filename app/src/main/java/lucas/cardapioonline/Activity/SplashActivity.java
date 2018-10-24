package lucas.cardapioonline.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lucas.cardapioonline.Classes.clCardapio_Itens;
import lucas.cardapioonline.Classes.clConfiguracoes;
import lucas.cardapioonline.Classes.clEmpresa;
import lucas.cardapioonline.Classes.clGravaDadosFirebaseSQLite;
import lucas.cardapioonline.Classes.clUsuarios;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.Helper.Preferencias;
import lucas.cardapioonline.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView imgLoading;
    private TextView txtAguarde;
    private DatabaseReference reference, referenceItens;

    boolean passouEmpresa = false;
    boolean passouUsuario = false;
    boolean passouItens = false;

    private clEmpresa todasEmpresas;
    private clUsuarios todosUsuarios;
    private clCardapio_Itens todosItens;
    private clGravaDadosFirebaseSQLite dadosFirebaseSQLite;
    private clUtil util;
    private clConfiguracoes configuracoes;
    private String mensagemConexao = "";
    private Preferencias preferencias;
    private boolean resultPreferencias = false;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imgLoading = findViewById(R.id.imgLoading);
        txtAguarde = findViewById(R.id.txtAguarde);

        dadosFirebaseSQLite = new clGravaDadosFirebaseSQLite(this);
        reference = FirebaseDatabase.getInstance().getReference();
        util = new clUtil(SplashActivity.this);
        preferencias = new Preferencias(this, "app.InternetDadosMoveis");
        dialog = new Dialog(this);

        RequestOptions options = new RequestOptions()
                .fitCenter()
                .override(90, 90);

        Glide.with(this)
                .load(R.mipmap.loading)
                .apply(options)
                .into(imgLoading);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        configuracoes = clConfiguracoes.getInstance(this);

        if (usuarioLogado()) {
            if (util.isConected(this)) {
                if ((validaAtualizacao_Wifi_DadosMoveis(configuracoes)) && resultPreferencias) {
                   persisisteDadosFirebase_SQLite firebase_sqLite = new persisisteDadosFirebase_SQLite();
                   firebase_sqLite.execute("Empresa e Itens", "Usuários");
                } else if (!resultPreferencias) {
                    finish();
                } else {
                    util.MensagemRapida(mensagemConexao);
                    mostrarLogin();
                }
            } else {
                util.MensagemRapida("Sem conexão com a internet");
                mostrarLogin();
            }
        } else

        {
            mostrarLogin();
        }

    }

    private void mostrarLogin() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validaAtualizacao_Wifi_DadosMoveis(clConfiguracoes c) {
        boolean resultado = true;
        resultPreferencias = true;
        mensagemConexao = "";

        if (!c.dadosWifi() && !c.dadosMoveis()) {
            resultado = false;
            mensagemConexao = "Nenhuma opção para atualização está configurada, verifique nas opção" +
                    "de configuração do App no menu configurações!";
        } else if ((c.dadosWifi()) && (!util.conexaoWifi(this))) {
            resultado = false;
            mensagemConexao = "Atualização somente por Wifi. Conecte-se para poder atualizar";
        } else if ((c.dadosMoveis()) && (!util.conexaoDadosMoveis(this))) {
            resultado = false;
            mensagemConexao = "Atualização somente por Dados Móveis. Conecte-se para poder atualizar";
        } else if ((c.dadosMoveis()) && (util.conexaoDadosMoveis(this))) {
            String _preferencia = preferencias.getSecaoPreferencias("DadosMoveis");
            if ((_preferencia.equals("NAO")) || _preferencia.equals("LEMBRAR") || _preferencia.equals("")) {
                resultado = true;
                mensagemConexao = "Atualização por Dados Móveis poderá ser cobrado de sua franquia de internet," +
                        "deseja continuar?";
                abrirDialogPersonalizado3Botoes(mensagemConexao);
            } else {
                resultado = true;
                mensagemConexao = "";
            }
        }

        return resultado;
    }

    private class persisisteDadosFirebase_SQLite extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            for (int i = 0; i <= strings.length - 1; i++) {
                try {
                    publishProgress(strings[i]);
                    gravarDadosSQLite_Firebase();

                    if (i == 0) {
                        while ((!passouEmpresa) && (!passouItens)) {
                            Thread.sleep(500);
                        }
                    } else if (i == 1) {
                        while (!passouUsuario) {
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
            mostrarLogin();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            txtAguarde.setText("Atualizando tabelas ... " + values[0]);
        }

    }

    private void gravarDadosSQLite_Firebase() {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("empresa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    todasEmpresas = postSnapShot.getValue(clEmpresa.class);
                    dadosFirebaseSQLite.gravaDadosEmpresa(todasEmpresas);

                    referenceItens = FirebaseDatabase.getInstance().getReference();
                    referenceItens.child("cardapio_itens")
                            .child(todasEmpresas.getKey_empresa())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapShotItens : dataSnapshot.getChildren()) {
                                        todosItens = postSnapShotItens.getValue(clCardapio_Itens.class);
                                        dadosFirebaseSQLite.gravaDadosCardapioItens(todosItens);
                                        passouItens = true;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                    passouEmpresa = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    todosUsuarios = postSnapShot.getValue(clUsuarios.class);
                    dadosFirebaseSQLite.gravaDadosUsuarios(todosUsuarios);
                    passouUsuario = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resultPreferencias = false;
                    preferencias.salvarPreferencias("NAO", "DadosMoveis");
                    dialog.dismiss();
                }
            });

            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resultPreferencias = true;
                    preferencias.salvarPreferencias("LEMBRAR", "DadosMoveis");
                    dialog.dismiss();
                }
            });

            dialog.show();

        } else {
            resultPreferencias = true;
        }
    }

}
