package restStandard.restStandard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restStandard.restStandard.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoardId(Long boardId);
}
