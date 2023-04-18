package med.voll.api.domain.medico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {


    @Autowired
    MedicoRepository repository;


    @Test
    @DisplayName("Deveria devolver null quando unico médcio cadastrado não esta disponivel na data")
    void escolherMedicoAleatorioLivreCenario1() {
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        var medicoLivre = repository.escolherMedicoAleatorioLivre(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver um médico quando ele estiver disponivel na data")
    void escolherMedicoAleatorioLivreCenario2() {
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        var medicoLivre = repository.escolherMedicoAleatorioLivre(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        assertThat(medicoLivre).isNull();
    }

}
