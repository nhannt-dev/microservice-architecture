package com.example.borrowingservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.borrowingservice.command.data.BorrowRepository;
import com.example.borrowingservice.command.data.Borrowing;

@Component
public class BorrowingEventsHandler {
    @Autowired
    private BorrowRepository borrowRepository;
    
    @EventHandler
    public void on(BorrowCreatedEvent event) {
        Borrowing model = new Borrowing();
        BeanUtils.copyProperties(event, model);
        borrowRepository.save(model);
    }
    
    @EventHandler
    public void on(BorrowDeletedEvent event) {
        if(borrowRepository.findById(event.getId()).isPresent()){ // isPresent: Kiểm tra dữ liệu có null không?
            borrowRepository.deleteById(event.getId());
        } else return;
    }
}