package lucas.cardapioonline.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import java.io.Serializable;
import java.util.List;

import lucas.cardapioonline.Classes.clEmpresa;
import lucas.cardapioonline.Fragments.FragmentCardapio;
import lucas.cardapioonline.Fragments.FragmentMenu_Principal;
import lucas.cardapioonline.R;

public class MenuActivity extends AppCompatActivity {

    public String NomeCompleto = "", GeneroUsuario = "", Acao = "";
    private clEmpresa EmpresaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        NomeCompleto = bundle.getString("NomeCompleto");
        GeneroUsuario = bundle.getString("Genero");
        Acao = bundle.getString("Acao");
        EmpresaSelecionada = (clEmpresa) bundle.getSerializable("ClasseEmpresa");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Acao.equals("Abrir_Menus")) {
            abreMenuPrincipal();
        } else if (Acao.equals("Cardapio")) {
            abreMenuCardapio();
        }

    }

    private void abreMenuPrincipal() {
        FragmentMenu_Principal fragment_MenuPrincipal = new FragmentMenu_Principal();
        Bundle bundle = new Bundle();
        bundle.putString("NomeCompleto", NomeCompleto);
        bundle.putString("Genero", GeneroUsuario);
        fragment_MenuPrincipal.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.activity_menu_entrada, R.anim.activity_principal_saida)
                .replace(R.id.nav_contentframe, fragment_MenuPrincipal, "FragPrincipal")
                .addToBackStack(null)
                .commit();
    }

    private void abreMenuCardapio() {
        FragmentCardapio fragmentCardapio = new FragmentCardapio();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ClasseEmpresa", EmpresaSelecionada);
        fragmentCardapio.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.activity_menu_entrada, R.anim.activity_principal_saida)
                .replace(R.id.nav_contentframe, fragmentCardapio, "FragCardapio")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        String tipoChamada = "";
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if (fragments.size() > 0) {
            Fragment visibleFragment = fragments.get(0);
            if (visibleFragment.getTag().equals("FragEditar") ||
                    visibleFragment.getTag().equals("FragMenuDadosPessoais") ||
                    visibleFragment.getTag().equals("FragMenuConfigApp") ||
                    visibleFragment.getTag().equals("FragMenuConfigApp_Rede")) {
                tipoChamada = "Fragment";
            } else {
                tipoChamada = "Activity";
            }

            switch (tipoChamada) {
                case "Activity": {
                    abrePrincipalActivity();
                    break;
                }
                case "Fragment": {
                    FragmentManager fm = this.getSupportFragmentManager();
                    if (fm.getBackStackEntryCount() > 0) {
                        fm.popBackStack();
                    }
                    break;
                }
            }
        } else {
            abrePrincipalActivity();
        }
    }

    private void abrePrincipalActivity() {
        Intent intent = new Intent(MenuActivity.this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.activity_principal_entrada, R.anim.activity_menu_saida);
    }
}
