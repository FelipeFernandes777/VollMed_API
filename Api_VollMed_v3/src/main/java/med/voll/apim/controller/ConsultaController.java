package med.voll.apim.controller;

import jakarta.validation.Valid;
import med.voll.apim.domain.consulta.AgendaDeconsultas;
import med.voll.apim.domain.consulta.DadosAgendamentoConsultaDTO;
import med.voll.apim.domain.consulta.DadosDetalhamentoCnsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeconsultas agendaDeconsultas;

    @PostMapping
    @Transactional
    public ResponseEntity agendarConsulta(@RequestBody @Valid DadosAgendamentoConsultaDTO dados) {

        agendaDeconsultas.agendar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoCnsulta(null, null, null, null));
    }
}
