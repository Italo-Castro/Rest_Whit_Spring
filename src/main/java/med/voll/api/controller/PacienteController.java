package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import med.voll.api.domain.paciente.DadosListagemPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key") // indica que todos os metodos precisam de autenticação ... Isso para o openapi
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional

    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciete dados, UriComponentsBuilder uri) {
        var paciente = new Paciente(dados);
        repository.save(paciente);
        var url = uri.path("/medicos/${id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(new DadosDetalhamentoPaciete(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"paciente"}) Pageable paginacao) {
        var page = repository.findAll(paginacao).map((DadosListagemPaciente::new));

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInfxormacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciete(paciente));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.ok(new DadosDetalhamentoPaciete(paciente));
    }


}
