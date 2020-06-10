package com.gertec.exemplosgertec.ExemploSAT;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.gertec.exemplosgertec.R;

import java.io.File;
import java.util.Random;

public class Teste extends Activity {
    private Button button_consultar, button_status, button_teste, button_vendas, button_cancelamento, button_sessao;
    private EditText txtCodAtivacao;
    private Toast toast;
    private AlertDialog alerta;
    private File arquivoXml = new File("mnt/sdcard/Gertec/teste_fim_a_fim.xml");
    private File arquivoXmlVendas = new File("mnt/sdcard/Gertec/EnviarDadosVenda.xml");
    private File arquivoXmlCancelamento = new File("mnt/sdcard/Gertec/CancelarVenda.xml");
    Random gerador = new Random();
    SatLib satLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        satLib = new SatLib(this);
        setContentView(R.layout.teste);
        button_consultar = (Button) findViewById(R.id.buttonConsultar);
        button_status = (Button) findViewById(R.id.buttonStatus);
        button_teste = (Button) findViewById(R.id.buttonFimaFim);
        button_vendas = (Button) findViewById(R.id.buttonTesteVenda);
        button_cancelamento = (Button) findViewById(R.id.buttonCancelamento);
        button_sessao = (Button) findViewById(R.id.buttonConsultarSessao);
        txtCodAtivacao = (EditText) findViewById(R.id.txtCodAtivacao);


