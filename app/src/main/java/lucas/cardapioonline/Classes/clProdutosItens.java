package lucas.cardapioonline.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class clProdutosItens implements Parcelable {

    private String descricao;
    private String valorMeia;
    private String valorInteira;
    private String complemento;
    private boolean Favorito;

    public clProdutosItens(String descricao, String valorMeia, String valorInteira, String complemento, boolean Favorito) {
        this.descricao = descricao;
        this.valorMeia = valorMeia;
        this.valorInteira = valorInteira;
        this.complemento = complemento;
        this.Favorito = Favorito;
    }

    protected clProdutosItens(Parcel in) {
        descricao = in.readString();
    }

    public String getName() {
        return descricao;
    }

    public String getValorMeia() {
        return valorMeia;
    }

    public String getValorInteira() {
        return valorInteira;
    }

    public String getComplemento() {
        return complemento;
    }

    public boolean getFavorito() {
        return Favorito;
    }

    public static final Creator<clProdutosItens> CREATOR = new Creator<clProdutosItens>() {
        @Override
        public clProdutosItens createFromParcel(Parcel in) {
            return new clProdutosItens(in);
        }

        @Override
        public clProdutosItens[] newArray(int size) {
            return new clProdutosItens[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(descricao);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof clProdutosItens)) return false;

        clProdutosItens produtosItens = (clProdutosItens) o;

        if (getFavorito() != produtosItens.getFavorito()) return false;
        return getName() != null ? getName().equals(produtosItens.getName()) : produtosItens.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getFavorito() ? 1 : 0);
        return result;
    }
}
