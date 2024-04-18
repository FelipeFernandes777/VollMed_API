package med.voll.apim.domain.medico;

public record DadosListagemMedicoDTO(String nome, String email, String crm, Long id, Especialidade especialidade) {
    public DadosListagemMedicoDTO(Medico medico) {
        this(medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getId(), medico.getEspecialidade());
    }
}
