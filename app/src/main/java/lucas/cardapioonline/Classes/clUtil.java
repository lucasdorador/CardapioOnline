package lucas.cardapioonline.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class clUtil {

    private static Activity activity;

    public clUtil(Activity a) {
        activity = a;
    }

    public final void MensagemRapida(String mensagem) {
        Toast.makeText(clUtil.activity, mensagem, Toast.LENGTH_LONG).show();
    }

    public final int IntToDP(int Valor){
        Resources r = activity.getResources();
        if (Valor == 0){
            return 0;
        } else {
            return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Valor, r.getDisplayMetrics()));
        }
    }

    public final boolean isConected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( cm != null ) {
            NetworkInfo ni = cm.getActiveNetworkInfo();

            return ni != null && ni.isConnected();
        }

        return false;
    }

    public void gravarImagemArmazenamentoInterno(String NomeArquivo, byte[] Imagem){
        try{
            FileOutputStream fos = activity.openFileOutput(NomeArquivo, Context.MODE_PRIVATE);
            fos.write(Imagem);
            fos.close();
        }catch (IOException e) {
            Log.w("InternalStorage", "Error writing", e);
        }
    }

    public byte[] lerImagemArmazenamentoInterno(String NomeArquivo){
        byte [] resultado = new byte[1024];
        int n = 0;

        try{
            FileInputStream fis;
            fis = activity.openFileInput(NomeArquivo);
            StringBuffer fileContent = new StringBuffer("");

            while ((n = fis.read(resultado)) != -1)
            {
                fileContent.append(new String(resultado, 0, n));
            }

            fis.close();
        }catch (IOException e) {
            Log.w("InternalStorage", "Error read", e);
        }

        return resultado;
    }
}
