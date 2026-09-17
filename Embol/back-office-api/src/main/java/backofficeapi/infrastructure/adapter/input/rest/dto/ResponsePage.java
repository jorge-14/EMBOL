package backofficeapi.infrastructure.adapter.input.rest.dto;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.function.Function;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   17.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Data
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePage<T> {

    private List<T> content;
    private Pageable pageable;
    private boolean last;
    private Integer totalPages;
    private Long totalElements;
    private Sort sort;
    private boolean first;
    private Integer numberOfElements;
    private Integer size;
    private Integer number;
    private boolean empty;

    public static <M, D> ResponsePage<D> from(Page<M> page, Function<M, D> mapper) {
        ResponsePage<D> response = new ResponsePage<>();
        response.setContent(page.getContent().stream().map(mapper).toList());
        response.setPageable(page.getPageable());
        response.setLast(page.isLast());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setSort(page.getSort());
        response.setFirst(page.isFirst());
        response.setNumberOfElements(page.getNumberOfElements());
        response.setSize(page.getSize());
        response.setNumber(page.getNumber());
        response.setEmpty(page.isEmpty());
        return response;
    }
}
