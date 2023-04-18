package med.voll.api.controller;


import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
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

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest // subir o contexto intero do spring para testar o controller
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;


    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> jsonTesteDadosAgendamentoConsulta;
    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> jsonTesteDadosDetalhamentoConsulta;


    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria retornar http 400, quando informações estiverem invalídas")
    @WithMockUser
    void agenda_genario1() throws Exception {
        var response = mvc.perform(post("/consultas")).andReturn().getResponse(); //disparando sem body, então deverá da erro.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Deveria retornar http 200, quando informações estiverem valídas")
    @WithMockUser
    void agenda_genario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        var dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(null, 2L, 5L, data);

        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamentoConsulta);

        var response = mvc.perform(post("/consultas").contentType(MediaType.APPLICATION_JSON)
                .content(jsonTesteDadosAgendamentoConsulta.write(new DadosAgendamentoConsulta(2L, 5L, data, especialidade)
                        ).getJson()
                )).andReturn().getResponse(); //disparando sem body, então deverá da erro.

        var jsonEsperado = jsonTesteDadosDetalhamentoConsulta.write(dadosDetalhamentoConsulta).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }

}