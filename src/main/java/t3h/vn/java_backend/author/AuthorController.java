package t3h.vn.java_backend.author;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.java_backend.Model.author;
import t3h.vn.java_backend.service.AuthorService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("author")
public class AuthorController {
    AuthorService authorService = new AuthorService();
    @GetMapping("get/{id}")
    public String detail(@PathVariable Long id,
                         Model model// Truyền dư liệu từ controller xuống file html
    ) {
        author author = authorService.getAuthorById(id);
        model.addAttribute("a", author);
        return "author/detail.html";
    }

    @GetMapping("list")
    public String getAuthorList(Model model) {
        List<author> list = authorService.getAuthorList();
        model.addAttribute("l", list);
        return "author/list.html";
    }

    // Để tạo mới được tác giả cần 2 hàm
    //1: Load ra giao diện
    @GetMapping("create")
    public String create(Model model) {
//        List<author> list = authorService.listAuthor();
        model.addAttribute("author", new author());
        return "author/create.html";
    }

    // 2: Submit (gửi dư liệu lên từ form)
    @PostMapping("/save")
    public String createAuthor(@ModelAttribute author author,
                               RedirectAttributes model) {
        authorService.createAuthor(author);
        model.addAttribute("message", "Tạo mới " + author.getName() + " thành công!");
        return "redirect:/author/list";
    }
}
