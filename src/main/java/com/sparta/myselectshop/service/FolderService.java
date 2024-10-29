package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public void addFolders(List<String> folderNames, User user) {

        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);
        // 전체를 찾을건데 기준은 유저이고 폴더 테이블의 이름을 찾을 껀데 여러개 입니다 (in)
        List<Folder> folderList =new ArrayList<>();

        for (String folderName : folderNames) {
            if(!isExistFolderName(folderName, existFolderList)) { //->ture 일 떄
                //존재하는지 메서드를 밖으로 끄집어 내서 일치하는지 비교해야함.
                // 중복이 되지 않았네 -> false -> * ! => true 일치 하는 폴더가 없구나 그러면 중복되지 않겠네

                Folder folder = new Folder(folderName, user);//false 일 때 저장할 Folder 를 만들어야 함
                folderList.add(folder);
            } else{
                throw new IllegalArgumentException("폴더명이 중복되었습니다.");
            }
        }
        folderRepository.saveAll(folderList);
    }

    public List<FolderResponseDto> getFolders(User user) {
        List<Folder> folderList = folderRepository.findALlByUser(user);
        //user가 등록한 폴더만 가져와야 하니까 findAllByUser임
        List<FolderResponseDto> ResponseDtoList = new ArrayList<>();

        for (Folder folder : folderList) {
            ResponseDtoList.add(new FolderResponseDto(folder));
            // 생성자에 의해서 folder 를 받아와서
            // folder 의 정보를 responsedto에 필드쪽에 넣어주고 그러면서 객체가 생성되고
            // List<FolderResponseDto> 에 담길꺼임
        }
        return ResponseDtoList;
    }

    private boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
        for (Folder existFolder : existFolderList) {
            //  기존 폴더 리스트에서 폴더 네임이 있는지 확인해 봐야함.
            if(folderName.equals(existFolder.getName())) {
                return true; //같다
            }

        }
        return false; //일치하는게 없다.
    }


}
