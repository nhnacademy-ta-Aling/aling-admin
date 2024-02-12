package kr.aling.admin.common.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageResponseDto<T> {

    private long pageNumber;
    private long totalPages;
    private long totalElements;
    private List<T> content;
}
