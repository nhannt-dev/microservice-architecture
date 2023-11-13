package com.example.bookservice.query.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookservice.BookserviceApplication;
import com.example.bookservice.query.model.BookResponseModel;
import com.example.bookservice.query.queries.GetAllBooksQuery;
import com.example.bookservice.query.queries.GetBookQuery;

@SuppressWarnings("all")
@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
    @Autowired
    private QueryGateway queryGateway;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(BookserviceApplication.class);

    @GetMapping("/{bookId}")
    public BookResponseModel getBookDetail(@PathVariable String bookId) {
        GetBookQuery getBookQuery = new GetBookQuery();
        getBookQuery.setBookId(bookId);
        BookResponseModel bookResponseModel = queryGateway
                .query(getBookQuery, ResponseTypes.instanceOf(BookResponseModel.class)).join();
        return bookResponseModel;
    }

    @GetMapping
    public List<BookResponseModel> getAllBooks() {
        GetAllBooksQuery getAllBooksQuery = new GetAllBooksQuery();
        List<BookResponseModel> list = queryGateway
                .query(getAllBooksQuery, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
        logger.info("Danh sach Book " + list.toString());
        return list;
    }
}