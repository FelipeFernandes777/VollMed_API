package med.voll.apim.controller;

import jakarta.validation.Valid;
import med.voll.apim.domain.medico.DadosAtualizacaoMedicoDTO;
import med.voll.apim.domain.medico.DadosCadastroMedicoDTO;
import med.voll.apim.domain.medico.DadosListagemMedicoDTO;
import med.voll.apim.domain.medico.Medico;
import med.voll.apim.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedicoDTO dados) {
        repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedicoDTO> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemMedicoDTO::new);
    }

    @PutMapping
    @Transactional
    public void atualizaDadosMedicos(@RequestBody @Valid DadosAtualizacaoMedicoDTO dados) {
        Medico medico = repository.getReferenceById(dados.id());
        medico.atualizarInfrmacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluirMedico(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
}
