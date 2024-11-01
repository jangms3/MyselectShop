package com.sparta.myselectshop.repository;

import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUserAndNameIn(User user, List<String> folderNames);
    // select * from folder; = findAll
    // where user_id =? //ex)(1)
    // and
    // name in (?,?,?); // (1,2,3)

    List<Folder> findALlByUser(User user);

}
