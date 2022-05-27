package to.dolist.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import to.dolist.domain.Member;
import to.dolist.domain.ToDo;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

//testdata
@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;
    @PostConstruct
    public void init(){
        initService.dbInit();
        initService.dbInit2();

    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit(){
            Member member = createMember("홍길동","123@123.com",50);
            em.persist(member);
            ToDo toDo=ToDo.createToDo(member,"코딩");
            ToDo toDo2=ToDo.createToDo(member,"코딩2");
            em.persist(toDo);
            em.persist(toDo2);


        }
        public void dbInit2(){
            Member member = createMember("아무개","777@123.com",99);
            em.persist(member);
            ToDo toDo=ToDo.createToDo(member,"국어");
            ToDo toDo2=ToDo.createToDo(member,"수학");
            em.persist(toDo);
            em.persist(toDo2);


        }
        private Member createMember(String name,String email,Integer age) {
            Member member = new Member();
            member.setName(name);
            member.setEmail(email);
            member.setAge(age);
            member.setCreateAt(LocalDateTime.now());
            return member;
        }

    }
}
