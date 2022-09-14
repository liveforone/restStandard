package restStandard.restStandard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import restStandard.restStandard.dto.UserDto;
import restStandard.restStandard.service.UserService;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    //== 메인 페이지 ==//
    @GetMapping("/")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 회원가입 페이지 ==//
    @GetMapping("/user/signup")
    public ResponseEntity<?> signupPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 회원가입 처리 ==//
    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto) {
        userService.joinUser(userDto);
        log.info("Sign Up Success!!!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 로그인 페이지 ==//
    @GetMapping("/user/login")
    public ResponseEntity<?> loginPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 로그인 ==//
    @PostMapping("/user/login")
    public ResponseEntity<?> loginPage(
            @RequestBody UserDto userDto,
            HttpSession session
    ) {
        userService.login(userDto, session);
        log.info("Login Success!!!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 로그인 결과 페이지 ==//
    @GetMapping("/user/login/result")
    public ResponseEntity<?> LoginResult() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 로그아웃 결과 페이지 - 로그아웃은 config에 이미 경로가 저장되어있고 성공페이지만 필요함 ==//
    @GetMapping("/user/logout/result")
    public ResponseEntity<?> Logout() {
        log.info("Logout Success!!!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 접근 거부 페이지 ==//
    @GetMapping("/user/prohibition")
    public ResponseEntity<?> prohibition() {
        return new ResponseEntity<>("접근 권한이 없습니다. 로그인하여주세요.", HttpStatus.FORBIDDEN);
    }

    //== 어드민 페이지 ==//
    @GetMapping("/admin")
    public ResponseEntity<?> admin() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 주의사항 페이지 ==//
    @GetMapping("/user/warn")
    public ResponseEntity<?> warn() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
