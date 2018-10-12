package lucas.cardapioonline.Classes;

public class clEmpresas_Inicial {

    private String nome;
    private String resumo;
    private String url_logo;
    private String key_empresa;

    public clEmpresas_Inicial() {
        inicializarVariaveis();
    }

    private void inicializarVariaveis(){
        nome = "";
        resumo = "";
        url_logo = "";
        key_empresa = "";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getUrl_logo() {
        return url_logo;
    }

    public void setUrl_logo(String url_logo) {
        this.url_logo = url_logo;
    }

    public String getkey_empresa() {
        return key_empresa;
    }

    public void setkey_empresa(String key_empresa) {
        this.key_empresa = key_empresa;
    }
}
