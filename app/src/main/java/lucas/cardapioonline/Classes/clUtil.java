package lucas.cardapioonline.Classes;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class clUtil {

    private static Activity activity;

    public clUtil(Activity a) {
        activity = a;
    }

    public final void MensagemRapida(String mensagem) {
        Toast.makeText(clUtil.activity, mensagem, Toast.LENGTH_LONG).show();
    }

    public final boolean isConected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();

            return ni != null && ni.isConnected();
        }

        return false;
    }

    public final boolean conexaoWifi(Context context) {
        boolean result = false;
        ConnectivityManager conmag = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conmag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
            result = true;
        }

        return result;
    }

    public final String retornaDataAtual(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date hoje = cal.getTime();

        String data_atual = dateFormat.format(hoje);

        return data_atual;
    }

    public final String retornaHoraAtual(){
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        Calendar  cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String hora_atual = dateFormat_hora.format(data_atual);

        return hora_atual;
    }

    public final boolean conexaoDadosMoveis(Context context) {
        boolean result = false;
        ConnectivityManager conmag = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conmag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
            result = true;
        }

        return result;
    }

    private void gravarImagemArmazenamentoInterno(String NomeArquivo, byte[] Imagem) {
        try {
            FileOutputStream fos = activity.openFileOutput(NomeArquivo, Context.MODE_PRIVATE);
            fos.write(Imagem);
            fos.close();
        } catch (IOException e) {
            Log.w("InternalStorage", "Error writing", e);
        }
    }

    private void gravarImagemArmazenamentoExterno(String fileName, byte[] Imagem) {
        File path = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File(path, fileName);
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(Imagem);
            os.close();
        } catch (IOException e) {
            Log.w("ExternalStorage", "Error writing", e);
        }
    }

    public void gravarImagemArmazenamento(String fileName, byte[] Imagem) {
        //clConfiguracoes configuracoes = clConfiguracoes.getInstance(activity);

        //if ((configuracoes.armazenamentoExterno()) && armazenamentoExternoAcessivel()){
        //  gravarImagemArmazenamentoExterno(fileName, Imagem);
        //} else {
        gravarImagemArmazenamentoInterno(fileName, Imagem);
        //}
    }

    public static byte[] lerImagemArmazenamentoInterno(Context context, String filename) throws IOException {
        FileInputStream fis = context.openFileInput(filename);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int bytesRead = fis.read(buffer);
        while (bytesRead != -1) {
            baos.write(buffer, 0, bytesRead);
            bytesRead = fis.read(buffer);
        }
        return baos.toByteArray();
    }

    public boolean armazenamentoExternoAcessivel() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void carregaImagem_ImageView(int uri,
                                        ImageView imageView, int width,
                                        int height) {

        RequestOptions options = new RequestOptions().fitCenter().override(width, height);
        Glide.with(activity).load(uri).apply(options).into(imageView);

    }

    public void carregaImagem_ImageView(String uri,
                                        ImageView imageView, int width,
                                        int height) {

        RequestOptions options = new RequestOptions().fitCenter().override(width, height);
        Glide.with(activity).load(uri).apply(options).into(imageView);

    }
}
