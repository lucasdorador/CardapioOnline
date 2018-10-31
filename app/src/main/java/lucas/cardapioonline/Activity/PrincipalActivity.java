package lucas.cardapioonline.Activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import lucas.cardapioonline.Adapter.EmpresasAdapter;
import lucas.cardapioonline.Classes.clConstantes;
import lucas.cardapioonline.Classes.clEmpresa;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.Controller.clEmpresaController;
import lucas.cardapioonline.Controller.clUsuariosController;
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
    private EmpresasAdapter adapterSQLite;
    private List<clEmpresa> empresas;
    private clEmpresaController empresaController;
    private clUsuariosController usuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        autenticacao = FirebaseAuth.getInstance();
        reference = ConfiguracaoFirebase.getReferenciaFirebase();
        imgPrincipalFoto = findViewById(R.id.imgPrincipalFoto);
        txtSaudacaoInicial = findViewById(R.id.txtSaudacaoInicial);
        util = new clUtil(PrincipalActivity.this);
        empresaController = new clEmpresaController(this);
        usuarioController = new clUsuariosController(this);

        recycleViewEmpresas = findViewById(R.id.recycleViewEmpresas);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        abreActivityAtualizacao();

        imgPrincipalFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preencheInfoUsuarioLogado_SQLite();
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

    private void abreActivityAtualizacao() {
        Intent intent = new Intent(PrincipalActivity.this, AtualizaDadosActivity.class);
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((resultCode == RESULT_OK) && (requestCode == 123)) {
            //preencheInfoUsuarioLogado_SQLite();
            carregarTodasEmpresas();
        }
    }

    private void carregarTodasEmpresas() {
        recycleViewEmpresas.setHasFixedSize(true);
        mLayoutManagerTodosProdutos = new LinearLayoutManager(PrincipalActivity.this, LinearLayoutManager.VERTICAL, false);
        recycleViewEmpresas.setLayoutManager(mLayoutManagerTodosProdutos);
        retornaTodasEmpresas();
        adapterSQLite = new EmpresasAdapter(empresas, PrincipalActivity.this);
        recycleViewEmpresas.setAdapter(adapterSQLite);
        adapterSQLite.notifyDataSetChanged();
    }

    private void retornaTodasEmpresas() {
        empresas = empresaController.retornaListaClasseEmpresaSQLite();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_principal_entrada, R.anim.activity_menu_saida);
    }

    private void preencheInfoUsuarioLogado_SQLite() {
        String emailUsuarioLogado = autenticacao.getCurrentUser().getEmail().toString();
        String[] infoUsuario = usuarioController.retornaCamposUsuarioByEmail(new String[]{"nome", "genero"}, emailUsuarioLogado);

        if (infoUsuario.length != 0) {
            String nomeUsuarioLogado = infoUsuario[0];
            GeneroUsuario = infoUsuario[1];
            NomeCompleto = nomeUsuarioLogado;

            if (GeneroUsuario.equals("Masculino")) {
                util.carregaImagem_ImageView(R.mipmap.avatar_masc_124, imgPrincipalFoto, clConstantes.TamanhoFotoPerfil_Width, clConstantes.TamanhoFotoPerfil_Height);
            } else if (GeneroUsuario.equals("Feminino")) {
                util.carregaImagem_ImageView(R.mipmap.avatar_fem_124, imgPrincipalFoto, clConstantes.TamanhoFotoPerfil_Width, clConstantes.TamanhoFotoPerfil_Height);
            } else {
                util.carregaImagem_ImageView(R.mipmap.avatar_user_124, imgPrincipalFoto, clConstantes.TamanhoFotoPerfil_Width, clConstantes.TamanhoFotoPerfil_Height);
            }

            if (nomeUsuarioLogado.indexOf(" ") > 0) {
                txtSaudacaoInicial.setText(nomeUsuarioLogado.substring(0, nomeUsuarioLogado.indexOf(" ")));
            } else {
                txtSaudacaoInicial.setText(nomeUsuarioLogado);
            }
        }

    }
}
