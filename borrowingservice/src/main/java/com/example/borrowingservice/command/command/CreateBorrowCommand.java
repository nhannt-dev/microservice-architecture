package com.example.borrowingservice.command.command;

import java.util.Date;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateBorrowCommand {
    @TargetAggregateIdentifier
    private String id;
    private String bookId, employeeId;
    private Date borrowingDate;

    public CreateBorrowCommand(String id, String bookId, String employeeId, Date borrowingDate) {
        this.id = id;
        this.bookId = bookId;
        this.employeeId = employeeId;
        this.borrowingDate = borrowingDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Date getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(Date borrowingDate) {
        this.borrowingDate = borrowingDate;
    }
}