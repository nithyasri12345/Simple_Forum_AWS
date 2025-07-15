package com.example.demo.controller;

import com.example.demo.model.Comments;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repo.CommentsRepo;
import com.example.demo.repo.PostRepo;
import com.example.demo.repo.UserRepo;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentsRepo commentRepo;

   

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        if (userRepo.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "signup";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userRepo.save(user);
        session.setAttribute("user", user);

        return "redirect:/home";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            session.setAttribute("user", user.get());
            return "redirect:/home";
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    

    @GetMapping("/home")
    public String showHome(@RequestParam(value = "search", required = false) String search,
                           Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Post> posts;
        if (search != null && !search.isEmpty()) {
            posts = postRepo.findByTitleContainingIgnoreCaseOrderByIdDesc(search);
        } else {
            posts = postRepo.findAllByOrderByIdDesc();
        }

        model.addAttribute("posts", posts);
        return "home";
    }

    

    @PostMapping("/posts")
    public String createPostFromForm(@RequestParam String title,
                                     @RequestParam String content,
                                     HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(user);                    
       
        postRepo.save(post);

        return "redirect:/home";
    }

    @GetMapping("/posts/view/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Optional<Post> postOpt = postRepo.findById(id);

        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            model.addAttribute("post", post);
            model.addAttribute("comments", commentRepo.findByPostId(id));
            return "viewPost";
        }

        return "redirect:/home";
    }

    @PostMapping("/posts/{postId}/comments")
    public String addComment(@PathVariable Long postId,
                             @RequestParam String content,
                             HttpSession session) {

        User user = (User) session.getAttribute("user");

        Optional<Post> postOpt = postRepo.findById(postId);
        if (postOpt.isPresent() && user != null) {
            Comments comment = new Comments();
            comment.setContent(content);
            comment.setPost(postOpt.get());
            comment.setCommenter(user); 
            
              

            commentRepo.save(comment);
        }

        return "redirect:/posts/view/" + postId;
    }
    @PostMapping("/posts/{id}/resolve")
    public String markResolved(@PathVariable Long id, HttpSession session) {
        Optional<Post> postOpt = postRepo.findById(id);
        User currentUser = (User) session.getAttribute("user");

        if (postOpt.isPresent() && currentUser != null) {
            Post post = postOpt.get();
            if (post.getAuthor().getId().equals(currentUser.getId())) {
                post.setResolved(true);
                postRepo.save(post);
            }
        }

        return "redirect:/posts/view/" + id;
    }


}
