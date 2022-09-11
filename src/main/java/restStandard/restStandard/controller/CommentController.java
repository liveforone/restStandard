package restStandard.restStandard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restStandard.restStandard.domain.Comment;
import restStandard.restStandard.dto.CommentDto;
import restStandard.restStandard.service.CommentService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    //== 전체 댓글 ==//
    @GetMapping("/api/{boardId}/comment")
    public ResponseEntity<List<Comment>> commentHome(
            @PathVariable("boardId") Long boardId
    ) {
        List<Comment> commentList = commentService.getCommentList(boardId);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    //== 댓글 등록 ==//
    @PostMapping("/api/{boardId}/comment")
    public ResponseEntity<?> commentPost(
            @PathVariable("boardId") Long boardId,
            @RequestBody CommentDto commentDto,
            Principal principal
    ) {
        String user = principal.getName();
        commentService.saveComment(boardId, commentDto, user);
        log.info("Comment Posting Success!!");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
