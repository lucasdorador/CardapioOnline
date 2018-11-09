package lucas.cardapioonline.Classes;

import java.util.Date;

public class clInfoAtualizacao {

    private int _id;
    private Date data_atualizacao;
    private Date hora_atualizacao;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Date getData_atualizacao() {
        return data_atualizacao;
    }

    public void setData_atualizacao(Date data_atualizacao) {
        this.data_atualizacao = data_atualizacao;
    }

    public Date getHora_atualizacao() {
        return hora_atualizacao;
    }

    public void setHora_atualizacao(Date hora_atualizacao) {
        this.hora_atualizacao = hora_atualizacao;
    }
}
