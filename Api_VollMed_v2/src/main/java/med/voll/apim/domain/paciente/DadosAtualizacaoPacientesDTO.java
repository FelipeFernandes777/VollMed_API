package med.voll.apim.domain.paciente;

import med.voll.apim.domain.endereco.DadosEnderecoDTO;

public record DadosAtualizacaoPacientesDTO(String nome, String telefone, DadosEnderecoDTO endereco) {
}
