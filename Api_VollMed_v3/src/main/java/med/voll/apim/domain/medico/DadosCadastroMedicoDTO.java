package med.voll.apim.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.apim.domain.endereco.DadosEnderecoDTO;

public record DadosCadastroMedicoDTO(
        @NotBlank(message = "Nome cannot be null")
        String nome,
        @NotBlank(message = "Email cannot be null")
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull
        Especialidade especialidade,
        @NotNull
        @Valid
        DadosEnderecoDTO endereco) {
}
