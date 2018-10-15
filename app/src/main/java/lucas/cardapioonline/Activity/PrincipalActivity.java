package lucas.cardapioonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lucas.cardapioonline.Adapter.EmpresasAdapter;
import lucas.cardapioonline.Classes.clConstantes;
import lucas.cardapioonline.Classes.clEmpresa;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.DAO.ConfiguracaoFirebase;
import lucas.cardapioonline.R;

public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private DatabaseReference reference;
    private ImageView imgPrincipalFoto;
    private TextView txtSaudacaoInicial;
    private String NomeCompleto = "", GeneroUsuario = "";
    private clUtil util;
    private RecyclerView recycleViewEmpresas;
    private LinearLayoutManager mLayoutManagerTodosProdutos;
    private EmpresasAdapter adapter;
    private List<clEmpresa> empresas;
    private clEmpresa todasEmpresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        autenticacao = FirebaseAuth.getInstance();
        reference = ConfiguracaoFirebase.getReferenciaFirebase();
        imgPrincipalFoto = findViewById(R.id.imgPrincipalFoto);
        txtSaudacaoInicial = findViewById(R.id.txtSaudacaoInicial);
        util = new clUtil(PrincipalActivity.this);

        recycleViewEmpresas = findViewById(R.id.recycleViewEmpresas);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        preencheInfoUsuarioLogado();

        carregarTodasEmpresas();

        imgPrincipalFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreActivityMenus();
            }
        });
    }

    private void abreActivityMenus() {
        Intent intent = new Intent(PrincipalActivity.this, MenuActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Acao", "Abrir_Menus");
        bundle.putString("NomeCompleto", NomeCompleto);
        bundle.putString("Genero", GeneroUsuario);
        intent.putExtras(bundle);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.activity_menu_entrada, R.anim.activity_principal_saida);
        ActivityCompat.startActivity(PrincipalActivity.this, intent, optionsCompat.toBundle());
        finish();
    }

    private void carregarTodasEmpresas() {
        recycleViewEmpresas.setHasFixedSize(true);
        mLayoutManagerTodosProdutos = new LinearLayoutManager(PrincipalActivity.this, LinearLayoutManager.VERTICAL, false);
        recycleViewEmpresas.setLayoutManager(mLayoutManagerTodosProdutos);
        retornaTodasEmpresas();
        adapter = new EmpresasAdapter(empresas, PrincipalActivity.this);
        recycleViewEmpresas.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void retornaTodasEmpresas(){
        empresas = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();

        reference.child("empresa").orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    todasEmpresas = postSnapShot.getValue(clEmpresa.class);
                    empresas.add(todasEmpresas);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_principal_entrada, R.anim.activity_menu_saida);
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
