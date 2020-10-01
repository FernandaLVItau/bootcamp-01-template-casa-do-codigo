package br.com.zup.treinocasadocodigo.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NovoAutorRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    @Size(max=400)
    private String descricao;

    public NovoAutorRequest(@Email @NotBlank String email, @NotBlank String nome, @NotBlank @Size(max = 400) String descricao) {
        this.email = email;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Autor toModel() {
        return new Autor(this.nome, this.email, this.descricao);
    }
}
