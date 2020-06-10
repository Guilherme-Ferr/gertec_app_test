package com.gertec.exemplosgertec.ExemploSAT;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.phi.gertec.sat.satger.SatGerLib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SatLib  {
    public static SatGerLib serialComms;
    private File arquivoXml = new File("mnt/sdcard/Gertec/teste_fim_a_fim.xml");
    private File arquivoXmlVendas = new File("mnt/sdcard/Gertec/EnviarDadosVenda.xml");
    private File arquivoXmlCancelamento = new File("mnt/sdcard/Gertec/CancelarVenda.xml");
    private Context myContext;
    public SatLib(Context context){
        myContext = context;
        serialComms = new SatGerLib(context, dataReceivedListener); // Inicializando
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String enviarConfRede(int random, String[] dadosXml, String codigoAtivacao) throws IOException {
        String caminhoXML = "data/data/com.gertec.exemplosgertec/configRede.xml";
        File file = new File(caminhoXML);
        // Se o arquivo não existe, então cria
        if (!file.exists()) {
            file.createNewFile();
        }
        // Começa a escrever no arquivo
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bw.newLine();
        bw.write("<config>");
        bw.newLine();
        bw.write("<tipoInter>ETHE</tipoInter>");
        bw.newLine();
        bw.write(dadosXml[0]);
        bw.newLine();

        if (!dadosXml[1].equals("")) {
            bw.write(dadosXml[1]);
            bw.newLine();
        }
        if (!dadosXml[2].equals("")) {
            bw.write(dadosXml[2]);
            bw.newLine();
        }
        if (!dadosXml[3].equals("")) {
            bw.write(dadosXml[3]);
            bw.newLine();
        }
        if (!dadosXml[4].equals("")) {
            bw.write(dadosXml[4]);
            bw.newLine();
        }
        if (!dadosXml[5].equals("")) {
            bw.write(dadosXml[5]);
            bw.newLine();
        }

        bw.write(dadosXml[6]);
        bw.newLine();

        if (!dadosXml[7].equals("")) {
            bw.write(dadosXml[7]);
            bw.newLine();
        }
        if (!dadosXml[8].equals("")) {
            bw.write(dadosXml[8]);
            bw.newLine();
        }
        if (!dadosXml[9].equals("")) {
            bw.write(dadosXml[9]);
            bw.newLine();
        }
        if (!dadosXml[10].equals("")) {
            bw.write(dadosXml[10]);
            bw.newLine();
        }

        bw.write("</config>");
        bw.flush();
        bw.close();
        // Chama o método para enviar a configuração de rede
        try {
            String configData;
            // Abre o arquivo XML
            BufferedReader br = new BufferedReader(new FileReader(caminhoXML));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            // Lê linha por linha
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            configData = sb.toString();
            return retornoDaFuncaoSat(serialComms.ConfigurarInterfaceDeRede(random, codigoAtivacao, configData));
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public String versao() {
        try {
            return retornoDaFuncaoSat(serialComms.VersaoDllGerSAT());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String atualizarSoftware(String codAtivacao, int random) {
        try {
            return retornoDaFuncaoSat(serialComms.AtualizarSAT(random, codAtivacao));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String extrairLog(String codAtivacao, int random) {
        try {
            return retornoDaFuncaoSat(serialComms.ExtrairLogs(random, codAtivacao));
        } catch (Exception e) {
           return e.getMessage();
        }
    }

    public String desbloquearSat(String codAtivacao, int random) {
        try {
            return retornoDaFuncaoSat(serialComms.DesbloquearSAT(random, codAtivacao));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String bloquearSat(String codAtivacao, int random) {
        try {
           return retornoDaFuncaoSat(serialComms.BloquearSAT(random, codAtivacao));
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public String trocarCodAtivacao(String codAtual, int opt, String codNovo,int random) {
        try {
            // Sempre o codigo de confirmação vai ser igual ao novo, pois já foi validado no flutter
          return retornoDaFuncaoSat(serialComms.TrocarCodigoDeAtivacao(random, codAtual, opt, codNovo, codNovo));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String consultarNumeroSessao(String codAtivacao, int cNumeroDeSessao, int random) {
        try {
            return retornoDaFuncaoSat(serialComms.ConsultarNumeroSessao(random, codAtivacao, cNumeroDeSessao));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String cancelarUltimaVenda(String codAtivacao, String chave, int random) {
        try {
            String vendaData;
            // Carrega o XML
            BufferedReader br = new BufferedReader(new FileReader(arquivoXmlCancelamento));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            vendaData = sb.toString();
            return retornoDaFuncaoSat(serialComms.CancelarUltimaVenda(random, codAtivacao, chave, vendaData));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String enviarTesteVendas(String codAtivacao, int random) {
        try {
            String vendaData;
            // Carrega o XML
            BufferedReader br = new BufferedReader(new FileReader(arquivoXmlVendas));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            vendaData = sb.toString();
            return retornoDaFuncaoSat(serialComms.EnviarDadosVenda(random, codAtivacao, vendaData));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String enviarTesteFim(String codAtivacao, int random) {
        try {
            String vendaData;
            // Carrega o XML
            BufferedReader br = new BufferedReader(new FileReader(arquivoXml));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            vendaData = sb.toString();
            return retornoDaFuncaoSat(serialComms.TesteFimAFim(random, codAtivacao, vendaData));
        } catch (Exception e) {
            return  e.getMessage();
        }
    }

    public String consultarStatusOperacional(int random, String codAtivacao) {
        try {
            return retornoDaFuncaoSat( serialComms.ConsultarStatusOperacional(random, codAtivacao));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String consultarSat(int random) {
        try {
            return retornoDaFuncaoSat(serialComms.ConsultarSAT(random));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String associarSat(String cnpj, String cnpjSW, String codAtivacao, String assinatura, int random) {
        try {
            return retornoDaFuncaoSat(serialComms.AssociarAssinatura(random, codAtivacao, cnpjSW + "" + cnpj, assinatura));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String ativarSat(String codAtivacao, String cnpj, int random) {
        try {
            System.out.println("ENTROUUU");
            String resposta = retornoDaFuncaoSat(serialComms.AtivarSAT(random, 1, codAtivacao, cnpj, 35));
            System.out.println(resposta);
            return resposta;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String retornoDaFuncaoSat(String s) {
        //TODO Fazer algo com a String que foi retornada do sat.
        try {
            return s;
        } catch (Exception e)    {
            return e.getMessage();
        }
    }
    SatGerLib.OnDataReceived dataReceivedListener = new SatGerLib.OnDataReceived() {
        public void onError(Exception e) {
            System.out.println(e.getMessage());
        }
        @Override
        public void onReceivedDataUpdate(String s) {
            //TODO Fazer algo com a String que foi retornada do sat.
            try {
                    // Trata o retorno da função
                    String[] respostaSplited;
                    respostaSplited = s.split("\\|");
                    byte ptext[] = respostaSplited[2].getBytes();
                    String value = new String(ptext, "UTF-8");
            } catch (Exception e) {
            }
        }
    };
}
