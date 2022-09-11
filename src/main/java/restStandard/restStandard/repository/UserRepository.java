package restStandard.restStandard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restStandard.restStandard.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

    //== 이메일로 찾기 ==//
    Users findByEmail(String email);
}
