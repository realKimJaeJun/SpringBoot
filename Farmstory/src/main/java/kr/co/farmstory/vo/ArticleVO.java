package kr.co.farmstory.vo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ArticleVO {
    private int no;
    private int parent;
    private int comment;
    private String cate;
    private String title;
    private String content;
    private int file;
    private MultipartFile fname;
    private int hit;
    private String uid;
    private String regip;
    private String rdate;
}
