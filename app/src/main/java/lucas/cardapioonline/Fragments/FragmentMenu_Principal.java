package lucas.cardapioonline.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import lucas.cardapioonline.Activity.MainActivity;
import lucas.cardapioonline.Classes.clConstantes;
import lucas.cardapioonline.R;

public class FragmentMenu_Principal extends Fragment {

    private OnFragmentInteractionListener mListener;
    private LinearLayout linearLayout_RetornarMenuPrincipal,
            linearLayout_MenuConfiguracoes, linearLayout_MenuLogout,
            linearLayout_MenuConfigApp;
    private TextView txtNomeCompletoMenuPrincipal;
    private ImageView imgFotoUsuario_Menu;
    private FirebaseAuth autenticacao;

    public FragmentMenu_Principal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_menu_principal, container, false);

        linearLayout_RetornarMenuPrincipal = view.findViewById(R.id.linearLayout_RetornarMenuPrincipal);
        linearLayout_MenuConfiguracoes = view.findViewById(R.id.linearLayout_MenuConfiguracoes);
        linearLayout_MenuLogout = view.findViewById(R.id.linearLayout_MenuLogout);
        linearLayout_MenuConfigApp = view.findViewById(R.id.linearLayout_MenuConfigApp);
        txtNomeCompletoMenuPrincipal = view.findViewById(R.id.txtNomeCompletoMenuPrincipal);
        imgFotoUsuario_Menu = view.findViewById(R.id.imgFotoUsuario_Menu);
        autenticacao = FirebaseAuth.getInstance();

        Bundle bundle = this.getArguments();

        txtNomeCompletoMenuPrincipal.setText(bundle.getString("NomeCompleto"));

        String GeneroUsuario = bundle.getString("Genero");

        if (GeneroUsuario.equals("Masculino")) {
            Picasso.get().load(R.mipmap.avatar_masc_124).resize(clConstantes.TamanhoFotoPerfil_Width, clConstantes.TamanhoFotoPerfil_Height).centerCrop().into(imgFotoUsuario_Menu);
        } else if (GeneroUsuario.equals("Feminino")) {
            Picasso.get().load(R.mipmap.avatar_fem_124).resize(clConstantes.TamanhoFotoPerfil_Width, clConstantes.TamanhoFotoPerfil_Height).centerCrop().into(imgFotoUsuario_Menu);
        } else {
            Picasso.get().load(R.mipmap.avatar_user_124).resize(clConstantes.TamanhoFotoPerfil_Width, clConstantes.TamanhoFotoPerfil_Height).centerCrop().into(imgFotoUsuario_Menu);
        }

        linearLayout_RetornarMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        linearLayout_MenuConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.activity_menu_entrada, R.anim.activity_principal_saida);
                transaction.replace(R.id.nav_contentframe, new FragmentMenu_DadosPessoais(), "FragMenuDadosPessoais");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        linearLayout_MenuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioDesconectar();
            }
        });

        linearLayout_MenuConfigApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.activity_menu_entrada, R.anim.activity_principal_saida);
                transaction.replace(R.id.nav_contentframe, new FragmentMenu_ConfigApp(), "FragMenuConfigApp");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void usuarioDesconectar() {
        autenticacao.signOut();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
