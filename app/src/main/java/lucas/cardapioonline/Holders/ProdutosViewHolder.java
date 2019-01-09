package lucas.cardapioonline.Holders;

import android.view.View;
import android.widget.TextView;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.DecimalFormat;

import lucas.cardapioonline.R;

public class ProdutosViewHolder extends ChildViewHolder {

    private TextView lista_itens_produtos_descricao;
    private TextView lista_itens_produtos_valormeia;
    private TextView lista_itens_produtos_valorinteira;
    private TextView lista_itens_produtos_complemento;
    DecimalFormat nf;

    public ProdutosViewHolder(View itemView) {
        super(itemView);
        nf = new DecimalFormat("0.00");
        lista_itens_produtos_descricao = itemView.findViewById(R.id.lista_itens_produtos_descricao);
        lista_itens_produtos_valormeia = itemView.findViewById(R.id.lista_itens_produtos_valormeia);
        lista_itens_produtos_valorinteira = itemView.findViewById(R.id.lista_itens_produtos_valorinteira);
        lista_itens_produtos_complemento = itemView.findViewById(R.id.lista_itens_produtos_complemento);
    }

    public void setProdutoDescricao(String descricao) {
        lista_itens_produtos_descricao.setText(descricao);
    }

    public void setProdutoValorMeia(String valorMeia) {
        if (valorMeia.equals("0")) {
            lista_itens_produtos_valormeia.setText("--");
        } else if (!valorMeia.equals("")) {
            lista_itens_produtos_valormeia.setText(nf.format(Double.valueOf(valorMeia)));
        }
    }

    public void setProdutoValorInteira(String valorInteira) {
        if (!valorInteira.equals("")) {
            lista_itens_produtos_valorinteira.setText(nf.format(Double.valueOf(valorInteira)));
        }
    }

    public void setProdutoComplemento(String complemento) {
        lista_itens_produtos_complemento.setText(complemento);
    }
}
