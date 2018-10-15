package lucas.cardapioonline.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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

import java.io.Serializable;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import lucas.cardapioonline.Activity.MenuActivity;
import lucas.cardapioonline.Classes.clEmpresa;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.DAO.ConfiguracaoFirebase;
import lucas.cardapioonline.R;

public class EmpresasAdapter extends RecyclerView.Adapter<EmpresasAdapter.ViewHolder> {

    private List<clEmpresa> mEmpresasList;
    private Activity activity;
    private DatabaseReference referenciaFirebase;
    private List<clEmpresa> empresas;
    private clEmpresa todasEmpresas;
    private clUtil util;
    private clEmpresa EmpresaSelecionada;

    public EmpresasAdapter(List<clEmpresa> l, Activity a) {
        activity = a;
        mEmpresasList = l;
        util = new clUtil(activity);
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
        empresas = new ArrayList<>();
        referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        referenciaFirebase.child("empresa").orderByChild("nome").equalTo(item.getNome()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                empresas.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    todasEmpresas = postSnapShot.getValue(clEmpresa.class);
                    empresas.add(todasEmpresas);
                    DisplayMetrics metrics = activity.getResources().getDisplayMetrics();

                    final int height = (metrics.heightPixels / 9);
                    final int width = (metrics.widthPixels / 5);

                    Picasso.get().load(todasEmpresas.getUrl_logo()).resize(width, height).centerCrop().into(holder.img_LogoEmpresas);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.txtNomeEmpresa.setText(item.getNome());
        holder.txtResumoEmpresa.setText(item.getResumo());
        holder.linearLayout_Empresas.setTag(item.getKey_empresa());
        holder.linearLayout_Empresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EmpresaClicada = (String) view.getTag();
                pcdCarregaEmpresa(EmpresaClicada);

            }
        });
    }

    public void pcdCarregaEmpresa(String key_empresa) {
        EmpresaSelecionada = new clEmpresa();
        DatabaseReference reference = ConfiguracaoFirebase.getReferenciaFirebase();

        reference.child("empresa")
                .orderByChild("key_empresa")
                .equalTo(key_empresa)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                            EmpresaSelecionada = postSnapShot.getValue(clEmpresa.class);
                            abreActivityMenus(EmpresaSelecionada.getKey_empresa(), EmpresaSelecionada);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
        protected clEmpresa EmpresaSelecionada;

        public ViewHolder(View itemView) {
            super(itemView);

            txtNomeEmpresa = itemView.findViewById(R.id.txtNomeEmpresas);
            txtResumoEmpresa = itemView.findViewById(R.id.txtResumoEmpresas);
            linearLayout_Empresas = itemView.findViewById(R.id.linearLayout_Empresas);
            img_LogoEmpresas = itemView.findViewById(R.id.img_LogoEmpresas);
        }

    }
}
