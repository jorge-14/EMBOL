package backofficeapi.infrastructure.adapter.input.rest.advice;

import backofficeapi.domain.enums.ErrorType;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.ExternalApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.infrastructure.adapter.input.rest.dto.ErrorHttpDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
=======
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
<<<<<<< HEAD

=======
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
import java.util.stream.Collectors;

/*
 *----------------------------------------
<<<<<<< HEAD
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ErrorHandler
 *   Descripción: Manejador global de excepciones para la API REST
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Slf4j
=======
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   07.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Log4j2
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {

    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    @ExceptionHandler(BusinessApiException.class)
    public ResponseEntity<ErrorHttpDTO> handleBusinessApiException(BusinessApiException ex) {
        log.warn("Excepción de negocio: {} - {}", ex.getErrorCode(), resolveMessage(ex.getErrorCode(), ex.getArgs()));
        return buildErrorResponse(ex.getHttpStatus(), ErrorType.BUSINESS, ex.getErrorCode(), ex.getArgs());
    }

<<<<<<< HEAD
    @ExceptionHandler(TechnicalApiException.class)
=======
    @ExceptionHandler({TechnicalApiException.class})
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
    public ResponseEntity<ErrorHttpDTO> handleTechnicalApiException(TechnicalApiException ex) {
        log.error("Excepción técnica: {} - {}", ex.getErrorCode(), ex.getMessage(), ex);
        return buildErrorResponse(ex.getHttpStatus(), ErrorType.TECHNICAL, ex.getErrorCode(), ex.getArgs());
    }

<<<<<<< HEAD
    @ExceptionHandler(ExternalApiException.class)
=======
    @ExceptionHandler({ExternalApiException.class})
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
    public ResponseEntity<ErrorHttpDTO> handleExternalApiException(ExternalApiException ex) {
        log.error("Error en servicio externo: {} - {}", ex.getErrorCode(), ex.getMessage());
        ErrorHttpDTO dto = mapExternalError(ex);
        return new ResponseEntity<>(dto, ex.getHttpStatus());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorHttpDTO> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.warn("Recurso no encontrado: {} - {}: {}", ex.getHttpMethod(), ex.getResourcePath(), ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, ErrorType.TECHNICAL, "error.resource.notFound");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorHttpDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Error de validación: {}", ex.getMessage());

        String errorDetails = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    String localizedMessage = resolveMessage(fieldError);
                    return String.format("'%s': %s", fieldError.getField(), localizedMessage);
                })
                .collect(Collectors.joining(". "));

<<<<<<< HEAD
        ErrorHttpDTO error = ErrorHttpDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .typeError(ErrorType.BUSINESS)
                .detail(errorDetails)
                .build();
=======
        ErrorHttpDTO error = new ErrorHttpDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ErrorType.BUSINESS,
                errorDetails
        );
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorHttpDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("Error de validación: {}", ex.getMessage(), ex);

        if (ex.getCause() instanceof InvalidFormatException cause) {
            if (cause.getTargetType().isEnum()) {
                return buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        ErrorType.TECHNICAL,
                        "error.enum.notValid",
<<<<<<< HEAD
                        cause.getValue());
=======
                        cause.getValue()
                );
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
            }
        }

        return buildErrorResponse(HttpStatus.BAD_REQUEST, ErrorType.TECHNICAL, "error.http.message.not.readable");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorHttpDTO> handleGenericException(Exception ex) {
        log.error("Excepción no controlada: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.TECHNICAL, "error.unexpected");
    }

<<<<<<< HEAD
    private ResponseEntity<ErrorHttpDTO> buildErrorResponse(HttpStatus status, ErrorType type, String errorCode,
            Object... args) {
        String localizedMessage = resolveMessage(errorCode, args);
        ErrorHttpDTO error = ErrorHttpDTO.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .typeError(type)
                .detail(localizedMessage)
                .build();
=======
    private ResponseEntity<ErrorHttpDTO> buildErrorResponse(HttpStatus status, ErrorType type, String errorCode, Object... args) {
        String localizedMessage = resolveMessage(errorCode, args);
        ErrorHttpDTO error = new ErrorHttpDTO(status.value(), status.getReasonPhrase(), type, localizedMessage);
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
        return new ResponseEntity<>(error, status);
    }

    private String resolveMessage(String errorCode, Object... args) {
        try {
            return messageSource.getMessage(errorCode, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
<<<<<<< HEAD
            return errorCode != null ? errorCode : "Error desconocido";
=======
            log.warn(" No se pudo resolver el mensaje para el código '{}'. Usando el código como mensaje.", errorCode, e);
            return errorCode;
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
        }
    }

    private String resolveMessage(FieldError fieldError) {
        try {
            return messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
        } catch (Exception e) {
<<<<<<< HEAD
=======
            log.warn("No se pudo resolver el mensaje para el campo '{}'. Usando mensaje por defecto.", fieldError.getField(), e);
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
            return fieldError.getDefaultMessage();
        }
    }

    private ErrorHttpDTO mapExternalError(ExternalApiException ex) {
        try {
<<<<<<< HEAD
            if (ex.getResponse() != null && !ex.getResponse().isBlank()) {
                return objectMapper.readValue(ex.getResponse(), ErrorHttpDTO.class);
            }
        } catch (JsonProcessingException e) {
            log.warn("No se pudo parsear la respuesta del servicio externo: {}", e.getMessage());
        }
        return ErrorHttpDTO.builder()
                .status(ex.getHttpStatus().value())
                .error(ex.getHttpStatus().getReasonPhrase())
                .typeError(ErrorType.EXTERNAL)
                .detail(ex.getMessage())
                .build();
    }
}
=======
            var err = objectMapper.readValue(ex.getResponse(), ErrorHttpDTO.class);
            ErrorType type = err.getTypeError() != null ? err.getTypeError() : ErrorType.TECHNICAL;
            String detail = Strings.isBlank(err.getDetail()) ? ex.getMessage() : err.getDetail();
            return new ErrorHttpDTO(ex.getHttpStatus().value(), ex.getHttpStatus().getReasonPhrase(), type, detail);
        } catch (JsonProcessingException e) {
            log.warn("NNo se pudo parsear la respuesta del servicio externo: {}", e.getMessage());
            return new ErrorHttpDTO(
                    ex.getHttpStatus().value(),
                    ex.getHttpStatus().getReasonPhrase(),
                    ErrorType.TECHNICAL,
                    ex.getMessage()
            );
        }
    }
}

>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
