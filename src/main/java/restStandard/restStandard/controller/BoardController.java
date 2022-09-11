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

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/api/home")
    public ResponseEntity<Page<Board>> getBoardHome(
            @PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Board> boardList = boardService.getBoardList(pageable);
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @PostMapping("/api/home")
    public ResponseEntity<?> boardPost(@RequestBody BoardDto boardDto) {
        boardService.savePost(boardDto);
        log.info("Posting Success!!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Board> boardDetail(@PathVariable("id") Long id) {
        Board board = boardService.getBoard(id);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PostMapping("/api/{id}")
    public ResponseEntity<?> boardEdit(
            @PathVariable("id") Long id,
            @RequestBody BoardDto boardDto
    ) {
        boardService.editBoard(id, boardDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/delete/{id}")
    public ResponseEntity<?> boardDelete(
            @PathVariable("id") Long id
    ) {
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
