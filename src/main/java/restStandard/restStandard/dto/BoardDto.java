package restStandard.restStandard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import restStandard.restStandard.domain.Board;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private int good;
    private LocalDateTime createdDate;

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .writer(writer)
                .good(good)
                .build();
    }
}
