package med.voll.apim.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoCnsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
}
