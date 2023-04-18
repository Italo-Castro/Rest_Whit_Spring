package med.voll.api.domain.paciente;


public record DadosDetalhamentoPaciete(Long id, String nome, String email) {

    public DadosDetalhamentoPaciete(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail());
    }
}
