package med.voll.apim.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.apim.domain.endereco.DadosEnderecoDTO;

public record DadosCadastroPacienteDTO(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String cpf,
        String telefone,
        @NotNull
        @Valid
        DadosEnderecoDTO endereco) {
}
