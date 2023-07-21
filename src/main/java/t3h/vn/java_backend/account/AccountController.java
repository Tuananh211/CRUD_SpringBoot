package t3h.vn.java_backend.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.java_backend.Model.Account;

import java.util.List;

@Controller
@RequestMapping("account")
public class AccountController {
    AccountRestController accountRestController = new AccountRestController();

    @GetMapping("get/{id}")
    public String get(@PathVariable int id, Model model){
        Account account = accountRestController.getDetialAccount(id);
        model.addAttribute("acc", account);
//        model.addAttribute("newaccount", new Account());
        return "account/detailaccount.html";
    }

//    @PostMapping ("put/{id}")
    @RequestMapping(value = "put/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public String put(@PathVariable int id, @ModelAttribute Account newaccount,RedirectAttributes model){
        accountRestController.putAccount(id,newaccount);
        model.addAttribute("message","Sửa thành công");
        return "redirect:/account/list";
    }
    @GetMapping("list")
    public String list(Model model){
        List<Account> listaccount= accountRestController.listAccount();
        model.addAttribute("accounts",listaccount);
        return "account/listAccount.html";
    }
    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes model){
        accountRestController.deleteAccount(id);
        return "redirect:/account/list";
    }

    @GetMapping ("create")
    public String create(Model model){
        model.addAttribute("account",new Account());
        return "account/create.html";
    }
    @PostMapping("save")
    public String ceateAccount(@ModelAttribute Account account, RedirectAttributes model){
        accountRestController.postAccount(account);
        model.addAttribute("message","Tạo mới " + account.getID() + " thành công!");
        return "redirect:/account/list";
    }


}
