package lucas.cardapioonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.Gravity;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import lucas.cardapioonline.Classes.clConstantes;
import lucas.cardapioonline.Classes.clLinearLayout;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.Classes.clpropertyLinearLayout;
import lucas.cardapioonline.DAO.ConfiguracaoFirebase;
import lucas.cardapioonline.R;

public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private DatabaseReference reference;
    private ImageView imgPrincipalFoto;
    private TextView txtSaudacaoInicial;
    private String NomeCompleto = "", GeneroUsuario = "";
    private LinearLayout linearLayout_Petiscaria;
    private LinearLayout linearLayout_TodasEmpresas;
    private clUtil util;
    private clLinearLayout clLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        autenticacao = FirebaseAuth.getInstance();
        reference = ConfiguracaoFirebase.getReferenciaFirebase();
        imgPrincipalFoto = findViewById(R.id.imgPrincipalFoto);
        txtSaudacaoInicial = findViewById(R.id.txtSaudacaoInicial);
        linearLayout_Petiscaria = findViewById(R.id.linearLayout_Petiscaria);
        clLinearLayout = new clLinearLayout(PrincipalActivity.this);
        util = new clUtil(PrincipalActivity.this);

        criarLayoutsEmpresas_Firebase();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        preencheInfoUsuarioLogado();

        imgPrincipalFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreActivityMenus("Abrir_Menus");
            }
        });

        linearLayout_Petiscaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreActivityMenus("Cardapio");
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_principal_entrada, R.anim.activity_menu_saida);
    }

    private void criarLayoutsEmpresas_Firebase() {
        reference.child("empresa").orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    CriacaoLayoutEmpresas(postSnapShot.child("nome").getValue().toString(),
                            postSnapShot.child("resumo").getValue().toString(),
                            postSnapShot.child("url_logo").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void abreActivityMenus(String AcaoAbertura) {
        Intent intent = new Intent(PrincipalActivity.this, MenuActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Acao", AcaoAbertura);
        bundle.putString("Empresa", "Petiscaria");
        bundle.putString("NomeCompleto", NomeCompleto);
        bundle.putString("Genero", GeneroUsuario);
        intent.putExtras(bundle);
        //startActivity(intent);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.activity_menu_entrada, R.anim.activity_principal_saida);
        ActivityCompat.startActivity(PrincipalActivity.this, intent, optionsCompat.toBundle());
        finish();
    }

    private void CriacaoLayoutEmpresas(String psNomeEmpresa, String psResumoEmpresa, String url_logo) {
        linearLayout_TodasEmpresas = findViewById(R.id.linearLayout_TodasEmpresas);

        //View view = getLayoutInflater().inflate(R.layout.layout_view_menus, null);

        LinearLayout linearLayout_Empresa = getLinearLayoutEmpresa();
        LinearLayout linearLayout_Componentes = getLinearLayoutComponentes();
        LinearLayout linearLayout_TextViews = getLinearLayoutTextViews();

        linearLayout_TodasEmpresas.addView(linearLayout_Empresa);

        linearLayout_Empresa.addView(linearLayout_Componentes);
        ImageView imgFotoLogoEmpresas = new ImageView(this);
        LinearLayout.LayoutParams layoutParams_ImageView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (url_logo.equals("")) {
            Picasso.get().load(R.mipmap.logo_pizzaria_124).resize(220, 220).centerCrop().into(imgFotoLogoEmpresas);
        } else {
            Picasso.get().load(url_logo).resize(220, 220).centerCrop().into(imgFotoLogoEmpresas);
        }

        linearLayout_Componentes.addView(imgFotoLogoEmpresas, layoutParams_ImageView);
        linearLayout_Componentes.addView(linearLayout_TextViews);

        TextView txtNomeEmpresas = (TextView) getLayoutInflater().inflate(R.layout.layout_textview_nome_empresa, null);
        LinearLayout.LayoutParams layoutParams_TextView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams_TextView.leftMargin = util.IntToDP(10);
        txtNomeEmpresas.setText(psNomeEmpresa);

        TextView txtResumoEmpresas = (TextView) getLayoutInflater().inflate(R.layout.layout_textview_resumo_empresa, null);
        txtResumoEmpresas.setText(psResumoEmpresa);

        linearLayout_TextViews.addView(txtNomeEmpresas, layoutParams_TextView);
        linearLayout_TextViews.addView(txtResumoEmpresas, layoutParams_TextView);

        //linearLayout_TodasEmpresas.addView(view);
    }

    private LinearLayout getLinearLayoutTextViews() {
        clpropertyLinearLayout layoutPropery = new clpropertyLinearLayout();
        layoutPropery.setLayout_width(ViewGroup.LayoutParams.MATCH_PARENT);
        layoutPropery.setLayout_height(ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutPropery.setOrientation(LinearLayout.VERTICAL);
        layoutPropery.setGravity(Gravity.CENTER);
        return clLinearLayout.createLinearLayout(layoutPropery);
    }

    private LinearLayout getLinearLayoutComponentes() {
        clpropertyLinearLayout layoutPropery = new clpropertyLinearLayout();
        layoutPropery.setLayout_width(ViewGroup.LayoutParams.MATCH_PARENT);
        layoutPropery.setLayout_height(ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutPropery.setOrientation(LinearLayout.HORIZONTAL);
        layoutPropery.setMargimBottom(5);
        layoutPropery.setMargimTop(5);
        layoutPropery.setMargimLeft(5);
        layoutPropery.setMargimRight(5);
        return clLinearLayout.createLinearLayout(layoutPropery);
    }

    private LinearLayout getLinearLayoutEmpresa() {
        clpropertyLinearLayout layoutPropery = new clpropertyLinearLayout();
        layoutPropery.setGravity(Gravity.CENTER + Gravity.TOP);
        layoutPropery.setLayout_width(ViewGroup.LayoutParams.MATCH_PARENT);
        layoutPropery.setLayout_height(ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutPropery.setOrientation(LinearLayout.VERTICAL);
        return clLinearLayout.createLinearLayout(layoutPropery);
    }

    private void preencheInfoUsuarioLogado() {
        String emailUsuarioLogado = autenticacao.getCurrentUser().getEmail().toString();

        reference.child("usuarios").orderByChild("email").equalTo(emailUsuarioLogado.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    String nomeUsuarioLogado = postSnapShot.child("nome").getValue().toString();
                    GeneroUsuario = postSnapShot.child("genero").getValue().toString();
                    NomeCompleto = nomeUsuarioLogado;

                    if (GeneroUsuario.equals("Masculino")) {
                        Picasso.get().load(R.mipmap.avatar_masc_124).resize(clConstantes.TamanhoFotoPerfil_Width, clConstantes.TamanhoFotoPerfil_Height).centerCrop().into(imgPrincipalFoto);
                    } else if (GeneroUsuario.equals("Feminino")) {
                        Picasso.get().load(R.mipmap.avatar_fem_124).resize(clConstantes.TamanhoFotoPerfil_Width, clConstantes.TamanhoFotoPerfil_Height).centerCrop().into(imgPrincipalFoto);
                    } else {
                        Picasso.get().load(R.mipmap.avatar_user_124).resize(clConstantes.TamanhoFotoPerfil_Width, clConstantes.TamanhoFotoPerfil_Height).centerCrop().into(imgPrincipalFoto);
                    }

                    if (nomeUsuarioLogado.indexOf(" ") > 0) {
                        txtSaudacaoInicial.setText(nomeUsuarioLogado.substring(0, nomeUsuarioLogado.indexOf(" ")));
                    } else {
                        txtSaudacaoInicial.setText(nomeUsuarioLogado);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
