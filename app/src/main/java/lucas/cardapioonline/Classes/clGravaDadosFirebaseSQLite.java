package lucas.cardapioonline.Classes;

import android.content.Context;

import lucas.cardapioonline.Controller.clCardapioItensController;
import lucas.cardapioonline.Controller.clEmpresaController;
import lucas.cardapioonline.Controller.clInfoAtualizacaoController;
import lucas.cardapioonline.Controller.clUsuariosController;
import lucas.cardapioonline.Controller.clGrupoController;

public class clGravaDadosFirebaseSQLite {

    private clCardapioItensController itensController;
    private clEmpresaController empresaController;
    private clUsuariosController usuariosController;
    private clInfoAtualizacaoController infoAtualizacaoController;
    private clGrupoController clGrupoController;
    private Context context;

    public clGravaDadosFirebaseSQLite(Context context) {
        empresaController = new clEmpresaController(context);
        usuariosController = new clUsuariosController(context);
        itensController = new clCardapioItensController(context);
        infoAtualizacaoController = new clInfoAtualizacaoController(context);
        clGrupoController = new clGrupoController(context);
        this.context = context;
    }

    public boolean gravaDadosEmpresa(clEmpresa empresa) {
        boolean resultado = true;

        try {
            if ((empresa != null) && (!empresa.getKey_empresa().equals(""))) {
                if (empresaController.existeDadosCadastrados(empresa.getKey_empresa())) {
                    empresaController.alteraDadosEmpresa(empresa);
                } else {
                    empresaController.insereDadosEmpresa(empresa);
                }
            }
        } catch (Throwable e) {
            resultado = false;
            e.printStackTrace();
        }

        return resultado;
    }

    public boolean gravaDadosUsuarios(clUsuarios usuarios) {
        boolean resultado = true;

        try {
            if ((usuarios != null) && (!usuarios.getKeyUsuario().equals(""))) {
                if (usuariosController.existeDadosCadastrados(usuarios.getKeyUsuario())) {
                    usuariosController.alteraDadosUsuarios(usuarios);
                } else {
                    usuariosController.insereDadosUsuarios(usuarios);
                }
            }
        } catch (Throwable e) {
            resultado = false;
            e.printStackTrace();
        }

        return resultado;
    }

    public boolean gravaDadosCardapioItens(clCardapio_Itens itens) {
        boolean resultado = true;

        try {
            if ((itens != null) && (!itens.getKey_produto().equals(""))) {
                if (itensController.existeDadosCadastrados(itens.getKey_produto(), itens.getKey_empresa())) {
                    itensController.alteraDadosCardapioItens(itens);
                } else {
                    itensController.insereDadosCardapioItens(itens);
                }
            }
        } catch (Throwable e) {
            resultado = false;
            e.printStackTrace();
        }

        return resultado;
    }

    public boolean gravaDadosInfoAtualizacao(clInfoAtualizacao infoAtualizacao) {
        boolean resultado = true;

        try {
            if (infoAtualizacaoController.existeDadosCadastrados()) {
                infoAtualizacaoController.alteraDadosInfoAtualizacao(infoAtualizacao);
            } else {
                infoAtualizacaoController.insereDadosInfoAtualizacao(infoAtualizacao);
            }

        } catch (Throwable e) {
            resultado = false;
            e.printStackTrace();
        }

        return resultado;

    }

    public boolean gravaDadosGrupos(clGrupos grupos) {
        boolean resultado = true;

        try {
            if (clGrupoController.existeDadosCadastrados(grupos.getKey_grupo())) {
                clGrupoController.alteraDadosGrupos(grupos);
            } else {
                clGrupoController.insereDadosGrupos(grupos);
            }

        } catch (Throwable e) {
            resultado = false;
            e.printStackTrace();
        }

        return resultado;

    }

    public boolean existeDadosBancoSQLite() {
        return empresaController.existeDadosCadastrados("");
    }


}
