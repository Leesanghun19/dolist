package to.dolist.repository;

import org.springframework.stereotype.Repository;
import to.dolist.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;
    //저장
    public void save(Member member){em.persist(member);}
    //단건조회
    public Member findOne(Long id){return em.find(Member.class,id);}
    //조회
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    //아이디조회
    public List<Member> findById(Long id){
        return em.createQuery("select m from Member m where m.id = :id"

                        ,Member.class)
                .setParameter("id",id)
                .getResultList();
    }
    //이메일조회
    public List<Member> findByEmail(String email){
        return em.createQuery("select m from Member m where m.email = :email",Member.class)
                .setParameter("email",email)
                .getResultList();
    }
}
