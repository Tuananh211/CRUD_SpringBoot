package t3h.vn.java_backend.author;

import org.springframework.web.bind.annotation.*;
import t3h.vn.java_backend.Model.author;
import t3h.vn.java_backend.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("rest/author")
public class AuthorRestController {
   static AuthorService authorService = new AuthorService();

    @GetMapping("get") //Lấy thông tin
    public author get(@RequestParam Long id) { //Xuất hiện sau dấu ? => được sử dụng để tìm kiếm
        return authorService.getAuthorById(id);
    }

    @PostMapping("post") //Được sử dụng để tạo mới dữ liệu
    public author post(@RequestBody author o) { //Che giấu thông tin

        return authorService.createAuthor(o);
    }

    @PutMapping("put/{id}/{hoten}/{diachi}")
    public author put(@PathVariable Long id,@RequestBody author author) {// path variable xuất hiện sau dấu /

        return authorService.updateAuthorById(id,author);
    }

    @DeleteMapping("delete/{id}")
    public Boolean delete(@PathVariable Long id) {

        return authorService.deleteAuthorById(id);
    }

    @GetMapping("list")
    public List<author> list() {

        return  authorService.getAuthorList();
}
//    Viết api thêm("/add+ request body),sửa("/update"+request body),xóa("/delete/{id}"+pathvariable),
//    lấy thông tin chi tiết("get/{id}" + PathVariable)
//    Lấy thông tin chi tiết("list")
}
