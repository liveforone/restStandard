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
import java.util.Objects;

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

    //== 댓글 삭제 ==//
    @PostMapping("/api/{boardId}/comment/delete/{id}")
    public ResponseEntity<?> deleteComment(
            @PathVariable("id") Long id,
            @RequestParam("user") String user,
            Principal principal
    ) {
        String loginUser = principal.getName();

        if (Objects.equals(user, loginUser)) {
            commentService.deleteComment(id);
            log.info("Comment Delete success!!");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.info("Comment Delete Fail");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
