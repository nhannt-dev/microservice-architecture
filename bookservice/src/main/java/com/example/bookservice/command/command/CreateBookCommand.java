package com.example.bookservice.command.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateBookCommand {
    @TargetAggregateIdentifier // TargetAggregateIdentifier: Đinh nghĩa cho bookId là 1 Identifier
    // TargetAggregateIdentifier mang ý nghĩa là định danh duy nhất cho 1 command
    private String bookId;
	private String name, author;
	private Boolean isReady;

    public CreateBookCommand(String bookId, String name, String author, Boolean isReady) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.isReady = isReady;
    }

    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Boolean getIsReady() {
        return isReady;
    }
    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
    }
}
