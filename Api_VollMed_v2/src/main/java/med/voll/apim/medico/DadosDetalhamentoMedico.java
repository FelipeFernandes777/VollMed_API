package med.voll.apim.medico;

import med.voll.apim.domain.endereco.Endereco;
import med.voll.apim.domain.medico.Especialidade;
import med.voll.apim.domain.medico.Medico;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone,
                                      Especialidade especialidade,
                                      Endereco endereco) {

    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getID(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }

}
