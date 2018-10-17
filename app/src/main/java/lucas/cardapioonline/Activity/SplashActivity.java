package lucas.cardapioonline.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lucas.cardapioonline.Classes.clCardapio_Itens;
import lucas.cardapioonline.Classes.clEmpresa;
import lucas.cardapioonline.Classes.clGravaDadosFirebaseSQLite;
import lucas.cardapioonline.Classes.clUsuarios;
import lucas.cardapioonline.Classes.clUtil;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imgLoading = findViewById(R.id.imgLoading);
        txtAguarde = findViewById(R.id.txtAguarde);

        dadosFirebaseSQLite = new clGravaDadosFirebaseSQLite(this);
        reference = FirebaseDatabase.getInstance().getReference();
        util = new clUtil(SplashActivity.this);

        RequestOptions options = new RequestOptions()
                .fitCenter()
                .override(90, 90);

        Glide.with(this)
                .load(R.mipmap.loading)
                .apply(options)
                .into(imgLoading);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (util.isConected(this)) {
            persisisteDadosFirebase_SQLite firebase_sqLite = new persisisteDadosFirebase_SQLite();
            firebase_sqLite.execute("Empresa e Itens", "Usuários");
        } else {
            util.MensagemRapida("Sem conexão com a internet");
            mostrarLogin();
        }
    }

    private void mostrarLogin() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class persisisteDadosFirebase_SQLite extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            for (int i = 0; i <= strings.length - 1; i++) {
                try {
                    publishProgress(strings[i]);
                    gravarDadosSQLite_Firebase();

                    if (i == 0){
                        while ((!passouEmpresa) && (!passouItens)){
                            Thread.sleep(500);
                        }
                    } else if (i ==1) {
                        while (!passouUsuario){
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
}