        button_consultar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String resp = satLib.consultarSat(gerador.nextInt(999999));
                RetornoConsultasTesteSat retornoConsultasTesteSat = new RetornoConsultasTesteSat(resp);
                dialogoRetorno(retornoConsultasTesteSat.getCodigoResposta() + " - " + retornoConsultasTesteSat.getRespostaRecebida());
            }
        });

        button_status.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (txtCodAtivacao.getText().toString().length() >= 8) {
                    String resp = satLib.consultarStatusOperacional(gerador.nextInt(999999), txtCodAtivacao.getText().toString());
                    System.out.println(resp);
                    RetornoStatusSat retornoStatusSat = new RetornoStatusSat(resp);
                    if (retornoStatusSat.existeErroReposta() == false) {
                        dialogoRetorno("Número de Série do SAT: " +
                                retornoStatusSat.getNumeroSerieSat() +
                                "Tipo de Lan: " +
                                retornoStatusSat.getTipoLan() +
                                "IP SAT: " +
                                retornoStatusSat.getIpSat() +
                                "MAC SAT: " +
                                retornoStatusSat.getMacSat() +
                                "Máscara: " +
                                retornoStatusSat.getMascara() +
                                "Gateway: " +
                                retornoStatusSat.getGateway() +
                                "DNS 1: " +
                                retornoStatusSat.getDns1() +
                                "DNS 2: " +
                                retornoStatusSat.getDns2() +
                                "Status da Rede: " +
                                retornoStatusSat.getStatusRede() +
                                "Nível da Bateria: " +
                                retornoStatusSat.getNivelBateria() +
                                "Memória de Trabalho Total: " +
                                retornoStatusSat.getMemoriaDeTrabalhoTotal() +
                                "Memória de Trabalho Usada: " +
                                retornoStatusSat.getMemoriaDeTrabalhoUsada() +
                                "Data: " +
                                retornoStatusSat.getData() +
                                "Hora: " +
                                retornoStatusSat.getHora() +
                                "Versão: " +
                                retornoStatusSat.getVersao() +
                                "Versão de Leiaute: " +
                                retornoStatusSat.getVersaoLeiaute() +
                                "Último CFe-Sat Emitido: " +
                                retornoStatusSat.getUltimoCfeEmitido() +
                                "Primeiro CFe-Sat Em Memória: " +
                                retornoStatusSat.getPrimeiroCfeMemoria() +
                                "Último CFe-Sat Em Memória: " +
                                retornoStatusSat.getUltimoCfeMemoria() +
                                "Última Transmissão de CFe-SAT para SEFAZ - Data: " +
                                retornoStatusSat.getUltimaTransmissaoSefazData() +
                                "Última Transmissão de CFe-SAT para SEFAZ - Hora: " +
                                retornoStatusSat.getUltimaTransmissaoSefazHora() +
                                "Data da Última Comunicação com a SEFAZ:" +
                                retornoStatusSat.getUltimaComunicacaoSefazData() +
                                "Estado de Operação do SAT: " +
                                retornoStatusSat.getEstadoDeOperacao());
                    }
                } else {
                    Toast.makeText(Teste.this, "Código de Ativação deve ter entre 8 a 32 caracteres!", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_teste.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View arg0) {
                if (txtCodAtivacao.getText().toString().length() >= 8) {
                    if (arquivoXml.exists()) {
                        String resp = satLib.enviarTesteFim(txtCodAtivacao.getText().toString(), gerador.nextInt(999999));
                        RetornoConsultasTesteSat retornoConsultasTesteSat = new RetornoConsultasTesteSat(resp);
                        dialogoRetorno(retornoConsultasTesteSat.getCodigoResposta() + " - " + retornoConsultasTesteSat.getRespostaRecebida());
                    } else {
                        Toast.makeText(Teste.this, "XML não existe na pasta Gertec!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Teste.this, "Código de Ativação deve ter entre 8 a 32 caracteres!", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_vendas.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View arg0) {
                if (txtCodAtivacao.getText().toString().length() >= 8) {
                    if (arquivoXmlVendas.exists()) {
                        String resp = satLib.enviarTesteVendas(txtCodAtivacao.getText().toString(), gerador.nextInt(999999));
                        RetornoConsultasTesteSat retornoConsultasTesteSat = new RetornoConsultasTesteSat(resp);
                        dialogoRetorno(retornoConsultasTesteSat.getCodigoResposta() + " - " + retornoConsultasTesteSat.getRespostaRecebida());
                    } else {
                        Toast.makeText(Teste.this, "XML não existe na pasta Gertec!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Teste.this, "Código de Ativação deve ter entre 8 a 32 caracteres!", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_cancelamento.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Teste.this);
                //define o titulo
                builder.setTitle("Atenção");
                //define a mensagem
                builder.setMessage("Digite a chave de cancelamento");
                final EditText input = new EditText(Teste.this);
                builder.setView(input);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString().length() > 0) {
                           String resp = satLib.cancelarUltimaVenda(txtCodAtivacao.getText().toString(), input.getText().toString().trim(), gerador.nextInt(999999));
                            RetornoConsultasTesteSat retornoConsultasTesteSat = new RetornoConsultasTesteSat(resp);
                            dialogoRetorno(retornoConsultasTesteSat.getCodigoResposta() + " - " + retornoConsultasTesteSat.getRespostaRecebida());
                        } else {
                            Toast.makeText(Teste.this, "Digite uma chave de cancelamento!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                //cria o AlertDialog
                alerta = builder.create();
                //Exibe
                alerta.show();
            }
        });

        button_sessao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Teste.this);
                //define o titulo
                builder.setTitle("Atenção");
                //define a mensagem
                builder.setMessage("Digite o número da sessão");
                final EditText input = new EditText(Teste.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString().length() > 0) {
                            String resp = satLib.consultarNumeroSessao(txtCodAtivacao.getText().toString(), Integer.valueOf(input.getText().toString().trim()), gerador.nextInt(999999));
                            RetornoConsultasTesteSat retornoConsultasTesteSat = new RetornoConsultasTesteSat(resp);
                            dialogoRetorno(retornoConsultasTesteSat.getCodigoResposta() + " - " + retornoConsultasTesteSat.getRespostaRecebida());
                        } else {
                            Toast.makeText(Teste.this, "Digite um número de sessão!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                //cria o AlertDialog
                alerta = builder.create();
                //Exibe
                alerta.show();
            }
        });
    }

    public void dialogoRetorno(String s) {
        //TODO Fazer algo com a String que foi retornada do sat.
        try {
            // Inicia o Alert
            AlertDialog.Builder builder = new AlertDialog.Builder(Teste.this);
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
