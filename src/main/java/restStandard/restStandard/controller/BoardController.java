package restStandard.restStandard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restStandard.restStandard.domain.Board;
import restStandard.restStandard.dto.BoardDto;
import restStandard.restStandard.service.BoardService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    //== 전체 게시글 + 페이징 ==//
    @GetMapping("/api/home")
    public ResponseEntity<Page<Board>> getBoardHome(
            @PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Board> boardList = boardService.getBoardList(pageable);

        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    //== 게시글 작성 페이지 ==//
    @GetMapping("/api/post")
    public ResponseEntity<?> postPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 게시글 작성 ==//
    @PostMapping("/api/post")
    public ResponseEntity<?> boardPost(
            @RequestBody BoardDto boardDto,
            Principal principal
    ) {
        String writer = principal.getName();

        boardService.savePost(boardDto, writer);
        log.info("Posting Success!!");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //== 게시글 상세조회 ==//
    @GetMapping("/api/{id}")
    public ResponseEntity<Map<String, Object>> boardDetail(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        Map<String, Object> result = new HashMap<>();
        String writer = principal.getName();
        Board board = boardService.getBoard(id);

        result.put("writer", writer);
        result.put("board", board);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //== 게시글 수정 - 작성자가 같을때 ==//
    @PostMapping("/api/{id}")
    public ResponseEntity<?> boardEdit(
            @PathVariable("id") Long id,
            @RequestBody BoardDto boardDto,
            Principal principal
    ) {
        String writer = principal.getName();

        boardService.editBoard(id, boardDto, writer);
        log.info("Board Update Success!!");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 게시글 삭제페이지 - 작성자 같을때 ==//
    @GetMapping("/api/delete/{id}")
    public ResponseEntity<Map<String, Object>> deletePage(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        Map<String, Object> result = new HashMap<>();
        String writer = principal.getName();
        Board board = boardService.getBoard(id);

        result.put("writer", writer);
        result.put("board", board);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //== 게시글 삭제 ==//
    @PostMapping("/api/delete/{id}")
    public ResponseEntity<?> boardDelete(
            @PathVariable("id") Long id
    ) {
        boardService.deleteBoard(id);
        log.info("Board Delete Success!!");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
