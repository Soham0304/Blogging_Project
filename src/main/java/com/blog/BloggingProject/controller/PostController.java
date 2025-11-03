package com.blog.BloggingProject.controller;

import org.springframework.ui.Model;
import com.blog.BloggingProject.model.Post;
import com.blog.BloggingProject.repository.PostRepo;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


@Controller
public class PostController {

    @Autowired
    PostRepo repo;

    @GetMapping("/")
    public String viewHomePage(Model model){
        model.addAttribute("listPosts", repo.findAll());
        return "index";
    }

    @GetMapping("/new")
    public String newPost(Model model){
        model.addAttribute("post", new Post());

        return "new_post";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute("post") Post post){
        System.out.println("post Author= "+post.getAuthor());
        repo.save(post);
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable int id, Model model){
        model.addAttribute("post", repo.findById(id));
        return "edit_post";
    }


    @PostMapping("/update")
    public String updatePost(@ModelAttribute("post") Post post) throws Exception{
        Optional<Post> optPost = repo.findById(post.getId());
        if(optPost.isPresent()){
            repo.deleteById(post.getId());
            repo.save(post);
        } else{
            throw new Exception("Invalid Enrty!");
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable int id){
        repo.deleteById(id);
        return "redirect:/";
    }
}
