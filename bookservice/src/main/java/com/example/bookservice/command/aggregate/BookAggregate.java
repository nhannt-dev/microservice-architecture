package com.example.bookservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.example.bookservice.command.command.CreateBookCommand;
import com.example.bookservice.command.command.DeleteBookCommand;
import com.example.bookservice.command.command.UpdateBookCommand;
import com.example.bookservice.command.event.BookCreatedEvent;
import com.example.bookservice.command.event.BookDeletedEvent;
import com.example.bookservice.command.event.BookUpdatedEvent;
import com.example.commonservice.command.UpdateStatusBookCommand;
import com.example.commonservice.event.BookUpdateStatusEvent;

@SuppressWarnings("all")
@Aggregate
public class BookAggregate {
    @AggregateIdentifier
    private String bookId;
    private String name, author;
    private Boolean isReady;

    public BookAggregate() {
    }

    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand) {
        // Command luôn gắn liền với event
        BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
        // Chép toàn bộ thuộc tính từ createBookCommand vào bookCreatedEvent
        BeanUtils.copyProperties(createBookCommand, bookCreatedEvent);
        // bookCreatedEvent sẽ được phát qua event sourcing tương ứng
        AggregateLifecycle.apply(bookCreatedEvent);
    }

    @CommandHandler
    public void handle(UpdateBookCommand updateBookCommand) {
        BookUpdatedEvent bookUpdatedEvent = new BookUpdatedEvent();
        BeanUtils.copyProperties(updateBookCommand, bookUpdatedEvent);
        AggregateLifecycle.apply(bookUpdatedEvent);
    }

    @CommandHandler
    public void handle(DeleteBookCommand deleteBookCommand) {
        BookDeletedEvent bookDeletedEvent = new BookDeletedEvent();
        BeanUtils.copyProperties(deleteBookCommand, bookDeletedEvent);
        AggregateLifecycle.apply(bookDeletedEvent);
    }
    
    @CommandHandler
    public void handle(UpdateStatusBookCommand command) {
        BookUpdateStatusEvent event = new BookUpdateStatusEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent event) {
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
        this.name = event.getName();
    }
    // Sau khi EventSourcingHandler được xử lí, tiếp tục nhảy vào EventHandler để đánh dấu component và lưu DB

    @EventSourcingHandler
    public void on(BookUpdatedEvent event) {
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
        this.name = event.getName();
    }
    
    @EventSourcingHandler
    public void on(BookDeletedEvent event){
        this.bookId = event.getBookId();
    }

    @EventSourcingHandler
    public void on(BookUpdateStatusEvent event){
        this.bookId = event.getBookId();
        this.isReady = event.getIsReady();
    }
}