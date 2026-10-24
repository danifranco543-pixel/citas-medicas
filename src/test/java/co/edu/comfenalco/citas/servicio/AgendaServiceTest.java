package co.edu.comfenalco.citas.servicio;

import co.edu.comfenalco.citas.modelo.Cita;
import co.edu.comfenalco.citas.repositorio.CitaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendaServiceTest {
    @Mock CitaRepository repo;
    @InjectMocks AgendaService agenda;
    private static final String PROF = "Dr. Pérez";
    private static final LocalDateTime HORA = LocalDateTime.of(2030, 11, 4, 8, 0);

    @Test void reservaHorarioLibre() {
        when(repo.countByProfesionalAndFechaHoraBetween(eq(PROF), any(), any())).thenReturn(0L);
        when(repo.saveAndFlush(any(Cita.class))).thenAnswer(inv -> inv.getArgument(0));
        Cita cita = agenda.reservar(PROF, HORA, "paciente-001");
        assertThat(cita.getPacienteId()).isEqualTo("paciente-001");
        verify(repo).saveAndFlush(any(Cita.class));
    }

    @Test void rechazaHorarioOcupado() {
        when(repo.existsByProfesionalAndFechaHora(PROF, HORA)).thenReturn(true);
        assertThrows(HorarioNoDisponibleException.class,
                () -> agenda.reservar(PROF, HORA, "paciente-002"));
        verify(repo, never()).saveAndFlush(any(Cita.class));
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 10, 19})
    void aceptaMientrasQuedeCupo(long previas) {
        when(repo.countByProfesionalAndFechaHoraBetween(eq(PROF), any(), any())).thenReturn(previas);
        when(repo.saveAndFlush(any(Cita.class))).thenAnswer(inv -> inv.getArgument(0));
        assertDoesNotThrow(() -> agenda.reservar(PROF, HORA, "paciente-003"));
        verify(repo).saveAndFlush(any(Cita.class));
    }

    @Test void rechazaCuandoYaHayVeinteCitas() {
        when(repo.countByProfesionalAndFechaHoraBetween(eq(PROF), any(), any())).thenReturn(20L);
        assertThrows(CupoDiarioExcedidoException.class,
                () -> agenda.reservar(PROF, HORA, "paciente-004"));
        verify(repo, never()).saveAndFlush(any(Cita.class));
    }
}
