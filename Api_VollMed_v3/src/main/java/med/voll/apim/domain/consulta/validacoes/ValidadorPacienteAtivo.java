package med.voll.apim.domain.consulta.validacoes;

import med.voll.apim.domain.consulta.DadosAgendamentoConsultaDTO;
import med.voll.apim.domain.exception.ValidacaoException;
import med.voll.apim.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsultaDTO dados) {
        if (dados.idPaciente() == null) {
            throw new RuntimeException("Id do paciente não pode ser nullo");
        }
        var pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());

        if (!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluido.");
        }
    }
}
