package to.dolist.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private Integer age;
    private String name;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "member")
    private List<ToDo> todo = new ArrayList<>();



}
