package com.footstep.domain.posting.controller;

import com.footstep.domain.base.BaseException;
import com.footstep.domain.base.BaseResponse;
import com.footstep.domain.base.BaseResponseStatus;
import com.footstep.domain.posting.dto.CreateCommentDto;
import com.footstep.domain.posting.service.CommentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/footstep/{posting_id}")
@Api(tags = {"댓글 API"})
public class CommentController {

    private final CommentService commentService;


    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 2005, message = "로그인이 필요합니다.")
    })

    @PostMapping("/comment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "posting_id", value = "게시물 아이디", required = true),
            @ApiImplicitParam(name = "createCommentDto", value = "댓글 내용", required = true)
    })
    @ApiResponse(code = 3031, message = "게시글이 존재하지 않습니다")
    @ApiOperation(value = "댓글 생성", notes = "해당 게시글에 댓글 달기")
    public BaseResponse<BaseResponseStatus> addComment(@PathVariable Long posting_id, @RequestBody CreateCommentDto createCommentDto) {
        try {
            commentService.addComment(createCommentDto.getContent(), posting_id);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PatchMapping("/{comment_id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "posting_id", value = "게시물 아이디", required = true),
            @ApiImplicitParam(name = "comment_id", value = "삭제할 댓글 아이디", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 3031, message = "게시글이 존재하지 않습니다."),
            @ApiResponse(code = 3041, message = "해당 댓글이 존재하지 않습니다")
    })
    @ApiOperation(value = "댓글 삭제", notes = "해당 댓글 삭제")
    public BaseResponse<BaseResponseStatus> deleteComment(@PathVariable Long posting_id, @PathVariable Long comment_id) {
        try {
            commentService.deleteComment(comment_id, posting_id);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());

        }
    }


    @GetMapping("/comments/count")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "posting_id", value = "해당 게시물 아이디", required = true),
    })
    @ApiResponse(code = 3031, message = "게시글이 존재하지 않습니다.")
    @ApiOperation(value = "댓글 개수", notes = "해당 게시물에 댓글 개수 세기")
    public BaseResponse<String> countComment(@PathVariable Long posting_id) {
        try {
            String result = commentService.count(posting_id);
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());

        }
    }
}
