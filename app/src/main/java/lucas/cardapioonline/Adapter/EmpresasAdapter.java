package lucas.cardapioonline.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import lucas.cardapioonline.Activity.MenuActivity;
import lucas.cardapioonline.Classes.clEmpresa;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.Controller.clEmpresaController;
import lucas.cardapioonline.R;

public class EmpresasAdapter extends RecyclerView.Adapter<EmpresasAdapter.ViewHolder> {

    private List<clEmpresa> mEmpresasList;
    private Activity activity;
    private clUtil util;
    private clEmpresa EmpresaSelecionada;
    private clEmpresaController empresaController;

    public EmpresasAdapter(List<clEmpresa> l, Activity a) {
        activity = a;
        mEmpresasList = l;
        util = new clUtil(activity);
        empresaController = new clEmpresaController(activity);
    }

    @NonNull
    @Override
    public EmpresasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_empresas, viewGroup, false);
        return new EmpresasAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EmpresasAdapter.ViewHolder holder, int position) {
        final clEmpresa item = mEmpresasList.get(position);

        try {
            byte[] byteImagem = util.lerImagemArmazenamentoInterno(activity, item.getKey_empresa());
            Bitmap bmp = BitmapFactory.decodeByteArray(byteImagem,0,byteImagem.length);
            holder.img_LogoEmpresas.setImageBitmap(bmp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.txtNomeEmpresa.setText(item.getNome());
        holder.txtResumoEmpresa.setText(item.getResumo());
        holder.linearLayout_Empresas.setTag(item.getKey_empresa());
        holder.linearLayout_Empresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EmpresaClicada = (String) view.getTag();
                pcdCarregaEmpresa_SQLite(EmpresaClicada);

            }
        });
    }

    public void pcdCarregaEmpresa_SQLite(String keyEmpresa){
        EmpresaSelecionada = empresaController.retornaClasseEmpresaSQLite(keyEmpresa);
        abreActivityMenus(EmpresaSelecionada.getKey_empresa(), EmpresaSelecionada);
    }

    @Override
    public int getItemCount() {
        return mEmpresasList.size();
    }

    private void abreActivityMenus(String Key_Empresa, clEmpresa clEmpresa) {
        Intent intent = new Intent(activity, MenuActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Acao", "Cardapio");
        bundle.putString("Key_Empresa", Key_Empresa);
        bundle.putSerializable("ClasseEmpresa", clEmpresa);
        intent.putExtras(bundle);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(activity.getApplicationContext(), R.anim.activity_menu_entrada, R.anim.activity_principal_saida);
        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
        activity.finish();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtNomeEmpresa, txtResumoEmpresa;
        protected LinearLayout linearLayout_Empresas;
        protected ImageView img_LogoEmpresas;

        public ViewHolder(View itemView) {
            super(itemView);

            txtNomeEmpresa = itemView.findViewById(R.id.txtNomeEmpresas);
            txtResumoEmpresa = itemView.findViewById(R.id.txtResumoEmpresas);
            linearLayout_Empresas = itemView.findViewById(R.id.linearLayout_Empresas);
            img_LogoEmpresas = itemView.findViewById(R.id.img_LogoEmpresas);
        }

    }
}
