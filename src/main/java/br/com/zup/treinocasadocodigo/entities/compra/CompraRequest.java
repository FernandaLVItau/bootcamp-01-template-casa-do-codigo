package br.com.zup.treinocasadocodigo.entities.compra;

import br.com.zup.treinocasadocodigo.entities.estado.Estado;
import br.com.zup.treinocasadocodigo.entities.livro.Livro;
import br.com.zup.treinocasadocodigo.entities.pais.Pais;
import br.com.zup.treinocasadocodigo.validators.cpfcnpj.CpfCnpj;
import br.com.zup.treinocasadocodigo.validators.existid.ExistId;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

public class CompraRequest {

    //Dados do comprador
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;
    @NotBlank
    @CpfCnpj
    private String documento;
    @NotBlank
    private String endereco;
    @NotBlank
    private String complemento;
    @NotBlank
    private String cidade;
    @NotNull
    @ExistId(dominioClasse = Pais.class, nomeCampo = "id")
    private Long idPais;
    @ExistId(dominioClasse = Estado.class, nomeCampo = "id")
    private Long idEstado;
    @NotBlank
    private String telefone;
    @NotBlank
    private String cep;

    //Dados da compra
    @NotNull
    @Valid
    private PedidoCompraRequest pedido;

    public CompraRequest(@NotBlank @Email String email, @NotBlank String nome, @NotBlank String sobrenome, @NotBlank String documento, @NotBlank String endereco, @NotBlank String complemento, @NotBlank String cidade, @NotNull Long idPais, Long idEstado, @NotBlank String telefone, @NotBlank String cep, @NotNull @Valid PedidoCompraRequest pedido) {
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.documento = documento;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.idPais = idPais;
        this.idEstado = idEstado;
        this.telefone = telefone;
        this.cep = cep;
        this.pedido = pedido;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public Long getIdPais() {
        return idPais;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }

    public PedidoCompraRequest getPedido() {
        return pedido;
    }

    public void setPedido(PedidoCompraRequest pedido) {
        this.pedido = pedido;
    }

    @Override
    public String toString() {
        return "CompraRequest{" +
                "email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", documento='" + documento + '\'' +
                ", endereco='" + endereco + '\'' +
                ", complemento='" + complemento + '\'' +
                ", cidade='" + cidade + '\'' +
                ", idPais=" + idPais +
                ", idEstado=" + idEstado +
                ", telefone='" + telefone + '\'' +
                ", cep='" + cep + '\'' +
                ", pedido={" + pedido + "}" +
                '}';
    }

    //1
    public Compra toModel(EntityManager manager, List<ItensCompra> itens){

        //1
        Pais pais = manager.find(Pais.class, this.idPais);
        //1
        Estado estado = null;
        //1
        if(this.idEstado != null) {
            estado = manager.find(Estado.class, this.idEstado);
        }

        //1
        return new Compra(this.email, this.nome, this.sobrenome, this.documento,
                this.endereco, this.complemento, this.cidade, pais, estado,
                this.telefone, this.cep, itens, this.pedido.getTotal());
    }
}