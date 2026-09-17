package backofficeapi.infrastructure.adapter.ouput.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuditableEntity
 *   Descripción: Entidad base MappedSuperclass para auditoría JPA automática (TBL_AUDITORIA)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   13.09.2026 | Camila Ledezma | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Mapeo a columnas TBL_AUDITORIA (DTFECHAC, SUSUARIOC, DTFECHAM, SUSUARIOM)
 *----------------------------------------
 */

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public abstract class AuditableEntity implements Serializable, Cloneable {

    @CreatedDate
    @Column(name = "DTFECHAC", nullable = false, updatable = false)
    protected LocalDateTime createdDate;

    @CreatedBy
    @Column(name = "SUSUARIOC", updatable = false, length = 150)
    protected String createdBy;

    @LastModifiedDate
    @Column(name = "DTFECHAM")
    protected LocalDateTime modifiedDate;

    @LastModifiedBy
    @Column(name = "SUSUARIOM", length = 150)
    protected String modifiedBy;

    @lombok.Builder.Default
    @Column(name = "DELETED", nullable = false)
    protected boolean deleted = false;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
