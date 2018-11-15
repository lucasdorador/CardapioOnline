package lucas.cardapioonline.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import lucas.cardapioonline.Classes.clCardapio_Itens;
import lucas.cardapioonline.R;

public class CardapioAdapter extends RecyclerView.Adapter<CardapioAdapter.ViewHolder> {

    private List<clCardapio_Itens> mCardapioList;
    private Context context;
    private String Key_Empresa;
    private String vlsControleGrupos = "";

    public CardapioAdapter(List<clCardapio_Itens> l, Context c, String ke) {
        context = c;
        mCardapioList = l;
        Key_Empresa = ke;
    }

    @NonNull
    @Override
    public CardapioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_cardapio, viewGroup, false);
        return new CardapioAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardapioAdapter.ViewHolder holder, int position) {
        DecimalFormat nf = new DecimalFormat("0.00");
        final clCardapio_Itens item = mCardapioList.get(position);

        holder.cardapio_Produto.setText(item.getDescricao());
        holder.cardapio_ComplementoProduto.setText(item.getComplemento());
        if (item.getValor_meia().equals("0")) {
            holder.cardapio_ValorMeia.setText("--");
        } else if (!item.getValor_meia().equals("")) {
            holder.cardapio_ValorMeia.setText(nf.format(Double.valueOf(item.getValor_meia())));
        }

        if (!item.getValor_inteira().equals("")) {
            holder.cardapio_ValorInteria.setText(nf.format(Double.valueOf(item.getValor_inteira())));
        }

        /*if (vlsControleGrupos.equals("")) {
            holder.cardapio_DescricaoGrupos.setText(item.getGrupo());
            holder.cardapio_FixoDescricao.setVisibility(View.VISIBLE);
            holder.cardapio_FixoValorMeia.setVisibility(View.VISIBLE);
            holder.cardapio_FixoValorInteira.setVisibility(View.VISIBLE);
            holder.cardapio_DescricaoGrupos.setVisibility(View.VISIBLE);
            holder.view_Cardapio.setVisibility(View.VISIBLE);
            vlsControleGrupos = item.getGrupo();
        } else {
            if (vlsControleGrupos.equals(item.getGrupo())) {
                holder.cardapio_FixoDescricao.setVisibility(View.INVISIBLE);
                holder.cardapio_FixoValorMeia.setVisibility(View.INVISIBLE);
                holder.cardapio_FixoValorInteira.setVisibility(View.INVISIBLE);
                holder.cardapio_DescricaoGrupos.setVisibility(View.INVISIBLE);
                holder.view_Cardapio.setVisibility(View.INVISIBLE);
            } else {
                holder.cardapio_DescricaoGrupos.setText(item.getGrupo());
                holder.cardapio_FixoDescricao.setVisibility(View.VISIBLE);
                holder.cardapio_FixoValorMeia.setVisibility(View.VISIBLE);
                holder.cardapio_FixoValorInteira.setVisibility(View.VISIBLE);
                holder.cardapio_DescricaoGrupos.setVisibility(View.VISIBLE);
                holder.view_Cardapio.setVisibility(View.VISIBLE);
                vlsControleGrupos = item.getGrupo();
            }
        }*/

        holder.linearLayout_Produtos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCardapioList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView cardapio_Produto, cardapio_ComplementoProduto,
                cardapio_ValorMeia, cardapio_ValorInteria/*, cardapio_DescricaoGrupos,
                cardapio_FixoDescricao, cardapio_FixoValorMeia, cardapio_FixoValorInteira*/;
        protected LinearLayout linearLayout_Produtos;
        protected View view_Cardapio;

        public ViewHolder(View itemView) {
            super(itemView);
            //Dados dos Produtos
            cardapio_Produto = itemView.findViewById(R.id.cardapio_Produto);
            cardapio_ComplementoProduto = itemView.findViewById(R.id.cardapio_ComplementoProduto);
            cardapio_ValorMeia = itemView.findViewById(R.id.cardapio_ValorMeia);
            cardapio_ValorInteria = itemView.findViewById(R.id.cardapio_ValorInteria);
            //cardapio_DescricaoGrupos = itemView.findViewById(R.id.cardapio_GrupoProdutos);
            //cardapio_FixoDescricao = itemView.findViewById(R.id.txtFixo_Descricao);
            //cardapio_FixoValorMeia = itemView.findViewById(R.id.txtFixo_ValorMeia);
            //cardapio_FixoValorInteira = itemView.findViewById(R.id.txtFixo_ValorInteira);
            linearLayout_Produtos = itemView.findViewById(R.id.linearLayout_Produtos);
            view_Cardapio = itemView.findViewById(R.id.view_Cardapio);
        }
    }
}
