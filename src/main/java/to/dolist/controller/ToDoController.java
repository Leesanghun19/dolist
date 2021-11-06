package to.dolist.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import to.dolist.Service.MemberService;
import to.dolist.Service.ToDoService;
import to.dolist.domain.ToDo;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;

    private final MemberService memberService;
    @GetMapping("/api/todo")
    public List<TodoDto> orderV3() {
        List<ToDo> orders = toDoService.findAllWIthMemberToDo();
        List<TodoDto> collect = orders.stream()
                .map(o -> new TodoDto(o))
                .collect(Collectors.toList());
        return collect;
    }
    /**
     TODO식별자조회
     */
    @GetMapping("/todos/{todoid}")
    public GetToDoResponse getToDoResponse(
            @PathVariable("todoid") Long id

    ){
        ToDo findtodo=toDoService.findOne(id);
        return new GetToDoResponse(findtodo);
    }
    /**
     TODO생성
     */
    @PostMapping("/members/{mid}/todos")
    public CreateToDoResponse saveToDo(@PathVariable("mid") Long mid,@RequestBody @Valid CreateToDoRequest request){
        ToDo toDo= new ToDo();
        toDo.setContent(request.content);
        toDo.setCreateAt(LocalDateTime.now());
        toDo.setMember(memberService.findOne(mid));
        toDo.setMId(memberService.findOne(mid).getId());
        toDo.setIsCompleted("not");
        Long id= toDoService.join(toDo);
        return  new CreateToDoResponse(id);

    }
    /**
     TODO수정
     */
    @PatchMapping("/todos/{todoid}")
    public UpateToDoResponse upateToDoResponse(
            @PathVariable("todoid") Long id,
            @RequestBody @Valid UpdateToDoRequest request
    ){
        toDoService.update(id,request.getIsCompleted());
        ToDo findtodo=toDoService.findOne(id);
        return new UpateToDoResponse(findtodo.getId(),findtodo.getIsCompleted());
    }
    /**
     TODO삭제
     */
    @DeleteMapping("/todos/{todoid}")
    public DeleteToDoResponse deleteToDoResponse(
            @PathVariable("todoid") Long id
    ){
        toDoService.deleteToDo(id);
        return new DeleteToDoResponse(id);
    }

    /**
     DTO
     */
    @Data
    @AllArgsConstructor
    static class DeleteToDoResponse{
        private Long id;

    }
    @Data
    static class GetToDoResponse{
        private String name;
        private String content;
        private Long todoId;
        private Long memberId;
        private String isCompleted;
        private LocalDateTime createAT;
        private LocalDateTime updateAt;

        public GetToDoResponse(ToDo todo){
             content=todo.getContent();
             todoId=todo.getId();
             memberId=todo.getMember().getId();
             name=todo.getMember().getName();
             isCompleted=todo.getIsCompleted();
             createAT=todo.getCreateAt();
             updateAt=todo.getUpdatedAt();
        }
    }
    @Data
    @AllArgsConstructor
    static class UpateToDoResponse {
        private Long id;
        private String isCompleted;
    }
    @Data
    @AllArgsConstructor
    static class UpdateToDoRequest {

        private String isCompleted;
        public UpdateToDoRequest(){

        }
    }

    @Data
    static class CreateToDoRequest {

        private String content;
        private Long mid;


    }
    @Data
    static class CreateToDoResponse{
        private Long id;

        public CreateToDoResponse(Long id){this.id=id;}
    }
    @Data
    static class TodoDto{
        private Long todoId;
        private String content;
        private String isCompleted;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
        private String name;
        private Long mid;

        public TodoDto(ToDo toDo){
            todoId=toDo.getId();
            name =toDo.getMember().getName();
            content=toDo.getContent();
            createAt =toDo.getCreateAt();
            isCompleted =toDo.getIsCompleted();
            mid =toDo.getMId();

        }
    }
}
