package lucas.cardapioonline.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;
import lucas.cardapioonline.Activity.PrincipalActivity;
import lucas.cardapioonline.Classes.clUsuarios;
import lucas.cardapioonline.Classes.clUtil;
import lucas.cardapioonline.Controller.clUsuariosController;
import lucas.cardapioonline.DAO.ConfiguracaoFirebase;
import lucas.cardapioonline.Helper.Preferencias_Usuario;
import lucas.cardapioonline.R;

public class FragmentConectar extends Fragment {

    private BootstrapButton btnConectarFacebook, btnConectarGoogle, btnCadastrarEmail;
    private BootstrapEditText edtConectarEmail, edtConectarSenha;
    private TextView txtEsqueceuSenha;
    private FirebaseAuth autenticacao;
    private DatabaseReference reference;
    private clUsuarios usuario;
    private AlertDialog dialog;
    private clUtil util;
    private AlertDialog alerta;
    private clUsuariosController usuariosController;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_conectar, container, false);

        dialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setTheme(R.style.LoginCustom)
                .setCancelable(false)
                .build();

        btnConectarFacebook = view.findViewById(R.id.btnConectarFacebook);
        btnConectarGoogle = view.findViewById(R.id.btnConectarGoogle);
        btnCadastrarEmail = view.findViewById(R.id.btnCadastrarEmail);
        edtConectarEmail = view.findViewById(R.id.edtConectarEmail);
        edtConectarSenha = view.findViewById(R.id.edtConectarSenha);
        txtEsqueceuSenha = view.findViewById(R.id.txtEsqueceuSenha);
        reference = FirebaseDatabase.getInstance().getReference();
        autenticacao = FirebaseAuth.getInstance();
        usuariosController = new clUsuariosController(getContext());
        util = new clUtil(getActivity());

        final EditText editTextEmail = new EditText(getContext());
        editTextEmail.setHint("exemplo@exemplo.com");
        editTextEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        btnConectarFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnConectarGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCadastrarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                conectarEmail();
            }
        });


        txtEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setTitle("Recuperar senha");
                builder.setMessage("Informe seu e-mail");
                builder.setView(editTextEmail);

                if (!editTextEmail.getText().equals("")) {
                    builder.setPositiveButton("Recuperar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            autenticacao = FirebaseAuth.getInstance();
                            String emailRecuperar = editTextEmail.getText().toString();

                            if (!emailRecuperar.equals("")) {
                                autenticacao.sendPasswordResetEmail(emailRecuperar).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            util.MensagemRapida("Em instantes você receberá um e-mail");
                                            reiniciarActivity();
                                        } else {
                                            util.MensagemRapida("Falha ao enviar o e-mail");
                                            reiniciarActivity();
                                        }
                                    }
                                });
                            } else {
                                util.MensagemRapida("Preencha o campo de e-mail!");
                                reiniciarActivity();
                            }
                        }
                    });

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reiniciarActivity();
                        }
                    });

                } else {
                    util.MensagemRapida("Preencha o campo de e-mail!");
                    reiniciarActivity();
                }

                alerta = builder.create();
                alerta.show();
            }
        });

        return view;
    }

    private void reiniciarActivity() {
        Bundle bundle = new Bundle();
        bundle.putString("ReiniciarFragmentConectar", "Ok");
        Intent intent = getActivity().getIntent();
        intent.putExtras(bundle);
        getActivity().overridePendingTransition(0, 0);
        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private void conectarEmail() {
        boolean vlbValidacao = true;

        if (edtConectarEmail.getText().toString().equals("")) {
            vlbValidacao = false;
            edtConectarEmail.setError("Preenchimento obrigatório!");
        }

        if (edtConectarSenha.getText().toString().equals("")) {
            vlbValidacao = false;
            edtConectarSenha.setError("Preenchimento obrigatório!");
        }

        if (vlbValidacao) {

            usuario = new clUsuarios();
            usuario.setEmail(edtConectarEmail.getText().toString());
            usuario.setSenha(edtConectarSenha.getText().toString());

            validarLogin();

        } else {
            dialog.dismiss();
        }
    }

    private void validarLogin() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail().toString(), usuario.getSenha().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    abrirTelaPrincipal();
                    Preferencias_Usuario preferenciasUsuario = new Preferencias_Usuario(getActivity());
                    preferenciasUsuario.salvarUsuarioPreferencias(usuario.getEmail(), usuario.getSenha());
                    dialog.dismiss();
                    util.MensagemRapida("Login efetuado com sucesso!");
                } else {
                    dialog.dismiss();
                    Exception e = task.getException();
                    if ((e.getMessage().contains("network error"))) {
                        util.MensagemRapida("Verifique se está conectado a internet! Tente novamente");
                    } else {
                        util.MensagemRapida("Usuário ou senha inválidos! Tente novamente");
                    }
                }
            }
        });
    }

    private void abrirTelaPrincipal() {
        String email = autenticacao.getCurrentUser().getEmail().toString();
        String tipoUsuario = usuariosController.retornaConsultaUsuarioByEmail("tipoUsuario", email);

        if (!tipoUsuario.equals("")) {
            abreMenus(tipoUsuario);
        } else {
            reference.child("usuarios").orderByChild("email").equalTo(email.toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String tipoEmailUsuario = postSnapshot.child("tipoUsuario").getValue().toString();
                        abreMenus(tipoEmailUsuario);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void abreMenus(String tipoUsuario) {
        if (tipoUsuario.equals("Administrador")) {

        } else if (tipoUsuario.equals("Comum")) {
            Intent intent = new Intent(getActivity(), PrincipalActivity.class);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(getActivity().getApplicationContext(), R.anim.activity_menu_entrada, R.anim.activity_principal_saida);
            ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
            dialog.dismiss();
            getActivity().finish();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
