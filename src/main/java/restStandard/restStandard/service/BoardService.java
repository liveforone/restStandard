package restStandard.restStandard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restStandard.restStandard.domain.Board;
import restStandard.restStandard.domain.Comment;
import restStandard.restStandard.dto.BoardDto;
import restStandard.restStandard.repository.BoardRepository;
import restStandard.restStandard.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @Transactional(readOnly = true)
    public Page<Board> getBoardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional
    public void savePost(BoardDto boardDto) {
        boardRepository.save(boardDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Board getBoard(Long id) {
        return boardRepository.findOneById(id);
    }

    @Transactional
    public void editBoard(Long id, BoardDto boardDto) {
        boardDto.setId(id);
        boardRepository.save(boardDto.toEntity());
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
        List<Comment> commentList = commentRepository.findByBoardId(id);
        for (Comment comment : commentList) {
            commentService.deleteComment(comment.getId());
        }
    }
}
