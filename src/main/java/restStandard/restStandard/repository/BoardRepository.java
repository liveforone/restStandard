package restStandard.restStandard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restStandard.restStandard.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findOneById(Long id);

    //== 좋아요 업데이트 ==//
    @Modifying
    @Query("update Board b set b.good = b.good + 1 where b.id = :id")
    void updateGood(@Param("id") Long id);
}
