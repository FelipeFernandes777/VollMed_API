package med.voll.apim.controller;

import jakarta.validation.Valid;
import med.voll.apim.domain.paciente.*;
import med.voll.apim.repository.PacienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {
    private PacienteRepository repository;

    @GetMapping
    public ResponseEntity<Page<DadosListagemPacienteDTO>> listar(@PageableDefault(size = 10, sort = "asc") Pageable paginacao) {
        Page<DadosListagemPacienteDTO> page = repository.findAll(paginacao).map(DadosListagemPacienteDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable String id) {
        Paciente pacientePorId = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(pacientePorId));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Paciente> excluir(@PathVariable String id) {

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
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPacientesDTO dados, @PathVariable String id) {
        Paciente paciente = repository.getReferenceById(id);
        paciente.atualiza(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}
