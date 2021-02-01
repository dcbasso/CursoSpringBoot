package com.dante.curso.dto;

import com.dante.curso.domain.Cliente;
import com.dante.curso.services.validation.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteUpdate
public class ClienteDTO implements Serializable {

    private Integer id;

    @NotEmpty(message = "Field is required")
    @Length(min=5, max=120, message = "Field should have size 5 to 120 chars")
    private String nome;

    @NotEmpty(message = "Field is required")
    @Email(message = "Invalid e-mail")
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(final Cliente cliente) {
        this.id = cliente.getId();
        this.email = cliente.getEmail();
        this.nome = cliente.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
