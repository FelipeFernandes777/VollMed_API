package med.voll.apim.domain.consulta.validacoes;

import med.voll.apim.domain.consulta.DadosAgendamentoConsultaDTO;
import med.voll.apim.domain.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {
    public void validar(DadosAgendamentoConsultaDTO dados) {
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoEnceramentoDaClinica = dataConsulta.getHour() < 18;

        if (domingo || antesDaAberturaDaClinica || depoisDoEnceramentoDaClinica) {
            throw new ValidacaoException("Consulta fora do horario de funcionamento da clinica");
        }
    }

}
