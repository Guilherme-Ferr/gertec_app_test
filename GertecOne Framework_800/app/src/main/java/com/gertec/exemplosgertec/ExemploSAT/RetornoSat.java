package com.gertec.exemplosgertec.ExemploSAT;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

// Este conjunto de classes são responsaveis por favor formatar a resposta recebida do SAT, possibilitando que o usuario consiga acessar as informações de uma forma mais facil, através de Getters e Setters.
// Cada ação que o SAT executa retorna uma resposta (string com pipes), mas existem algumas respostas que possuem padrões, por isto que algumas das classes abaixo servem como retorno de 1 ou mais funções.
// Toda classe a seguir recebe uma string como parâmetro (resposta completa não formatada "com pipes" obtida do SAT).

// Esta classe serve como retorno para a seguintes funções do Sat: Ativar Sat, Configurações de Rede, Alterar codigo de ativação, Bloquear sat, Desbloquear sat , atualizar software;
class RetornoDinamicoSat {
    RetornoDinamicoSat(String retornoCompleto) {
        this._retorno = retornoCompleto;
        this._dados = retornoCompleto.split("\\|");
    }

    private String _retorno; // Armazena a resposta completa não separadas por pipe
    private String[] _dados; // Armazena a o split da mensagem completa separada por pipes


    public String getRespostaCompletaPipe() {
        return this._retorno;
    }

    public String getRespostaRecebida() {
        if (_dados.length == 1)
            return _dados[0];
        return _dados[2];
    }
}

// Classe que formata a resposta obtida do Sat ao invocar a função Associar Sat
class RetornoAssociarSat {
    RetornoAssociarSat(String retornoCompleto) {
        this._retorno = retornoCompleto;
        this._dados = retornoCompleto.split("\\|");
    }

    private String _retorno; // Armazena a resposta completa não separadas por pipe
    private String[] _dados; // Armazena a o split da mensagem completa separada por pipes

    public String getRespostaCompletaPipe() {
        return this._retorno;
    }

    public String getRespostaRecebida() {
        if (_dados.length == 1) return _dados[0];
        return _dados[3];
    }

    public String getCodigoResposta() {
        return _dados[2];
    }
}

// Esta classe serve como retorno para a seguintes funções do Sat: ConsultarSat, teste fim a fim, Cancelar Última venda, Consultar numero sessao
class RetornoConsultasTesteSat {
    RetornoConsultasTesteSat(String retornoCompleto) {
        this._retorno = retornoCompleto;
        this._dados = retornoCompleto.split("\\|");
    }

    private String _retorno; // Armazena a resposta completa não separadas por pipe
    private String[] _dados; // Armazena a o split da mensagem completa separada por pipes

    public String getRespostaCompletaPipe() {
        return this._retorno;
    }

    public String getRespostaRecebida() {
        if (_dados.length == 1) return _dados[0];
        return _dados[2];
    }

    public String getCodigoResposta() {
        return _dados[1];
    }
}

// Classe que formata a resposta obtida do Sat ao invocar a função "Enviar teste vendas" Sat
class RetornoVendaTeste {
    RetornoVendaTeste(String retornoCompleto) {
        this._retorno = retornoCompleto;
        this._dados = retornoCompleto.split("\\|");
    }

    private String _retorno; // Armazena a resposta completa não separadas por pipe
    private String[] _dados; // Armazena a o split da mensagem completa separada por pipes

    public String getRespostaCompletaPipe() {
        return this._retorno;
    }

    public String getRespostaRecebida() {
        if (_dados.length == 1) return _dados[0];
        return _dados[2];
    }

    public String getCodigoResposta() {
        return _dados[1];
    }
}

// Classe que formata a resposta obtida do Sat ao invocar a função "Enviar teste vendas" Sat
class RetornoStatusSat {
    RetornoStatusSat(String retornoCompleto) {
        this._retorno = retornoCompleto;
        this._dados = retornoCompleto.split("\\|");
    }

    private String _retorno; // Armazena a resposta completa não separadas por pipe
    private String[] _dados; // Armazena a o split da mensagem completa separada por pipes

    public String getRespostaCompletaPipe() {
        return this._retorno;
    }

    public Boolean existeErroReposta(){
        if(_dados.length == 1){
            return true;
        }
        return false;
    }

    public String getEstadoDeOperacao() {
        if (_dados[27] == "0") return "DESBLOQUEADO";
        else if (_dados[27] == "1") return "BLOQUEADO SEFAZ";
        else if (_dados[27] == "2") return "BLOQUEIO CONTRIBUINTE";
        else if (_dados[27] == "3") return "BLOQUEIO AUTÔNOMO";
        else return "BLOQUEIO PARA DESATIVAÇÃO";
    }

    public String getNumeroSerieSat() {
        return _dados[5] + "\n";
    }

    public String getTipoLan() {
        return _dados[6] + "\n";
    }

    public String getIpSat() {
        return _dados[7] + "\n";
    }

    public String getMacSat() {
        return _dados[8] + "\n";
    }

    public String getMascara() {
        return _dados[9] + "\n";
    }

    public String getGateway() {
        return _dados[10] + "\n";
    }

    public String getDns1() {
        return _dados[11] + "\n";
    }

    public String getDns2() {
        return _dados[12] + "\n";
    }

    public String getStatusRede() {
        return _dados[13] + "\n";
    }

    public String getNivelBateria() {
        return _dados[14] + "\n";
    }

    public String getMemoriaDeTrabalhoTotal() {
        return _dados[15] + "\n";
    }

    public String getMemoriaDeTrabalhoUsada() {
        return _dados[16] + "\n";
    }

    public String getData() {
        return _dados[17].substring(6, 8) + "/" +_dados[17].substring(4, 6) + "/" + _dados[17].substring(0, 4) + "\n";
    }

    public String getHora() {
        return _dados[17].substring(8, 10) + ":" + _dados[17].substring(10, 12) + ":" + _dados[17].substring(12, 14) + "\n";
    }

    public String getVersao () {return _dados[18]+"\n";}
    public String getVersaoLeiaute () {return _dados[19]+"\n";}
    public String getUltimoCfeEmitido () {return _dados[20]+"\n";}
    public String getPrimeiroCfeMemoria() {return _dados[21]+"\n";}
    public String getUltimoCfeMemoria () {return _dados[22]+"\n";}

    public String getUltimaTransmissaoSefazData () {
    return _dados[23].substring(6,8) + "/"+ _dados[23].substring(4,6) + "/"+ _dados[23].substring(0,4) + "\n";}

    public String getUltimaTransmissaoSefazHora () {
        return _dados[23].substring(8,10) + ":"+ _dados[23].substring(10,12) + ":"+ _dados[23].substring(12,14) + "\n";
    }

    public String getUltimaComunicacaoSefazData (){
        return _dados[24].substring(6,8) + "/"+ _dados[24].substring(4,6) + "/"+ _dados[24].substring(0,4) + "\n";
    }

}

// Classe que formata a resposta obtida do Sat ao invocar a função "Extrair Log" Sat
class RetornoLogSat {
    RetornoLogSat(String retornoCompleto) {
        this._dados = retornoCompleto.split("\\|");
    }

    private String[] _dados; // Armazena a o split da mensagem completa separada por pipes
    public String getRespostaRecebida ()
    {
        if (_dados.length == 1) return _dados[0];
        return _dados[2];
    }

    public String getLog () throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(_dados[5], Base64.DEFAULT);
        String log = new String(decodedBytes, "UTF-8");
        return log;
    }
}
