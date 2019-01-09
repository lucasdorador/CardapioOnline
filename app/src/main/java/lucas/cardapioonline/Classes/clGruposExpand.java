package lucas.cardapioonline.Classes;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class clGruposExpand extends ExpandableGroup<clProdutosItens> {

    public clGruposExpand(String descricao, List<clProdutosItens> items) {
        super(descricao, items);
    }
}
