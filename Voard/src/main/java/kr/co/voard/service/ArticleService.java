package kr.co.voard.service;

import kr.co.voard.dao.ArticleDAO;
import kr.co.voard.repository.ArticleRepo;
import kr.co.voard.vo.ArticleVO;
import kr.co.voard.vo.FileVO;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class ArticleService {

    @Autowired
    private ArticleDAO dao;

    @Autowired
    private ArticleRepo repo;

    public List<ArticleVO> selectArticles(int start, String cate){
        return dao.selectArticles(start, cate);
    }

    public ArticleVO selectArticle(int no) {
        return dao.selectArticle(no);
    }

    @Transactional
    public int insertArticle(HttpServletRequest req, ArticleVO vo) {

        String regip = req.getRemoteAddr();
        vo.setRegip(regip);
        // 글 등록
        int result = dao.insertArticle(vo);
/*
        // 파일 업로드
        FileVO fvo = fileUpload(vo);

        if(fvo != null) {
            // 파일 등록
            dao.insertFile(fvo);
        }
*/
        return result;
    }

    public int updateArticle(ArticleVO vo) {

        int result = dao.updateArticle(vo);

        FileVO fvo = fileUpload(vo);

        if(fvo != null) {
            // 파일 등록
            dao.insertFile(fvo);
        }

        return result;
    }

    public int deleteArticle(int no) {
        return dao.deleteArticle(no);
    }

    @Transactional
    public FileVO selectFile(int fno) {
        FileVO vo = dao.selectFile(fno);
        dao.updateDownload(fno);

        return vo;
    }


    /**
     * 파일 업로드 메서드
     */
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public FileVO fileUpload(ArticleVO vo) {
        // 첨부 파일
        MultipartFile file = vo.getFname();
        FileVO fvo = null;

        if(!file.isEmpty()) {
            // 시스템 경로
            String path = new File(uploadPath).getAbsolutePath();

            // 새 파일명 생성
            String oName = file.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));
            String nName = UUID.randomUUID().toString()+ext;

            // 파일 저장
            try {
                file.transferTo(new File(path, nName));
            } catch (IllegalStateException e) {
                log.error(e.getMessage());
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            fvo = FileVO.builder()
                    .parent(vo.getNo())
                    .oriName(oName)
                    .newName(nName)
                    .build();
        }

        return fvo;
    }

    public ResponseEntity<Resource> fileDownload(FileVO vo) throws IOException {
        Path path = Paths.get(uploadPath+vo.getNewName());
        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename(vo.getOriName(), StandardCharsets.UTF_8)
                .build());
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    // 페이지 시작값
    public int getLimitStart(int currentPage) {
        return (currentPage - 1) * 10;
    }

    // 현재 페이지
    public int getCurrentPage(String pg) {
        int currentPage = 1;
        if(pg != null) {
            currentPage = Integer.parseInt(pg);
        }
        return currentPage;
    }

    // 전체 게시물 갯수
    public long getTotalCount() {
        return dao.selectCountTotal();
    }

    // 마지막 페이지 번호
    public int getLastPageNum(long total) {
        int lastPage = 0;

        if(total % 10 == 0) {
            lastPage = (int) (total/10);
        }else {
            lastPage = (int) (total/10) + 1;
        }
        return lastPage;
    }

    // 페이지 시작 번호
    public int getPageStartNum(long total, int start) {
        return (int) (total - start);
    }

    // 페이지 그룹
    public int[] getPageGroup(int currentPage, int lastPage) {
        int groupCurrent = (int) Math.ceil(currentPage/10.0);
        int groupStart = (groupCurrent - 1) * 10 + 1;
        int groupEnd = groupCurrent * 10;

        if(groupEnd > lastPage) {
            groupEnd = lastPage;
        }

        int[] groups = {groupStart, groupEnd};

        return groups;
    }
}
