package restStandard.restStandard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restStandard.restStandard.domain.Comment;
import restStandard.restStandard.dto.CommentDto;
import restStandard.restStandard.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<Comment> getCommentList(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    @Transactional
    public void saveComment(Long id, CommentDto commentDto) {
        commentDto.setBoardId(id);
        commentRepository.save(commentDto.toEntity());
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
