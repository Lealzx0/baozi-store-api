package br.com.baozistore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // IDs enviados no JSON, como solicitado no enunciado.
    @NotNull(message = "O clienteId e obrigatorio")
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @NotNull(message = "O produtoId e obrigatorio")
    @Column(name = "produto_id", nullable = false)
    private Long produtoId;

    @NotNull(message = "A quantidade e obrigatoria")
    @Min(value = 1, message = "A quantidade deve ser no minimo 1")
    @Column(nullable = false)
    private Integer quantidade;

    // Relacionamentos reais no banco, sem exigir objetos aninhados no JSON.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", insertable = false, updatable = false,
                foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", insertable = false, updatable = false,
                foreignKey = @ForeignKey(name = "fk_pedido_produto"))
    private Produto produto;

    public Pedido() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    // Sem getters de cliente/produto: API expoe apenas os IDs, evitando ciclos JSON.
}
