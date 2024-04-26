package com.cibertec.evaluacion.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultadoResponse {
    private boolean respuesta;
    private String mensaje;
}
