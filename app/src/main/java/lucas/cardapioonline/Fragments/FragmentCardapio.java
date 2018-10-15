package lucas.cardapioonline.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lucas.cardapioonline.Adapter.CardapioAdapter;
import lucas.cardapioonline.Classes.clCardapio_Itens;
import lucas.cardapioonline.Classes.clEmpresa;
import lucas.cardapioonline.R;

public class FragmentCardapio extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String Key_Empresa = "";
    private LinearLayout linearLayout_RetornarMenuPrincipal;
    private RecyclerView recycleViewCardapio;
    private LinearLayoutManager mLayoutManagerTodosProdutos;
    private CardapioAdapter adapter;
    private List<clCardapio_Itens> cardapios;
    private DatabaseReference referenciaFirebase;
    private clCardapio_Itens todosProdutos;
    private clEmpresa EmpresaSelecionada;
    protected TextView txtCardapioNome, txtCardapioEndereco, txtCardapioTelefone,
            txtCardapioHorarioFuncionamento;
    protected ImageView imgCardapioLogo;

    public FragmentCardapio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_cardapio, container, false);

        Bundle bundle = this.getArguments();
        Key_Empresa = bundle.getString("Key_Empresa");
        EmpresaSelecionada = (clEmpresa) bundle.getSerializable("ClasseEmpresa");

        //Dados da Empresa
        txtCardapioNome = view.findViewById(R.id.txtCardapioNome);
        txtCardapioEndereco = view.findViewById(R.id.txtCardapioEndereco);
        txtCardapioTelefone = view.findViewById(R.id.txtCardapioTelefone);
        txtCardapioHorarioFuncionamento = view.findViewById(R.id.txtCardapioHorarioFuncionamento);
        imgCardapioLogo = view.findViewById(R.id.imgCardapioLogo);

        linearLayout_RetornarMenuPrincipal = view.findViewById(R.id.linearLayout_RetornarMenuPrincipal);
        recycleViewCardapio = view.findViewById(R.id.recycleViewCardapio);

        carregaDadosEmpresa(EmpresaSelecionada);
        carregarTodosProdutos(Key_Empresa);

        linearLayout_RetornarMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void carregaDadosEmpresa(clEmpresa empresa){
        txtCardapioNome.setText(empresa.getNome());
        txtCardapioEndereco.setText(empresa.getLogradouro() + ", " + empresa.getNumero() +  " - " + empresa.getBairro());
        txtCardapioTelefone.setText(empresa.getTelefone());
        txtCardapioHorarioFuncionamento.setText(empresa.getHorario_funcionamento());

        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();

        final int height = (metrics.heightPixels / 9);
        final int width = (metrics.widthPixels / 5);

        Picasso.get().load(empresa.getUrl_logo()).resize(width, height).centerCrop().into(imgCardapioLogo);
    }

    private void retornaCardapioCompleto(String Key_Empresa) {
        cardapios = new ArrayList<>();
        referenciaFirebase = FirebaseDatabase.getInstance().getReference();

        referenciaFirebase.child("cardapio_itens").
                child(Key_Empresa).
                orderByChild("descricao").
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    todosProdutos = postSnapShot.getValue(clCardapio_Itens.class);
                    cardapios.add(todosProdutos);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void carregarTodosProdutos(String Key_Empresa) {
        recycleViewCardapio.setHasFixedSize(true);
        mLayoutManagerTodosProdutos = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycleViewCardapio.setLayoutManager(mLayoutManagerTodosProdutos);
        retornaCardapioCompleto(Key_Empresa);
        adapter = new CardapioAdapter(cardapios, getActivity(), Key_Empresa);
        recycleViewCardapio.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
