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
                cardapio_ValorMeia, cardapio_ValorInteria;
        protected LinearLayout linearLayout_Produtos;

        public ViewHolder(View itemView) {
            super(itemView);
            //Dados dos Produtos
            cardapio_Produto = itemView.findViewById(R.id.cardapio_Produto);
            cardapio_ComplementoProduto = itemView.findViewById(R.id.cardapio_ComplementoProduto);
            cardapio_ValorMeia = itemView.findViewById(R.id.cardapio_ValorMeia);
            cardapio_ValorInteria = itemView.findViewById(R.id.cardapio_ValorInteria);
            linearLayout_Produtos = itemView.findViewById(R.id.linearLayout_Produtos);
        }
    }
}
