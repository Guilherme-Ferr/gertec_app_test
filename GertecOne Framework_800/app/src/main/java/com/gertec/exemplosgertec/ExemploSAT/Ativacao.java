package com.gertec.exemplosgertec.ExemploSAT;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gertec.exemplosgertec.R;

import java.util.Random;


public class Ativacao extends Activity {
    private TextWatcher cnpjMask;
    private Button button_ativar, button_voltar;
    private EditText txtCodAtivacao, txtConfCodAtivacao;
    private AlertDialog alerta;
    Random gerador = new Random();
    SatLib satLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        satLib = new SatLib(this);
        setContentView(R.layout.ativacao);
        button_ativar = (Button) findViewById(R.id.buttonAtivar);
        txtCodAtivacao = (EditText) findViewById(R.id.txtCodAtivacao);
        txtConfCodAtivacao = (EditText) findViewById(R.id.txtConfCodAtivacao);
        final EditText editCnpj = (EditText) findViewById(R.id.txtCNPJ);
        cnpjMask = Mask.insert("##.###.###/####-##", editCnpj);
        editCnpj.addTextChangedListener(cnpjMask);

        button_ativar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (txtCodAtivacao.getText().toString().length() >= 8) {
                    if (txtCodAtivacao.getText().toString().equals(txtConfCodAtivacao.getText().toString())) {
                        String resp = satLib.ativarSat(txtCodAtivacao.getText().toString(), editCnpj.getText().toString().replace(".", "").replace("/", "").replace("-", ""), gerador.nextInt(999999));
                        RetornoDinamicoSat retornoDinamicoSat = new RetornoDinamicoSat(resp);
                        dialogoRetorno(retornoDinamicoSat.getRespostaRecebida()); // Inicia o dialogo para mostrar a mensagem de retorno do SAT
                    } else {
                        Toast.makeText(Ativacao.this, "O Código de Ativação e a Confirmação do Código de Ativação não correspondem!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Ativacao.this, "Código de Ativação deve ter entre 8 a 32 caracteres!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void dialogoRetorno(String s) {
        //TODO Fazer algo com a String que foi retornada do sat.
        try {
            // Inicia o Alert
            AlertDialog.Builder builder = new AlertDialog.Builder(Ativacao.this);
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

