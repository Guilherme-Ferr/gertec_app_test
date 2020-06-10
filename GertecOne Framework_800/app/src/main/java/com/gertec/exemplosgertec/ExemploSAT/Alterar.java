package com.gertec.exemplosgertec.ExemploSAT;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gertec.exemplosgertec.R;

import java.util.Random;

public class Alterar extends Activity {
    private Button button_alterar;
    private EditText txtCodAtual, txtCodNovo, txtCodConfirmar;
    private Spinner spinner;
    ArrayAdapter<String> adapter = null;
    private AlertDialog alerta;
    private int opcao = 1;
    String[] optCod = new String[]{"Código de ativação", "Código de Emergência"};
    Random gerador = new Random();
    SatLib satLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        satLib = new SatLib(this);
        setContentView(R.layout.alterar);
        txtCodAtual = findViewById(R.id.txtCodAtual);
        txtCodConfirmar = findViewById(R.id.txtCodConfirmar);
        txtCodNovo = findViewById(R.id.txtCodNovo);

        spinner = findViewById(R.id.Spinner1);
        button_alterar = findViewById(R.id.buttonAlterar);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optCod);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        button_alterar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                try {

                    if (txtCodAtual.getText().toString().length() >= 8 && txtCodNovo.getText().toString().length() >= 8) {
                        if (txtCodNovo.getText().toString().equals(txtCodConfirmar.getText().toString())) {
                            if (spinner.getSelectedItem().equals("Código de ativação")) {
                                opcao = 1;
                            } else {
                                opcao = 2;
                            }
                           String resp = satLib.trocarCodAtivacao(txtCodAtual.getText().toString(), opcao, txtCodNovo.getText().toString(), gerador.nextInt(999999));
                            RetornoDinamicoSat retornoDinamicoSat = new RetornoDinamicoSat(resp);
                            dialogoRetorno(retornoDinamicoSat.getRespostaRecebida());
                        } else {
                            Toast.makeText(Alterar.this, "O Novo Código de Ativação e a Confirmação do Novo Código de Ativação não correspondem!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Alterar.this, "Código de Ativação deve ter entre 8 a 32 caracteres!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    public void dialogoRetorno(String s) {
        //TODO Fazer algo com a String que foi retornada do sat.
        try {
            // Inicia o Alert
            AlertDialog.Builder builder = new AlertDialog.Builder(Alterar.this);
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

