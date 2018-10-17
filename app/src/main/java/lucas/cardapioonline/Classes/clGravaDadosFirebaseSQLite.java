package lucas.cardapioonline.Classes;

import android.content.Context;

import lucas.cardapioonline.Controller.clCardapioItensController;
import lucas.cardapioonline.Controller.clEmpresaController;
import lucas.cardapioonline.Controller.clUsuariosController;

public class clGravaDadosFirebaseSQLite {

    private clCardapioItensController itensController;
    private clEmpresaController empresaController;
    private clUsuariosController usuariosController;

    public clGravaDadosFirebaseSQLite(Context context) {
        empresaController = new clEmpresaController(context);
        usuariosController = new clUsuariosController(context);
        itensController = new clCardapioItensController(context);
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


}
