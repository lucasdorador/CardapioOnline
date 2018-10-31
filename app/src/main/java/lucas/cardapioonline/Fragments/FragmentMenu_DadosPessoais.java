package lucas.cardapioonline.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import lucas.cardapioonline.Activity.MainActivity;
import lucas.cardapioonline.Classes.clUsuarios;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.Controller.clUsuariosController;
import lucas.cardapioonline.DAO.ConfiguracaoFirebase;
import lucas.cardapioonline.Helper.Preferencias;
import lucas.cardapioonline.Helper.Preferencias_Usuario;
import lucas.cardapioonline.R;

public class FragmentMenu_DadosPessoais extends Fragment {

    private OnFragmentInteractionListener mListener;
    private LinearLayout linearLayout_MenuEditarPerfil, linearLayout_RetornarMenuConfiguracoes,
            linearLayout_MenuExcluirPreferencias;

    private FirebaseAuth autenticacao;
    private clUtil util;

    public FragmentMenu_DadosPessoais() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_menu_dados_pessoais, container, false);

        linearLayout_MenuEditarPerfil = view.findViewById(R.id.linearLayout_MenuEditarPerfil);
        linearLayout_RetornarMenuConfiguracoes = view.findViewById(R.id.linearLayout_RetornarMenuConfiguracoes);
        linearLayout_MenuExcluirPreferencias = view.findViewById(R.id.linearLayout_MenuExcluirPreferencias);
        autenticacao = FirebaseAuth.getInstance();
        util = new clUtil(getActivity());

        linearLayout_RetornarMenuConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        linearLayout_MenuEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarPerfilUsuario();
            }
        });

        linearLayout_MenuExcluirPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferencias preferencias = new Preferencias(getActivity(), "app.InternetDadosMoveis");
                preferencias.limparPreferencias();

                Preferencias_Usuario preferencias_usuario = new Preferencias_Usuario(getActivity());
                preferencias_usuario.limparPreferencias();

                util.MensagemRapida("Dados excluídos com sucesso!");
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

    private void editarPerfilUsuario() {
        String emailUsuarioLogado = autenticacao.getCurrentUser().getEmail();
        clUsuariosController usuariosController = new clUsuariosController(getContext());
        clUsuarios usuarios = usuariosController.retornaUsuarioCompleto(emailUsuarioLogado);

        final Bundle bundle = new Bundle();

        bundle.putString("origem", "editarUsuario");
        bundle.putString("email", usuarios.getEmail());
        bundle.putString("nome", usuarios.getNome());
        bundle.putString("idade", usuarios.getIdade());
        bundle.putString("keyusuario", usuarios.getKeyUsuario());
        bundle.putString("tipoUsuario", usuarios.getTipoUsuario());
        bundle.putString("genero", usuarios.getGenero());
        bundle.putString("endereco", usuarios.getEndereco());
        bundle.putString("numero", usuarios.getNumero());
        bundle.putString("bairro", usuarios.getBairro());
        bundle.putString("celular", usuarios.getCelular());
        bundle.putString("uriFotoPerfil", usuarios.getUriFotoPerfil());

        FragmentEditarPerfil fragment_EditarPerfil = new FragmentEditarPerfil();
        fragment_EditarPerfil.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.activity_menu_entrada, R.anim.activity_principal_saida);
        transaction.replace(R.id.nav_contentframe, fragment_EditarPerfil, "FragEditar");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
