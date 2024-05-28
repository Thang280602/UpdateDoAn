package com.otothang.controllers.home;

import com.otothang.Service.BlogService;
import com.otothang.Service.CategorySevice;
import com.otothang.Service.ChatService;
import com.otothang.Service.CommentSevice;
import com.otothang.Service.ProductSevice;
import com.otothang.Service.UserService;
import com.otothang.models.Blog;
import com.otothang.models.Category;
import com.otothang.models.Chat;
import com.otothang.models.Comment;
import com.otothang.models.CustomUserDetails;
import com.otothang.models.Product;
import com.otothang.models.SearchModel;
import com.otothang.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller

public class HomeController {
    @Autowired
    private CategorySevice categorySevice;
    @Autowired
    private ProductSevice productSevice;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentSevice commentSevice;
    @GetMapping("/success/{id}")
    public String returnHome(@PathVariable("id") Integer id){
        return "index";
    }
    @RequestMapping("")
    public String home(Model model, Principal principal) {
        List<Category> categories = categorySevice.findByCategoryStatus(true);
        model.addAttribute("listCate", categories);
        List<Category> categories1 = categorySevice.getAll();
        model.addAttribute("cate1", categories1);
        List<Product> productAll = productSevice.getAll();
        model.addAttribute("productALL", productAll);
        List<String> models = this.productSevice.findAllDistinctModels();
        model.addAttribute("model", models);
        List<String> productAddress = this.productSevice.findProductAddress();
        model.addAttribute("productAddress", productAddress);
        model.addAttribute("searchModel", new SearchModel());
        List<Blog> blog = this.blogService.getAll();
        model.addAttribute("blog", blog);
        model.addAttribute("principal", principal);
        return "index";
    }

    @PostMapping("/fillProduct")
    public String SearchProduct(Model model, @ModelAttribute("searchModel") SearchModel searchModel) {
        List<Product> listProduct = this.productSevice.SearchProduct(searchModel);
        model.addAttribute("product1", listProduct);
        List<Category> categories = categorySevice.getAll();
        model.addAttribute("listCate", categories);
        List<Product> productAll = productSevice.getAll();
        model.addAttribute("productALL", productAll);
        List<String> models = this.productSevice.findAllDistinctModels();
        model.addAttribute("model", models);
        List<String> productAddress = this.productSevice.findProductAddress();
        model.addAttribute("productAddress", productAddress);
        List<Blog> blog = this.blogService.getAll();
        model.addAttribute("blog", blog);
        return "/user/indexSearch";
    }

    @PostMapping("/searchCate")
    public String SearchCate(Model model, @Param("keyword") String keyword) {
        List<Product> pro6 = this.productSevice.searchCategory(keyword);
        List<Category> categories = categorySevice.getAll();
        model.addAttribute("listCate", categories);
        List<Product> productAll = productSevice.getAll();
        model.addAttribute("productALL", productAll);
        List<String> models = this.productSevice.findAllDistinctModels();
        model.addAttribute("model", models);
        List<String> productAddress = this.productSevice.findProductAddress();
        model.addAttribute("productAddress", productAddress);
        model.addAttribute("pro6", pro6);
        List<Blog> blog = this.blogService.getAll();
        model.addAttribute("blog", blog);
        return "/user/index3";
    }

    @RequestMapping("/user/service")
    public String Service(Model model) {
        List<Category> categories = categorySevice.getAll();
        model.addAttribute("listCate", categories);
        List<Blog> blog = this.blogService.getAll();
        model.addAttribute("blog", blog);
        return "/user/service";
    }

    @RequestMapping("/user/comingup")
    public String Comingup(Model model) {
        List<Category> categories = categorySevice.getAll();
        model.addAttribute("listCate", categories);
        return "/user/Comingup";
    }

    @GetMapping("/user/profileSetting")
    public String profileSetting(Model model, Principal principal) {
        List<Category> categories = categorySevice.getAll();
        model.addAttribute("listCate", categories);
        List<Blog> blog = this.blogService.getAll();
        model.addAttribute("blog", blog);
        if (principal == null) {
            return "/user/Login";
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = customUserDetails.getUser();
        model.addAttribute("user", user);
        return "/user/ProfileSetting";
    }

    @PostMapping("/user/profileSetting")
    public String FixprofileSetting(Model model, @ModelAttribute("user") User user, Principal principal) {
        List<Category> categories = categorySevice.getAll();
        model.addAttribute("listCate", categories);
        List<Blog> blog = this.blogService.getAll();
        model.addAttribute("blog", blog);
        this.userService.updateUserById(user.getId(), user.getFullName(), user.getEmail(), user.getTelephone(), user.getAddress());
        return "redirect:/user/profileSetting";
    }

    @RequestMapping("/user/blog/{id}")
    public String blog(@PathVariable("id") Integer id, Model model) {
        List<Comment> comments = this.commentSevice.getAll();
        model.addAttribute("comment", comments);
        List<Blog> blog = this.blogService.getAll();
        model.addAttribute("blog1", blog);
        List<Category> categories = categorySevice.getAll();
        model.addAttribute("listCate1", categories);
        List<Product> product = productSevice.getAll();
        model.addAttribute("pro", product);
        Blog blog1 = this.blogService.findById(id);
        model.addAttribute("blog", blog1);
        return "/user/Blog";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model, Principal principal) {
        List<Category> categories = categorySevice.findByCategoryStatus(true);
        model.addAttribute("listCate", categories);
        List<Category> categories1 = categorySevice.getAll();
        model.addAttribute("cate1", categories1);
        List<Product> productAll = productSevice.getAll();
        model.addAttribute("productALL", productAll);
        List<String> models = this.productSevice.findAllDistinctModels();
        model.addAttribute("model", models);
        List<String> productAddress = this.productSevice.findProductAddress();
        model.addAttribute("productAddress", productAddress);
        model.addAttribute("searchModel", new SearchModel());
        List<Blog> blog = this.blogService.getAll();
        model.addAttribute("blog", blog);
        model.addAttribute("principal", principal);
        model.addAttribute("errorMessage", "Bạn không có quyền truy cập vào trang admin.");
        return "index";
    }

    @GetMapping("/chatnay")
    public String index(Model model, Principal principal) {
        if (principal == null) {
            return "/user/Login";
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = customUserDetails.getUser();
        Chat chat = new Chat();
        chat.setUser(customUserDetails.getUser());
        model.addAttribute("user", user);
        model.addAttribute("chat1", chat);
        System.out.println(chat);
        List<Category> categories = categorySevice.findByCategoryStatus(true);
        model.addAttribute("listCate", categories);
        List<Category> categories1 = categorySevice.getAll();
        model.addAttribute("cate1", categories1);
        List<Product> productAll = productSevice.getAll();
        model.addAttribute("productALL", productAll);
        List<String> models = this.productSevice.findAllDistinctModels();
        model.addAttribute("model", models);
        List<String> productAddress = this.productSevice.findProductAddress();
        model.addAttribute("productAddress", productAddress);
        model.addAttribute("searchModel", new SearchModel());
        List<Blog> blog = this.blogService.getAll();
        model.addAttribute("blog", blog);
        model.addAttribute("principal", principal);
        return "chat1";
    }

    @PostMapping("/chat/chatnay")
    public String Chat(@ModelAttribute("chat1") Chat chat, Principal principal) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        chat.setUser(customUserDetails.getUser());
        this.chatService.create(chat);
        return "redirect:/chat";
    }
}
