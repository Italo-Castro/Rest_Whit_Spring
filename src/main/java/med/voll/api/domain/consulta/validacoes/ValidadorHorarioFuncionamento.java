package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoConsultas{

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaClinica = dataConsulta.getHour() < 7;
        var depoisDoExpedienteClinica = dataConsulta.getHour() > 18;

        if (domingo || antesDaAberturaClinica || depoisDoExpedienteClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clinica");
        }

    }
}
