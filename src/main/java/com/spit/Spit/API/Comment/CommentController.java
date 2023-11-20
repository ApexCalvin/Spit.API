package com.spit.Spit.API.Comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> createComment(@RequestBody CreateCommentDTO createCommentDTO) {
        if(createCommentDTO.getAccountId() == null
                || createCommentDTO.getPostId() == null
                || createCommentDTO.getMessage() == null
        ) {
            return new ResponseEntity<>("All fields are required.", HttpStatus.BAD_REQUEST);
        }

        commentService.createComment(createCommentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping("/all/desc")
//    public ResponseEntity<List<Comment>> getAllCommentsDesc() { return ResponseEntity.ok(commentServices.getAllCommentsDesc()); }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletedCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);

        if(comment != null) {
            commentService.deleteCommentById(id);
            return new ResponseEntity<>("Comment has been successfully deleted.", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
