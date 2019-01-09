package lucas.cardapioonline.Classes;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lucas.cardapioonline.Controller.clCardapioItensController;
import lucas.cardapioonline.Controller.clGrupoController;

public class clFuncoesPersistencia {

    private DatabaseReference reference, referenceItens;
    private clEmpresa todasEmpresas;
    private clUsuarios todosUsuarios;
    private clCardapio_Itens todosItens;
    private clGrupos todosGrupos;
    private clGravaDadosFirebaseSQLite dadosFirebaseSQLite;
    private clCardapioItensController itensController;
    private clGrupoController grupoController;
    private Activity activity;
    private clUtil util;

    public clFuncoesPersistencia(Activity a) {
        this.reference = FirebaseDatabase.getInstance().getReference();
        this.referenceItens = FirebaseDatabase.getInstance().getReference();
        activity = a;
        util = new clUtil(activity);
        dadosFirebaseSQLite = new clGravaDadosFirebaseSQLite(activity);
        itensController = new clCardapioItensController(activity);
        grupoController = new clGrupoController(activity);
    }

    public void gravarDadosSQLite_Empresa_Itens() {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("empresa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    todasEmpresas = postSnapShot.getValue(clEmpresa.class);
                    dadosFirebaseSQLite.gravaDadosEmpresa(todasEmpresas);

                    referenceItens = FirebaseDatabase.getInstance().getReference();
                    referenceItens.child("cardapio_itens")
                            .child(todasEmpresas.getKey_empresa())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapShotItens : dataSnapshot.getChildren()) {
                                        todosItens = postSnapShotItens.getValue(clCardapio_Itens.class);
                                        dadosFirebaseSQLite.gravaDadosCardapioItens(todosItens);
                                        clConstantes.passouItens = true;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                    clConstantes.passouEmpresa = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void gravarDadosSQLite_Itens_Grupos(final String key_Empresa) {
        if (!key_Empresa.equals("")) {
            referenceItens = FirebaseDatabase.getInstance().getReference();
            referenceItens.child("cardapio_itens")
                    .child(key_Empresa)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            itensController.deletaTODOSItens(key_Empresa);

                            for (DataSnapshot postSnapShotItens : dataSnapshot.getChildren()) {
                                todosItens = postSnapShotItens.getValue(clCardapio_Itens.class);
                                dadosFirebaseSQLite.gravaDadosCardapioItens(todosItens);
                                clConstantes.passouItens = true;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

            reference = FirebaseDatabase.getInstance().getReference();
            reference.child("grupos").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    grupoController.deletaTODOSGrupos();

                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        todosGrupos = postSnapShot.getValue(clGrupos.class);
                        dadosFirebaseSQLite.gravaDadosGrupos(todosGrupos);
                        clConstantes.passouGrupos = true;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void gravarDadosSQLite_Grupos() {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("grupos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                grupoController.deletaTODOSGrupos();

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    todosGrupos = postSnapShot.getValue(clGrupos.class);
                    dadosFirebaseSQLite.gravaDadosGrupos(todosGrupos);
                    clConstantes.passouGrupos = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void gravarDadosSQLite_Firebase() {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("empresa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    todasEmpresas = postSnapShot.getValue(clEmpresa.class);
                    dadosFirebaseSQLite.gravaDadosEmpresa(todasEmpresas);

                    referenceItens = FirebaseDatabase.getInstance().getReference();
                    referenceItens.child("cardapio_itens")
                            .child(todasEmpresas.getKey_empresa())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapShotItens : dataSnapshot.getChildren()) {
                                        todosItens = postSnapShotItens.getValue(clCardapio_Itens.class);
                                        dadosFirebaseSQLite.gravaDadosCardapioItens(todosItens);
                                        clConstantes.passouItens = true;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                    clConstantes.passouEmpresa = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    todosUsuarios = postSnapShot.getValue(clUsuarios.class);
                    dadosFirebaseSQLite.gravaDadosUsuarios(todosUsuarios);
                    clConstantes.passouUsuario = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("grupos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    todosGrupos = postSnapShot.getValue(clGrupos.class);
                    dadosFirebaseSQLite.gravaDadosGrupos(todosGrupos);
                    clConstantes.passouGrupos = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String mostrarLogin() {

        String resultado = "";

        if (dadosFirebaseSQLite.existeDadosBancoSQLite()) {
            /*Intent intent = new Intent(AtualizaDadosActivity.this, MainActivity.class);
            startActivity(intent);*/
            activity.setResult(activity.RESULT_OK);
            activity.finish();
        } else {
            if (util.isConected(activity)) {
                /*Intent intent = new Intent(AtualizaDadosActivity.this, MainActivity.class);
                startActivity(intent);*/
                activity.setResult(activity.RESULT_OK);
                activity.finish();
            } else {
                resultado = "Não foi conectar para atualizar os dados, " +
                        "tente novamente quando a internet for reestabelecida";
            }
        }

        return resultado;
    }
}
