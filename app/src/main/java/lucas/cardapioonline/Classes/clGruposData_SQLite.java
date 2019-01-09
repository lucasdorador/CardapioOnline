package lucas.cardapioonline.Classes;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import lucas.cardapioonline.Controller.clCardapioItensController;
import lucas.cardapioonline.Controller.clGrupoController;

public class clGruposData_SQLite {

    private Context context;
    private static String key_empresa = "";
    private static clGrupoController grupoController;
    private static clCardapioItensController itensController;

    public clGruposData_SQLite(Context context, String key_empresa) {
        this.context = context;
        this.key_empresa = key_empresa;
        itensController = new clCardapioItensController(context);
        grupoController = new clGrupoController(context);
    }

    public static List<clGruposExpand> carregaGrupos() {

        List<clGruposExpand> return_gruposlist = new ArrayList<>();
        List<clGrupos> gruposList = grupoController.retornaListaClasseGruposSQLite();

        for (clGrupos gr : gruposList) {
            return_gruposlist.add(ProdutosPorGrupo(gr.getKey_grupo(), gr.getDescricao()));
        }

        return return_gruposlist;
    }

    public static clGruposExpand ProdutosPorGrupo(String key_grupo, String descricao) {
        return new clGruposExpand(descricao, itensController.retornaProdutosItens_Grupos(key_empresa, key_grupo));
    }
}
