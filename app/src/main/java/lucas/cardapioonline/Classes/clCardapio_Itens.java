package lucas.cardapioonline.Classes;

public class clCardapio_Itens {

    //Dados do Cardápio
    private String grupo;
    private String key_produto;
    private String descricao;
    private String complemento;
    private String valor_meia;
    private String valor_inteira;

    public clCardapio_Itens() {
        inicializaVariaveis();
    }

    private void inicializaVariaveis(){
        grupo = "";
        key_produto = "";
        descricao = "";
        complemento = "";
        valor_meia = "";
        valor_inteira = "";
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getkey_produto() {
        return key_produto;
    }

    public void setkey_produto(String key_produto) {
        this.key_produto = key_produto;
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
