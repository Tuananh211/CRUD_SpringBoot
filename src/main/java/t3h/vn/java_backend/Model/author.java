package t3h.vn.java_backend.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //tự động generate hàm getter setter
@NoArgsConstructor //tự động gennerate ra khởi tạo ko tham so
@AllArgsConstructor //tự động tạo tất cả có tham số
@Builder //tạo ra một đối tượng chỉ phải chứa 1 hay nhiều tham số
public class author {
    private Long id;
    private String name;
    private String address;
    private Integer gender;
}
