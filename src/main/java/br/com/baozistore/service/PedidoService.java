package br.com.baozistore.service;

import br.com.baozistore.model.Pedido;
import br.com.baozistore.model.Produto;
import br.com.baozistore.repository.ClienteRepository;
import br.com.baozistore.repository.PedidoRepository;
import br.com.baozistore.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository,
                         ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    public Pedido salvar(Pedido pedido) {
        if (!clienteRepository.existsById(pedido.getClienteId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inexistente");
        }
        Produto produto = produtoRepository.findById(pedido.getProdutoId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto inexistente"));
        if (!Boolean.TRUE.equals(produto.getEstoque())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto indisponivel");
        }
        return pedidoRepository.save(pedido);
    }
}
