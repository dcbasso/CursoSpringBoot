package com.dante.curso.services.validation;

import com.dante.curso.domain.Cliente;
import com.dante.curso.domain.enums.TipoCliente;
import com.dante.curso.dto.ClienteNewDTO;
import com.dante.curso.repositories.ClienteRepository;
import com.dante.curso.resouces.exception.FieldMessage;
import com.dante.curso.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert constraintAnnotation) {
    }

    @Override
    public boolean isValid(final ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context) {
        final List<FieldMessage> list = new ArrayList<>();

        if (clienteNewDTO.getTipoCliente().getCodigo() == TipoCliente.PESSOA_FISICA.getCodigo() && !BR.isValidCPF(clienteNewDTO.getCpfCNPJ())) {
            list.add(new FieldMessage("cpfCNPJ", "CPF Inválido"));
        }
        if (clienteNewDTO.getTipoCliente().getCodigo() == TipoCliente.PESSOA_JURIDICA.getCodigo() && !BR.isValidCPF(clienteNewDTO.getCpfCNPJ())) {
            list.add(new FieldMessage("cpfCNPJ", "CNPJ Inválido"));
        }

        final Cliente clienteAux = this.clienteRepository.findByEmail(clienteNewDTO.getEmail());
        if (clienteAux != null) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (final FieldMessage fm : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fm.getMessage())
                    .addPropertyNode(fm.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
