package restStandard.restStandard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restStandard.restStandard.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findOneById(Long id);
}
