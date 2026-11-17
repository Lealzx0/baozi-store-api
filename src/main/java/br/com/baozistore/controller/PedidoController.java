package br.com.baozistore.controller;

import java.net.URI;
import java.util.List;

import br.com.baozistore.model.Pedido;
import br.com.baozistore.repository.PedidoRepository;
import br.com.baozistore.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoRepository repository;
    private final PedidoService service;

    public PedidoController(PedidoRepository repository, PedidoService service) {
        this.repository = repository;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Pedido> cadastrar(@Valid @RequestBody Pedido dados) {
        dados.setId(null);
        Pedido salvo = service.salvar(dados);
        return ResponseEntity.created(URI.create("/pedidos/" + salvo.getId())).body(salvo);
    }

    @GetMapping
    public List<Pedido> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @Valid @RequestBody Pedido dados) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        dados.setId(id);
        Pedido atualizado = service.salvar(dados);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
