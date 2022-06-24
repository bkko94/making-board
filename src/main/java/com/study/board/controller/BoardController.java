package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/boardwrite
    //어떤 url로 접근할건지 지정해주는 어노테이션
    public String boardWriteForm(){

        return "boardwrite";
    }



    @PostMapping("/board/writePro")
   // public String boardWritePro(String title, String content){
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception {   //board라는 클래스,엔티티를 만들어주어서 이걸로 사용
        // System.out.println("제목 : " + title);
        // System.out.println("내용 : " + content);복
         System.out.println(board.getTitle()); //롬복사용
         boardService.write(board, file);


       model.addAttribute("message", "글 작성이 완료되었습니다.");
       model.addAttribute("searchUrl", "/board/list");



        return "message";
    }


    @GetMapping("/board/list")
                                                        //page:default 페이지, size:한 페이의 게시글 수
    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort ="id", direction = Sort.Direction.DESC) Pageable pageable){
        //sort:정렬 기준 컬럼, direction : 정렬 순서

        Page<Board> list = boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1; //pageable은 0부터 시작하기때문에 1을 더해준다.
        int startPage = Math.max(nowPage - 4, 1);   //매개 변수로 들어온 두 값을 비교해서 높은 값을 반환하게 됨. 만일 nowpage에서 음수가 나오게 되는 경우 1로 반환
        int endPage = Math.min(nowPage + 5, list.getTotalPages());    //만약 5를 더했는데 totalpages보다 크면 totalpage를 반환하도로 함.

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boardlist";
    }


    @GetMapping("/board/view") //localhost:8080/board/view?id=1
        public String boardview(Model model, Integer id){

        //넘겨주기
        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
        }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }


    @GetMapping("/board/modify/{id}") //수정
    public String boardModify(@PathVariable("id") Integer id, Model model){

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model,
                              MultipartFile file) throws Exception{
        Board boardTemp = boardService.boardView(id);   //기존의 글이 담겨옴
        boardTemp.setTitle(board.getTitle());           //제목 가져오기
        boardTemp.setContent(board.getContent());       //내용 가져오기

        boardService.write(boardTemp, file);
        model.addAttribute("message","게시글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl","/board/list");

        return "message";
    }


}