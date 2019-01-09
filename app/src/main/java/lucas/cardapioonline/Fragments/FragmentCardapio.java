package lucas.cardapioonline.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;
import java.util.List;
import lucas.cardapioonline.Adapter.CardapioAdapter;
import lucas.cardapioonline.Adapter.GruposAdapter;
import lucas.cardapioonline.Classes.clCardapio_Itens;
import lucas.cardapioonline.Classes.clEmpresa;
import lucas.cardapioonline.Classes.clFuncoesPersistencia;
import lucas.cardapioonline.Classes.clGruposData_SQLite;
import lucas.cardapioonline.Classes.clPersistenciaDados_Firebase_SQLIte;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.Controller.clCardapioItensController;
import lucas.cardapioonline.R;

public class FragmentCardapio extends Fragment {

    private OnFragmentInteractionListener mListener;
    private clFuncoesPersistencia funcoesPersistencia;
    private LinearLayout linearLayout_RetornarMenuPrincipal;
    private RecyclerView recycleViewCardapio;
    private LinearLayoutManager mLayoutManagerTodosProdutos;
    private GruposAdapter adapter_SQLite;
    private List<clCardapio_Itens> cardapios;
    private clEmpresa EmpresaSelecionada;
    protected TextView txtCardapioNome, txtCardapioEndereco, txtCardapioTelefone,
            txtCardapioHorarioFuncionamento;
    protected ImageView imgCardapioLogo;
    private clCardapioItensController itensController;
    private clUtil util;
    private SwipeRefreshLayout swipeCardapio;
    private clPersistenciaDados_Firebase_SQLIte firebase_sqLite;

    public FragmentCardapio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_cardapio, container, false);

        Bundle bundle = this.getArguments();
        EmpresaSelecionada = (clEmpresa) bundle.getSerializable("ClasseEmpresa");

        //Dados da Empresa
        txtCardapioNome = view.findViewById(R.id.txtCardapioNome);
        txtCardapioEndereco = view.findViewById(R.id.txtCardapioEndereco);
        txtCardapioTelefone = view.findViewById(R.id.txtCardapioTelefone);
        txtCardapioHorarioFuncionamento = view.findViewById(R.id.txtCardapioHorarioFuncionamento);
        imgCardapioLogo = view.findViewById(R.id.imgCardapioLogo);
        swipeCardapio = view.findViewById(R.id.swipeCardapio);
        util = new clUtil(getActivity());
        funcoesPersistencia = new clFuncoesPersistencia(getActivity());

        linearLayout_RetornarMenuPrincipal = view.findViewById(R.id.linearLayout_RetornarMenuPrincipal);
        recycleViewCardapio = view.findViewById(R.id.recycleViewCardapio);

        RecyclerView.ItemAnimator animator = recycleViewCardapio.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        itensController = new clCardapioItensController(getActivity());

        carregaDadosEmpresa(EmpresaSelecionada);
        carregaListaProdutos_Grupos(EmpresaSelecionada.getKey_empresa());

        //carregarTodosProdutos(EmpresaSelecionada.getKey_empresa());

        linearLayout_RetornarMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        swipeCardapio.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                atualizaTodosItens(EmpresaSelecionada.getKey_empresa());
            }
        });

        txtCardapioEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        txtCardapioTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:" + util.removeCaracteres(txtCardapioTelefone.getText().toString(),
                        new String[]{"(", ")", "-", " "}));

                Intent intent = new Intent(Intent.ACTION_DIAL, uri);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }

                startActivity(intent);
            }
        });

        swipeCardapio.setColorSchemeResources(R.color.colorPrimary);
        return view;
    }

    public void setAtualizaDadosTela(){
        //carregarTodosProdutos(EmpresaSelecionada.getKey_empresa());
        carregaListaProdutos_Grupos(EmpresaSelecionada.getKey_empresa());
        util.MensagemRapida("Dados atualizados com sucesso!");
    }

    private void carregaListaProdutos_Grupos(String key_Empresa){
        clGruposData_SQLite clGruposData_sqLite = new clGruposData_SQLite(getContext(), key_Empresa);
        adapter_SQLite = new GruposAdapter(clGruposData_sqLite.carregaGrupos());
        mLayoutManagerTodosProdutos = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycleViewCardapio.setLayoutManager(mLayoutManagerTodosProdutos);
        recycleViewCardapio.setAdapter(adapter_SQLite);
    }

    private void atualizaTodosItens(String key_Empresa) {
        firebase_sqLite = new clPersistenciaDados_Firebase_SQLIte(getActivity(), key_Empresa, swipeCardapio, this);
        firebase_sqLite.execute("Itens e Grupos");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((resultCode == getActivity().RESULT_OK) && (requestCode == 123)) {
            swipeCardapio.setRefreshing(false);
        }
    }

    private void carregaDadosEmpresa(clEmpresa empresa) {
        txtCardapioNome.setText(empresa.getNome());
        txtCardapioEndereco.setText(empresa.getLogradouro() + ", " + empresa.getNumero() + " - " + empresa.getBairro());
        txtCardapioTelefone.setText(empresa.getTelefone());
        txtCardapioHorarioFuncionamento.setText(empresa.getHorario_funcionamento());

        try {
            byte[] byteImagem = util.lerImagemArmazenamentoInterno(getActivity(), empresa.getKey_empresa());
            Bitmap bmp = BitmapFactory.decodeByteArray(byteImagem, 0, byteImagem.length);
            imgCardapioLogo.setImageBitmap(bmp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void retornaCardapioCompleto(String Key_Empresa) {
        cardapios = itensController.retornaListaClasseItensCardapio(Key_Empresa);
    }

    private void carregarTodosProdutos(String Key_Empresa) {
        recycleViewCardapio.setHasFixedSize(true);
        mLayoutManagerTodosProdutos = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycleViewCardapio.setLayoutManager(mLayoutManagerTodosProdutos);
        retornaCardapioCompleto(Key_Empresa);
        //adapter_SQLite = new CardapioAdapter(cardapios, getActivity(), Key_Empresa);
        recycleViewCardapio.setAdapter(adapter_SQLite);
        adapter_SQLite.notifyDataSetChanged();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
}
