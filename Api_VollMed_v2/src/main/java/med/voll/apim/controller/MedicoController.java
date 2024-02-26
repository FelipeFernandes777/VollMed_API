package med.voll.apim.controller;

import jakarta.validation.Valid;
import med.voll.apim.domain.medico.*;
import med.voll.apim.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicoDTO dados, UriComponentsBuilder uriBuilder) {
        Medico medico = new Medico(dados);
        repository.save(medico);

        //Criaação da URI para a location do metodo POST
        var uri = uriBuilder.path("/medicos/{id}")
                .buildAndExpand(medico.getID())
                .toUri();

        //Recebe 2 parametros, 1º URI            2º O DTO de retorno
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
        //HTTP statusCode 201 - created
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicoDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        Page<DadosListagemMedicoDTO> page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicoDTO::new);

        return ResponseEntity.ok(page);
        //HTTP statusCode 200 - ok
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizaDadosMedicos(@RequestBody @Valid DadosAtualizacaoMedicoDTO dados) {
        Medico medico = repository.getReferenceById(dados.id());
        medico.atualizarInfrmacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
