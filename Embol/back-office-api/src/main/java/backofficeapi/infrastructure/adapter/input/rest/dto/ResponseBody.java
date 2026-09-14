package backofficeapi.infrastructure.adapter.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ResponseBody
 *   Descripción: Envoltorio genérico estándar para respuestas HTTP
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBody<T> {

    @Builder.Default
    private String code = "000";
    private String message;
    private T data;

    public static <T> ResponseBody<T> success(T data) {
        return ResponseBody.<T>builder()
                .code("000")
                .message("Operación exitosa")
                .data(data)
                .build();
    }

    public static <T> ResponseBody<T> success(String message, T data) {
        return ResponseBody.<T>builder()
                .code("000")
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ResponseBody<T> error(String code, String message) {
        return ResponseBody.<T>builder()
                .code(code)
                .message(message)
                .build();
    }

    public static <T> ResponseBody<T> error(String code, String message, T data) {
        return ResponseBody.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}
