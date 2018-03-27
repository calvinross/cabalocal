package com.ross53.cobar.repository;

import com.ross53.cobar.domain.InfoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InfoCodeRepository extends JpaRepository<InfoCode,Integer>{


    @Query("SELECT info FROM InfoCode info WHERE info.infoStatus = :infoStatus ORDER BY info.id desc")
    List<InfoCode> getInfoCodes(@Param("infoStatus")int infoStatus);

    @Query("SELECT info FROM InfoCode info WHERE info.infoCode = :code AND info.infoStatus = 0 ORDER BY info.id desc")
    List<InfoCode> getInfoCodeByCode(@Param("code") String code);

    @Query("SELECT info FROM InfoCode info WHERE info.infoCode = :code AND (info.infoCode = 0 OR info.infoCode = 1) ")
    List<InfoCode> getUnprocessedCode(@Param("code")String code);
}
