package com.community.xdldm.community.controller;

import com.community.xdldm.community.mapper.QuestionMapper;
import com.community.xdldm.community.mapper.UserMapper;
import com.community.xdldm.community.model.Question;
import com.community.xdldm.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        User user = null;
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        if (title==null|| title.equals("")){
            model.addAttribute("error","未填写标题");
            return "publish";
        }
        if (description==null|| description.equals("")){
            model.addAttribute("error","未填写正文");
            return "publish";
        }
        if (tag==null|| tag.equals("")){
            model.addAttribute("error","未填写标签");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setCreator(user.getId());
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);

        return "redirect:/";

    }

}
