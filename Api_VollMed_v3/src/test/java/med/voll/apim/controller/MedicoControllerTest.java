package med.voll.apim.controller;

import med.voll.apim.domain.endereco.DadosEnderecoDTO;
import med.voll.apim.domain.endereco.Endereco;
import med.voll.apim.domain.medico.DadosCadastroMedicoDTO;
import med.voll.apim.domain.medico.DadosDetalhamentoMedico;
import med.voll.apim.domain.medico.Especialidade;
import med.voll.apim.domain.medico.Medico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DadosCadastroMedicoDTO> dadosCadastroMedicoDTOJson;
    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoDTOJson;
    @MockBean
    private Medico medico;

    private final DadosEnderecoDTO enderecoDTO = new DadosEnderecoDTO("teste1", "teste2", "35171375", "teste3", "MG", "teste5", "teste6");
    private final Endereco endereco = new Endereco("teste1", "teste2", "35111111", "teste3", "teste4", "teste5", "MG");
    private final Especialidade especialidade = Especialidade.CARDIOLOGIA;

    @Test
    @DisplayName("Deve devolver codigo 201 quando as informações estiverem validas")
    @WithMockUser
    void medico_cenario1() throws Exception {
        var dadosCadastroMedico = new DadosDetalhamentoMedico(3l, "felipe", "felipe@felipe.com", "55 xxxxx-xxxxx", "54321", this.especialidade, this.endereco);

        var response = mvc.perform(post("/medicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosCadastroMedicoDTOJson.write(new DadosCadastroMedicoDTO(
                        "felipe",
                        "felipe@felipe.com",
                        "55 xxxxx-xxxxx",
                        "54321",
                        this.especialidade,
                        this.enderecoDTO)
                ).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = dadosDetalhamentoMedicoDTOJson.write(dadosCadastroMedico);

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve devolver codigo 400 quando as informações estiverem invalidas")
    @WithMockUser
    void medico_cenario2() throws Exception {
        var response = mvc.perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve devolver codigo 403 quando não tiver token")
    void medico_cenario3() throws Exception {
        var response = mvc.perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}