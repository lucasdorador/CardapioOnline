package lucas.cardapioonline.Classes;

public class clGrupos {

    public clGrupos() {
        inicializaVariaveis();
    }

    private void inicializaVariaveis() {
        key_grupo = "";
        descricao = "";
    }

    public String getKey_grupo() {
        return key_grupo;
    }

    public void setKey_grupo(String key_grupo) {
        this.key_grupo = key_grupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    private String key_grupo;
    private String descricao;


}
