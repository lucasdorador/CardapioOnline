package lucas.cardapioonline.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import android.widget.Toast;
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
}
