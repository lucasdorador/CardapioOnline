package lucas.cardapioonline.Classes;

public class clEmpresa {

    private String bairro;
    private String cep;
    private String cidade;
    private String horario_funcionamento;
    private String key_empresa;
    private String logradouro;
    private String nome;
    private String numero;
    private String resumo;
    private String telefone;
    private String url_logo;

    public clEmpresa() {
        inicializaVariaveis();
    }

    private void inicializaVariaveis() {
        bairro = "";
        cep = "";
        cidade = "";
        horario_funcionamento = "";
        key_empresa = "";
        logradouro = "";
        nome = "";
        numero = "";
        resumo = "";
        telefone = "";
        url_logo = "";
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getHorario_funcionamento() {
        return horario_funcionamento;
    }

    public void setHorario_funcionamento(String horario_funcionamento) {
        this.horario_funcionamento = horario_funcionamento;
    }

    public String getKey_empresa() {
        return key_empresa;
    }

    public void setKey_empresa(String key_empresa) {
        this.key_empresa = key_empresa;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getUrl_logo() {
        return url_logo;
    }

    public void setUrl_logo(String url_logo) {
        this.url_logo = url_logo;
    }
}
