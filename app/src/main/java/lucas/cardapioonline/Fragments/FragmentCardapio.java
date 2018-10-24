package lucas.cardapioonline.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import lucas.cardapioonline.Classes.clCardapio_Itens;
import lucas.cardapioonline.Classes.clEmpresa;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.Controller.clCardapioItensController;
import lucas.cardapioonline.R;

public class FragmentCardapio extends Fragment {

    private OnFragmentInteractionListener mListener;
    private LinearLayout linearLayout_RetornarMenuPrincipal;
    private RecyclerView recycleViewCardapio;
    private LinearLayoutManager mLayoutManagerTodosProdutos;
    private CardapioAdapter adapter_SQLite;
    private List<clCardapio_Itens> cardapios;
    private clEmpresa EmpresaSelecionada;
    protected TextView txtCardapioNome, txtCardapioEndereco, txtCardapioTelefone,
            txtCardapioHorarioFuncionamento;
    protected ImageView imgCardapioLogo;
    private clCardapioItensController itensController;
    private clUtil util;

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
        util = new clUtil(getActivity());

        linearLayout_RetornarMenuPrincipal = view.findViewById(R.id.linearLayout_RetornarMenuPrincipal);
        recycleViewCardapio = view.findViewById(R.id.recycleViewCardapio);
        itensController = new clCardapioItensController(getActivity());

        carregaDadosEmpresa(EmpresaSelecionada);
        carregarTodosProdutos(EmpresaSelecionada.getKey_empresa());

        linearLayout_RetornarMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void carregaDadosEmpresa(clEmpresa empresa) {
        txtCardapioNome.setText(empresa.getNome());
        txtCardapioEndereco.setText(empresa.getLogradouro() + ", " + empresa.getNumero() + " - " + empresa.getBairro());
        txtCardapioTelefone.setText(empresa.getTelefone());
        txtCardapioHorarioFuncionamento.setText(empresa.getHorario_funcionamento());

        try {
            byte[] byteImagem = util.lerImagemArmazenamentoInterno(getActivity(), empresa.getKey_empresa());
            Bitmap bmp = BitmapFactory.decodeByteArray(byteImagem,0,byteImagem.length);
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
        adapter_SQLite = new CardapioAdapter(cardapios, getActivity(), Key_Empresa);
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
