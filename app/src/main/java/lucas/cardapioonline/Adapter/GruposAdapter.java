package lucas.cardapioonline.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import lucas.cardapioonline.Classes.clGruposExpand;
import lucas.cardapioonline.Classes.clProdutosItens;
import lucas.cardapioonline.Holders.GrupoViewHolder;
import lucas.cardapioonline.Holders.ProdutosViewHolder;
import lucas.cardapioonline.R;

public class GruposAdapter extends ExpandableRecyclerViewAdapter<GrupoViewHolder, ProdutosViewHolder> {

    Activity activity;

    public GruposAdapter(List<? extends ExpandableGroup> groups, Activity a) {
        super(groups);

        activity = a;
    }

    @Override
    public GrupoViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_item_grupos, parent, false);
        return new GrupoViewHolder(view);
    }

    @Override
    public ProdutosViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_itens_produtos, parent, false);
        return new ProdutosViewHolder(view, activity);
    }

    @Override
    public void onBindChildViewHolder(ProdutosViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final clProdutosItens produtosItens = ((clGruposExpand) group).getItems().get(childIndex);
        holder.setProdutoDescricao(produtosItens.getName());
        holder.setProdutoValorMeia(produtosItens.getValorMeia());
        holder.setProdutoValorInteira(produtosItens.getValorInteira());
        holder.setProdutoComplemento(produtosItens.getComplemento());


    }

    @Override
    public void onBindGroupViewHolder(GrupoViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGrupoDescricao(group);
    }
}
