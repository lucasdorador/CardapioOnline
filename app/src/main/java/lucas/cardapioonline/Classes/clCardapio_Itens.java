package lucas.cardapioonline.Classes;

public class clCardapio_Itens {

    //Dados do Cardápio
    private String complemento;
    private String descricao;
    private String key_grupo;
    private String key_produto;
    private String key_empresa;
    private String valor_inteira;
    private String valor_meia;

    public clCardapio_Itens() {
        inicializaVariaveis();
    }

    private void inicializaVariaveis(){
        key_grupo = "";
        key_produto = "";
        descricao = "";
        complemento = "";
        valor_meia = "";
        valor_inteira = "";
        key_empresa = "";
    }

    public String getKey_produto() {
        return key_produto;
    }

    public void setKey_produto(String key_produto) {
        this.key_produto = key_produto;
    }

    public String getKey_empresa() {
        return key_empresa;
    }

    public void setKey_empresa(String key_empresa) {
        this.key_empresa = key_empresa;
    }

    public String getkey_Grupo() {
        return key_grupo;
    }

    public void setkey_Grupo(String grupo) {
        this.key_grupo = grupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getValor_meia() {
        return valor_meia;
    }

    public void setValor_meia(String valor_meia) {
        this.valor_meia = valor_meia;
    }

    public String getValor_inteira() {
        return valor_inteira;
    }

    public void setValor_inteira(String valor_inteira) {
        this.valor_inteira = valor_inteira;
    }
}
