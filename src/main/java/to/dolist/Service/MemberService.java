package to.dolist.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import to.dolist.domain.Member;
import to.dolist.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    //회원가입
    public Long join(Member member){
        validateDuplicateMember(member);//중복확인
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMember =memberRepository.findByEmail(member.getEmail());
        if(!findMember.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //회원조회
    public List<Member> findMembers(){return memberRepository.findAll();}
    public Member findOne(Long memberId) {return memberRepository.findOne(memberId);}
    public List<Member> findById(Long id){return  memberRepository.findById(id);}
    //변경
    @Transactional
    public void update(Long id,String name,Integer age){
        Member member = memberRepository.findOne(id);
        member.setName(name);
        member.setAge(age);
        member.setUpdatedAt(LocalDateTime.now());
    }

}
