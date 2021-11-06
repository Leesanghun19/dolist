package to.dolist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="todo")
@Getter
@Setter
@NoArgsConstructor
public class ToDo {
    @Id
    @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;
    private String isCompleted;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private Long mId;
    //연관관계 메서드//
    public void setMember(Member member){
        this.member=member;
        member.getTodo().add(this);
    }
    //생성 매서드//

    public static ToDo createToDo(Member member,String content){
        ToDo todo = new ToDo();
        todo.setMember(member);
        todo.setContent(content);
        todo.setIsCompleted("not");
        todo.setCreateAt(LocalDateTime.now());
        todo.setMId(member.getId());
        return todo;


    }





}
