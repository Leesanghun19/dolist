package to.dolist.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import to.dolist.Service.MemberService;
import to.dolist.domain.Member;
import to.dolist.domain.ToDo;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     회원리스트조회
     */
    @GetMapping("/members")
    public Result memberfindall(){
        List<Member> findMembers=memberService.findMembers();
        List<MemberDto>collect = findMembers.stream()
                .map(m->new MemberDto(m.getName(),m.getId()))
                .collect(Collectors.toList());
        return new Result(collect);
    }
    /**
     회원등록
     */
    @PostMapping("/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        member.setAge(request.age);
        member.setEmail(request.email);
        member.setCreateAt(LocalDateTime.now());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    /**
     회원수정
     */
    @PatchMapping("/members/{id}")
    public UpdateMemberResponse updateMember(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request
    ){
        memberService.update(id, request.getName(),request.getAge());
        Member findMember = memberService.findOne(id);

        return new UpdateMemberResponse(findMember.getId(),findMember.getName(),findMember.getAge());
    }
    /**
     회원단건조회
     */
    @GetMapping("/members/{id}")
    public Result findoneMember(
            @PathVariable("id") Long id

    ){

        List<Member> findMembers=memberService.findById(id);
        List<MemberToDo>collect = findMembers.stream()
                .map(m->new MemberToDo(m))
                .collect(Collectors.toList());
        return new Result(collect);

    }

    /**
     DTO
     */

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
        @NotEmpty
        private String email;
        private Integer age;


    }
    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
        private Integer age;
    }

    @Data
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;

        private Integer age;
    }
    @Data
    @AllArgsConstructor
    private class MemberDto{
        private String name;
        private Long id;
    }
    @Data
    @AllArgsConstructor
    private class MemberToDo{
        private Long id;
        private String name;
        private Integer age;
        private String email;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
        private List<TodoListDto> todolist;

        public MemberToDo(Member member){
            id=member.getId();
            age=member.getAge();
            email=member.getEmail();
            createAt=member.getCreateAt();
            updateAt=member.getUpdatedAt();
            name=member.getName();
            todolist=member.getTodo().stream()
                    .map(todo->new TodoListDto(todo))
                    .collect(Collectors.toList());
        }

    }
    @Getter
    static class TodoListDto{
        private Long todoId;
        private String content;
        private String isCompleted;
        private LocalDateTime createAT;
        private LocalDateTime updateAt;
        public TodoListDto(ToDo todo){
            content=todo.getContent();
            todoId=todo.getId();
            isCompleted=todo.getIsCompleted();
            createAT=todo.getCreateAt();
            updateAt=todo.getUpdatedAt();
        }
    }

}
