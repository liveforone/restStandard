package restStandard.restStandard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import restStandard.restStandard.domain.Board;
import restStandard.restStandard.domain.Comment;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String user;
    private String content;
    private Long boardId;
    private LocalDateTime createdDate;

    public Comment toEntity() {
        return Comment.builder()
                .id(id)
                .user(user)
                .content(content)
                .boardId(boardId)
                .build();
    }
}
