package com.itlin.communityapi.controller;

import com.itlin.communityapi.service.CommentsService;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentsService commentsService;

    /**
     * 评论列表功能
     * @param id
     * @return
     */
    @GetMapping("/article")
    public Result comments(Long id){
        return commentsService.commentsByArticleId(id);

    }

    /**
     * 评论功能
     * @param commentParam
     * @return
     */
    @PostMapping("/create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }

}
