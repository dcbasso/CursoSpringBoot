package com.dante.curso.services.validation;

import com.dante.curso.domain.Cliente;
import com.dante.curso.dto.ClienteDTO;
import com.dante.curso.repositories.ClienteRepository;
import com.dante.curso.resouces.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {
    }

    @Override
    public boolean isValid(final ClienteDTO clienteDTO, ConstraintValidatorContext context) {
        final List<FieldMessage> list = new ArrayList<>();

        @SuppressWarnings("unchecked")
        final Map<String, String> map = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        final Integer clienteId = Integer.parseInt(map.get("id"));

        final Cliente clienteAux = this.clienteRepository.findByEmail(clienteDTO.getEmail());
        if (clienteAux != null && !clienteAux.getId().equals(clienteId)) {
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
