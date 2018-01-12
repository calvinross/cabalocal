package com.ross53.cobar.service;

import com.ross53.cobar.domain.CompleteItem;
import com.ross53.cobar.repository.CompletedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletedItemService {

    @Autowired
    CompletedItemRepository completedItemRepository;

    public CompleteItem getCompleteItemById(Integer id){

        return completedItemRepository.findOne(id);
    }

    public List<CompleteItem> getCompleteItemsbyItemId(Integer itemId){

        return completedItemRepository.findByItemId(itemId);
    }

    public CompleteItem saveCompletetedItem(CompleteItem completeItem){
         return completedItemRepository.save(completeItem);
    }


}
