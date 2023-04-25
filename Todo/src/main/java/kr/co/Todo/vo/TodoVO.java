package kr.co.Todo.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TodoVO {
    private int no;
    private int status;
    private String content;
    private String rdate;
}
