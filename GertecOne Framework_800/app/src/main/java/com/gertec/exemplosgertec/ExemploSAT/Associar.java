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

public class Associar extends Activity {
    private TextWatcher cnpjMask;
    private Button button_associar;
    private EditText txtCodAtivacao, txtAssinatura;
    private AlertDialog alerta;
    Random gerador = new Random();
    SatLib satLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        satLib = new SatLib(this);
        setContentView(R.layout.assinatura);
        button_associar = (Button) findViewById(R.id.buttonAssociar);
        txtCodAtivacao = (EditText) findViewById(R.id.txtCodAtivacao);
        txtAssinatura = (EditText) findViewById(R.id.txtAssinatura);
        final EditText editCnpj = (EditText) findViewById(R.id.txtCNPJ);
        cnpjMask = Mask.insert("##.###.###/####-##", editCnpj);
        editCnpj.addTextChangedListener(cnpjMask);
        final EditText editCnpjSW = (EditText) findViewById(R.id.txtSW);
        cnpjMask = Mask.insert("##.###.###/####-##", editCnpjSW);
        editCnpjSW.addTextChangedListener(cnpjMask);

        button_associar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (txtCodAtivacao.getText().toString().length() >= 8) {
                    if (txtAssinatura.getText().toString().length() != 0) {
                     String resp = satLib.associarSat(editCnpj.getText().toString().replace(".", "").replace("/", "").replace("-", ""), editCnpjSW.getText().toString().replace(".","").replace("/", "").replace("-", ""), txtCodAtivacao.getText().toString(), txtAssinatura.getText().toString(), gerador.nextInt(999999));
                     RetornoAssociarSat retornoAssociarSat = new RetornoAssociarSat(resp);
                     dialogoRetorno(retornoAssociarSat.getRespostaRecebida());
                    } else {
                        Toast.makeText(Associar.this, "Assinatura AC Inválida!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Associar.this, "Código de Ativação deve ter entre 8 a 32 caracteres!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void dialogoRetorno(String s) {
        //TODO Fazer algo com a String que foi retornada do sat.
        try {
            // Inicia o Alert
            AlertDialog.Builder builder = new AlertDialog.Builder(Associar.this);
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
