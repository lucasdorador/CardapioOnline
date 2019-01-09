package lucas.cardapioonline.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import lucas.cardapioonline.Classes.clGruposExpand;
import lucas.cardapioonline.R;

public class GrupoViewHolder extends GroupViewHolder {

    private TextView grupoDescricao;
    private ImageView setaSelecao;

    public GrupoViewHolder(View itemView) {
        super(itemView);

        grupoDescricao = itemView.findViewById(R.id.lista_item_grupo_descricao);
        setaSelecao = itemView.findViewById(R.id.lista_item_grupo_setaSelecao);
    }

    public void setGrupoDescricao(ExpandableGroup grupos) {
        if (grupos instanceof clGruposExpand) {
            grupoDescricao.setText(grupos.getTitle());
        }
    }

}
