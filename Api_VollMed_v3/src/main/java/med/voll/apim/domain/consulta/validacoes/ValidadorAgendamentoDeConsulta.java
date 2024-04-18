package med.voll.apim.domain.consulta.validacoes;

import med.voll.apim.domain.consulta.DadosAgendamentoConsultaDTO;

public interface ValidadorAgendamentoDeConsulta {
    void validar(DadosAgendamentoConsultaDTO dados);
}
