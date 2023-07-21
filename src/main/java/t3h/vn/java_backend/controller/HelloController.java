package t3h.vn.java_backend.controller;

import com.sun.net.httpserver.Headers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest;

@Controller
//@Rescontroller = @Controller + @ResponeseBody
//render ra giao diện html
//@RestController
////Trả về đúng những j được lấy ra
public class HelloController {
    @RequestMapping("index")
    public String index() {
        return "index.html";
    }

    @RequestMapping("redirect")
    public ResponseEntity redirect(@RequestParam String key) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","https://www.google.com.vn/?hl=vi");
        return new ResponseEntity(headers, HttpStatus.FOUND);
    }
}
