package med.voll.apim.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.apim.domain.endereco.DadosEnderecoDTO;

public record DadosAtualizacaoMedicoDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        String email,
        DadosEnderecoDTO endereco
) {
}
