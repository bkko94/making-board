package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

  //글 작성
    public void write(Board board, MultipartFile file) throws Exception{
        //project path담기
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\anotherfiles";
        //System.getProperty("user.path") + "\\src/main/resources/static/files";

       UUID uuid = UUID.randomUUID();
      //  uuid : 식별자
       String fileName = uuid + "_" + file.getOriginalFilename();


        File saveFile = new File(projectPath, fileName);
        System.out.println(saveFile);
        file.transferTo(saveFile);

      //  File saveFile = new File(projectPath, fileName);
       // file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("\\anotherfiles\\" + fileName);
        System.out.println(projectPath);
        System.out.println(fileName);
     boardRepository.save(board);

    }

//게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){

        return boardRepository.findAll(pageable);
    }

//특정 게시글 불러오기
    public Board boardView(Integer id){

        return  boardRepository.findById(id).get();
    }


    //특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }



}




