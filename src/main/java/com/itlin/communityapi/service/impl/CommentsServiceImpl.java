package com.itlin.communityapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itlin.communityapi.dao.mapper.CommentMapper;
import com.itlin.communityapi.dao.pojo.Comment;
import com.itlin.communityapi.dao.pojo.User;
import com.itlin.communityapi.service.CommentsService;
import com.itlin.communityapi.service.UserService;
import com.itlin.communityapi.util.UserThreadLocal;
import com.itlin.communityapi.vo.CommentVo;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.UserVo;
import com.itlin.communityapi.vo.params.CommentParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserService userService;
    @Override
    public Result commentsByArticleId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    @Override
    public Result comment(CommentParam commentParam) {
        User user = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setCommentatorId(user.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));

        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment , commentVo);
        //评论者信息
        Long commentatorId = comment.getCommentatorId();
        UserVo userVo = this.userService.findUserVoById(commentatorId);
        commentVo.setCommentator(userVo);
        //子评论
        Integer level = comment.getLevel();
        if(1 == level){
            Long id = comment.getId();
            List<CommentVo>  commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        //给别人评论
        if(level > 1){
            Long toUid = comment.getToUid();
            UserVo toUserVo = this.userService.findUserVoById(toUid);
            commentVo.setCommentator(toUserVo);


        }
        return commentVo;

    }
    private List<CommentVo> findCommentsByParentId(Long id){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);

        return copyList(commentMapper.selectList(queryWrapper));

    }

}
