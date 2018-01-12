package com.ross53.cobar.repository;

import com.ross53.cobar.domain.CompleteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  CompletedItemRepository extends JpaRepository<CompleteItem,Integer> {

    @Query("SELECT ci FROM CompleteItem ci WHERE ci.itemId = :itemId")
    public List<CompleteItem> findByItemId(@Param("itemId")int itemId);


}
