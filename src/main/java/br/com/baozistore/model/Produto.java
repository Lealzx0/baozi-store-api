package br.com.baozistore.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do produto e obrigatorio")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O preco e obrigatorio")
    @DecimalMin(value = "0.01", message = "O preco deve ser maior que zero")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    // Conforme o enunciado, estoque e um Boolean (disponivel ou nao).
    @NotNull(message = "Informe se o produto esta em estoque")
    @Column(nullable = false)
    private Boolean estoque;

    public Produto() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Boolean getEstoque() { return estoque; }
    public void setEstoque(Boolean estoque) { this.estoque = estoque; }
}
