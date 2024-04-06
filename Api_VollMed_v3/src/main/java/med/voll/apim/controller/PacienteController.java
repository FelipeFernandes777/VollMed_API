package med.voll.apim.controller;

import jakarta.validation.Valid;
import med.voll.apim.domain.paciente.*;
import med.voll.apim.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @GetMapping
    public ResponseEntity<Page<DadosListagemPacienteDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        Page<DadosListagemPacienteDTO> page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPacienteDTO::new);
        if (page != null) {
            return ResponseEntity.ok(page);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        Paciente pacientePorId = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(pacientePorId));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        if (id != null) {
            Paciente pacientePorId = repository.getReferenceById(id);
            pacientePorId.inativar();
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> criar(@RequestBody @Valid DadosCadastroPacienteDTO dados, UriComponentsBuilder uriBuilder) {
        Paciente paciente = new Paciente(dados);
        repository.save(paciente);

        //EndPoint da aplicação que recebe um objeto.id via path | Recebe o meu ID do objeto criado | transforma em URI novamente
        URI uri = uriBuilder.path("/pacientes/{id}")
                .buildAndExpand(paciente.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPacientesDTO dados, @PathVariable Long id) {
        Paciente paciente = repository.getReferenceById(id);
        paciente.atualiza(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}
