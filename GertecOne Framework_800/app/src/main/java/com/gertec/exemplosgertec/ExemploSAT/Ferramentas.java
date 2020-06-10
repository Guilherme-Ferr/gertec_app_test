package com.gertec.exemplosgertec.ExemploSAT;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gertec.exemplosgertec.R;

import java.util.Random;

public class Ferramentas extends Activity {
    private Button button_bloquear, button_desbloquear, button_log, button_atualizar, button_versao;
    private EditText txtCodAtivacao;
    private AlertDialog alerta;
    Random gerador = new Random();
    SatLib satLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        satLib = new SatLib(this);
        setContentView(R.layout.ferramentas);
        button_bloquear = (Button) findViewById(R.id.buttonBloquear);
        button_desbloquear = (Button) findViewById(R.id.buttonDesbloquear);
        button_log = (Button) findViewById(R.id.buttonLog);
        button_atualizar = (Button) findViewById(R.id.buttonAtualizar);
        button_versao = (Button) findViewById(R.id.buttonVersao);
        txtCodAtivacao = (EditText) findViewById(R.id.txtCodAtivacao);


        button_bloquear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (txtCodAtivacao.getText().toString().length() >= 8) {
                   String resp = satLib.bloquearSat(txtCodAtivacao.getText().toString(), gerador.nextInt(999999));
                    RetornoDinamicoSat retornoDinamicoSat = new RetornoDinamicoSat(resp);
                    dialogoRetorno(retornoDinamicoSat.getRespostaRecebida());
                } else {
                    Toast.makeText(Ferramentas.this, "Código de Ativação deve ter entre 8 a 32 caracteres!", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_desbloquear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (txtCodAtivacao.getText().toString().length() >= 8) {
                    String resp = satLib.desbloquearSat(txtCodAtivacao.getText().toString(), gerador.nextInt(999999));
                    RetornoDinamicoSat retornoDinamicoSat = new RetornoDinamicoSat(resp);
                    dialogoRetorno(retornoDinamicoSat.getRespostaRecebida());
                } else {
                    Toast.makeText(Ferramentas.this, "Código de Ativação deve ter entre 8 a 32 caracteres!", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (txtCodAtivacao.getText().toString().length() >= 8) {
                    String resp = satLib.extrairLog(txtCodAtivacao.getText().toString(), gerador.nextInt(999999));
                    RetornoLogSat retornoLogSat = new RetornoLogSat(resp);
                    dialogoRetorno(retornoLogSat.getRespostaRecebida());
                } else {
                    Toast.makeText(Ferramentas.this, "Código de Ativação deve ter entre 8 a 32 caracteres!", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_atualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (txtCodAtivacao.getText().toString().length() >= 8) {
                    String resp = satLib.atualizarSoftware(txtCodAtivacao.getText().toString(), gerador.nextInt(999999));
                    RetornoDinamicoSat retornoDinamicoSat = new RetornoDinamicoSat(resp);
                    dialogoRetorno(retornoDinamicoSat.getRespostaRecebida());
                } else {
                    Toast.makeText(Ferramentas.this, "Código de Ativação deve ter entre 8 a 32 caracteres!", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_versao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                try {
                    dialogoRetorno(satLib.versao());
                } catch (Exception e) {
                    Toast.makeText(Ferramentas.this, "Erro ao verificar versão!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void dialogoRetorno(String s) {
        //TODO Fazer algo com a String que foi retornada do sat.
        try {
            // Inicia o Alert
            AlertDialog.Builder builder = new AlertDialog.Builder(Ferramentas.this);
            //define o titulo
            builder.setTitle("Retorno");
            //define a mensagem
            builder.setMessage(s);
            //define um botão ok
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
//                        button_associar.setEnabled(true);
                }
            });
            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
            alerta.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
