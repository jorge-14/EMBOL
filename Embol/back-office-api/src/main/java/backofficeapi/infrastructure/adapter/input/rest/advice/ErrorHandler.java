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
import lombok.extern.slf4j.Slf4j;
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

import java.util.stream.Collectors;

/*
 *----------------------------------------
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

    @ExceptionHandler(TechnicalApiException.class)
    public ResponseEntity<ErrorHttpDTO> handleTechnicalApiException(TechnicalApiException ex) {
        log.error("Excepción técnica: {} - {}", ex.getErrorCode(), ex.getMessage(), ex);
        return buildErrorResponse(ex.getHttpStatus(), ErrorType.TECHNICAL, ex.getErrorCode(), ex.getArgs());
    }

    @ExceptionHandler(ExternalApiException.class)
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

        ErrorHttpDTO error = ErrorHttpDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .typeError(ErrorType.BUSINESS)
                .detail(errorDetails)
                .build();

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
                        cause.getValue());
            }
        }

        return buildErrorResponse(HttpStatus.BAD_REQUEST, ErrorType.TECHNICAL, "error.http.message.not.readable");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorHttpDTO> handleGenericException(Exception ex) {
        log.error("Excepción no controlada: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.TECHNICAL, "error.unexpected");
    }

    private ResponseEntity<ErrorHttpDTO> buildErrorResponse(HttpStatus status, ErrorType type, String errorCode,
                                                            Object... args) {
        String localizedMessage = resolveMessage(errorCode, args);
        ErrorHttpDTO error = ErrorHttpDTO.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .typeError(type)
                .detail(localizedMessage)
                .build();
        return new ResponseEntity<>(error, status);
    }

    private String resolveMessage(String errorCode, Object... args) {
        try {
            return messageSource.getMessage(errorCode, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return errorCode != null ? errorCode : "Error desconocido";
        }
    }

    private String resolveMessage(FieldError fieldError) {
        try {
            return messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return fieldError.getDefaultMessage();
        }
    }

    private ErrorHttpDTO mapExternalError(ExternalApiException ex) {
        try {
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
