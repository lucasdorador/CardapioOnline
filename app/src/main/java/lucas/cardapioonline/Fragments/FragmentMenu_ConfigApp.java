package lucas.cardapioonline.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.beardedhen.androidbootstrap.BootstrapButton;

import lucas.cardapioonline.Classes.clConfiguracoes;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.Controller.clConfiguracoesController;
import lucas.cardapioonline.R;

public class FragmentMenu_ConfigApp extends Fragment {

    private OnFragmentInteractionListener mListener;
    private LinearLayout linearLayout_RetornarMenuConfiguracoes_App_Rede;
    private BootstrapButton btnSalvarConfigREDE;
    private Switch SwitchDadosWifi, SwitchDadosMoveis, SwitchArmazenamento;
    private clConfiguracoesController controller;
    private clConfiguracoes configuracoes;
    private clUtil util;
    Boolean vlBDadosmoveis = false, vlBDadoswifi = false, vlBDadosArmazenamento = false;

    public FragmentMenu_ConfigApp() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_menu_configapp, container, false);

        linearLayout_RetornarMenuConfiguracoes_App_Rede = view.findViewById(R.id.linearLayout_RetornarMenuConfiguracoes_App_ConfigREDE);
        btnSalvarConfigREDE = view.findViewById(R.id.btnSalvarConfig_REDE);
        SwitchDadosWifi = view.findViewById(R.id.SwitchDadosWifi);
        SwitchDadosMoveis = view.findViewById(R.id.SwitchDadosMoveis);
        SwitchArmazenamento = view.findViewById(R.id.SwitchArmazenamento);
        controller = new clConfiguracoesController(getContext());
        util = new clUtil(getActivity());

        carregaConfiguracoes();

        SwitchArmazenamento.setChecked(util.armazenamentoExternoAcessivel());
        SwitchArmazenamento.setEnabled(util.armazenamentoExternoAcessivel());

        SwitchDadosWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                vlBDadoswifi = b;
            }
        });

        SwitchDadosMoveis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                vlBDadosmoveis = b;
            }
        });

        SwitchArmazenamento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                vlBDadosArmazenamento = b;
            }
        });

        btnSalvarConfigREDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.insereDadosConfiguracoes(String.valueOf(vlBDadosmoveis),
                        String.valueOf(vlBDadoswifi),
                        String.valueOf(vlBDadosArmazenamento));
                configuracoes.atualizaDados(vlBDadosmoveis, vlBDadoswifi, vlBDadosArmazenamento);

                util.MensagemRapida("Dados atualizados!");
            }
        });

        linearLayout_RetornarMenuConfiguracoes_App_Rede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
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

    private void carregaConfiguracoes() {
        configuracoes = clConfiguracoes.getInstance(getContext());
        vlBDadosmoveis = configuracoes.dadosMoveis();
        vlBDadoswifi = configuracoes.dadosWifi();
        vlBDadosArmazenamento = configuracoes.armazenamentoExterno();

        SwitchDadosMoveis.setChecked(vlBDadosmoveis);
        SwitchDadosWifi.setChecked(vlBDadoswifi);
        SwitchArmazenamento.setChecked(vlBDadosArmazenamento);

    }
}