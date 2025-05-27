package com.ssafy.trip.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.trip.model.dto.Member;
import com.ssafy.trip.model.dto.Page;
import com.ssafy.trip.model.dto.Post;
import com.ssafy.trip.model.dto.SearchCondition;
import com.ssafy.trip.model.service.BasicMemberService;
import com.ssafy.trip.model.service.PostService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private static final long serialVersionUID = 1L;
    private final PostService pService;
    private final BasicMemberService mService;

//    protected void service(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String action = preProcessing(request, response);
//        switch (action) {
//        case "main" -> redirect(request, response, "/board/main.jsp");    
//        case "regist-post" -> forward(request, response, "/board/regist-post-form.jsp");
//        case "regist" -> regist(request, response);    
//        case "post-list" -> postList(request, response);
    
//        case "read" -> read(request, response);
//        case "update-form" -> forward(request, response, "/board/update-form.jsp");
//        case "update" -> update(request, response);
//        case "delete" -> delete(request, response);
//        
//        default -> response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404 처리
//        }
//    }
    @GetMapping("/main")
    public String gomain() {        
        return "board/main";
    }
    
    @GetMapping("regist-post")
    public String registPost() {
        return "board/regist-post-form";
    }
    
    @PostMapping("regist") 
    public String regist(HttpSession session, @RequestParam String title, @RequestParam String content) {
        int mid = ((Member) session.getAttribute("member")).getMno();
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Post post = new Post(mid, title, content, date);
        try {
        pService.regist(post);

//        postList(req,res);
        return "redirect:/board/main";
    } catch (Exception e) {
        e.printStackTrace();
        return "redirect:/error";
    }
        
    }
    
//    protected void regist(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        int mid = ((Member) req.getSession().getAttribute("member")).getMno();
//        String title = req.getParameter("title");
//        String content = req.getParameter("content");
//        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        Post post = new Post(mid, title, content, date);
//        try {
//            pService.regist(post);
//            req.getSession().setAttribute("alertMsg", "작성 완료");
//            postList(req,res);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    @GetMapping("post-list")
    public String goPost(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage, Model model) {
        Map<String, String> keyMap = Map.of("1", "name", "2", "title", "3", "content");

        String key = "content";
        String word = "";
    
        try {
            if(key==null || !key.equals("content")) {
                System.out.println("hi");
                System.out.println(key==null);
                Page<Post> page = pService.search(new SearchCondition(key, word, currentPage));
                model.addAttribute("page", page);
            } else {
                Page<Post> page = pService.search(new SearchCondition(key, word, currentPage));
                model.addAttribute("page", page);
                System.out.println(page.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("alertMsg", e.getMessage());
        }
        
        return "board/main";
    }
    @PostMapping("post-list")
    public String postList(@RequestParam String key, @RequestParam String word , @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage, Model model ) {
        Map<String, String> keyMap = Map.of("1", "name", "2", "title", "3", "content");
        System.out.println(keyMap);
        if (key != null) {
            key = keyMap.getOrDefault(key, "");
        }
        try {
            if(key==null || !key.equals("content")) {
                System.out.println("hi");
                System.out.println(key==null);
                Page<Post> page = pService.search(new SearchCondition(key, word, currentPage));
                model.addAttribute("page", page);
                System.out.println(page.getList());
            } else {
                Page<Post> page = pService.search(new SearchCondition(key, word, currentPage));
                model.addAttribute("page", page);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("alertMsg", e.getMessage());
        }
        return "board/main";
    }
//    
    
//    protected void postList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        Map<String, String> keyMap = Map.of("1", "name", "2", "title", "3", "content");
//        String key = req.getParameter("key");
//        System.out.println(keyMap);
//        if (key != null) {
//            key = keyMap.getOrDefault(key, "");
//        }
//        String word = req.getParameter("word");
//        int currentPage = req.getParameter("currentPage")==null?1:Integer.parseInt(req.getParameter("currentPage"));
//        try {
//            if(key==null || !key.equals("content")) {
//                System.out.println("hi");
//                System.out.println(key==null);
//                Page<Post> page = pService.search(new SearchCondition(key, word, currentPage));
//                req.setAttribute("page", page);
//            } else {
//                Page<Post> page = pService.searchByContent(new SearchCondition(key, word, currentPage));
//                req.setAttribute("page", page);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            req.setAttribute("alertMsg", e.getMessage());
//        }
//        forward(req,res,"/board/main.jsp");
//    }
    
    @GetMapping("read")
    public String read(@RequestParam int no, HttpSession session) {
        try {
        Post p = pService.getPost(no);
        session.setAttribute("post", p);
        System.out.println(p);
        return "board/read-post";
    } catch (Exception e) {
        e.printStackTrace();
        return "error";
    }

    }
    
//    protected void read(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        int no = Integer.parseInt(req.getParameter("no"));
//        try {
//            Post p = pService.getPost(no);
//            req.getSession().setAttribute("post", p);
//            forward(req,res,"/board/read-post.jsp");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    @GetMapping("delete")    
    public String delete(HttpSession session) {
        System.out.println("delete 옴");
        int no = ((Post) session.getAttribute("post")).getNo();
        try {
            pService.delete(no);
            session.setAttribute("alertMsg", "삭제 완료");
            
            return "redirect:/board/post-list";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
//    protected void delete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        int no = ((Post) req.getSession().getAttribute("post")).getNo();
//        try {
//            pService.delete(no);
//            req.getSession().setAttribute("alertMsg", "삭제 완료");
//            postList(req,res);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    @GetMapping("update-form")
    String goUpdateForm() {
        return "board/update-form";
    }
    
    @PostMapping("update")
    public String update(HttpSession session, @RequestParam String title, @RequestParam String content) {
        Post p = (Post)session.getAttribute("post");
        System.out.println("hi");
        System.out.println(p);
        int no = ((Post) session.getAttribute("post")).getNo();
        System.out.println("hi2");
        System.out.println(no);
//        int mid = ((Member) session.getAttribute("member")).getMno();
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Post post = new Post(no, title, content, date);
        System.out.println("안녕");
        System.out.println(post);
        try {
            pService.update(post,no);
            session.setAttribute("alertMsg", "수정 완료");
            return "redirect:/board/post-list";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
//    protected void update(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        int no = ((Post) req.getSession().getAttribute("post")).getNo();
//        int mid = ((Member) req.getSession().getAttribute("member")).getMno();
//        String title = req.getParameter("title");
//        String content = req.getParameter("content");
//        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        Post post = new Post(mid, title, content, date);
//        try {
//            pService.update(post,no);
//            req.getSession().setAttribute("alertMsg", "수정 완료");
//            postList(req,res);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}