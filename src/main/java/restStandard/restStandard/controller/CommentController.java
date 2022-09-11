package restStandard.restStandard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restStandard.restStandard.domain.Comment;
import restStandard.restStandard.dto.CommentDto;
import restStandard.restStandard.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/{boardId}/comment")
    public ResponseEntity<List<Comment>> commentHome(@PathVariable("boardId") Long boardId) {
        List<Comment> commentList = commentService.getCommentList(boardId);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @PostMapping("/api/{boardId}/comment")
    public ResponseEntity<?> commentPost(
            @PathVariable("boardId") Long boardId,
            @RequestBody CommentDto commentDto
    ) {
        commentService.saveComment(boardId, commentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/{boardId}/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
