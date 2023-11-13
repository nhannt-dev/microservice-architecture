package com.example.borrowingservice.command.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.borrowingservice.command.command.DeleteBorrowCommand;
import com.example.borrowingservice.command.event.BorrowCreatedEvent;

import com.example.commonservice.command.UpdateStatusBookCommand;
import com.example.commonservice.model.BookResponseCommonModel;
import com.example.commonservice.query.GetDetailsBookQuery;

@SuppressWarnings("all")
@Saga
public class BorrowingSaga {
    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id") // Tìm TargetAggregateIdentifier
    private void handle(BorrowCreatedEvent event) {
        try {
            SagaLifecycle.associateWith("bookId", event.getBookId());
            GetDetailsBookQuery getDetailsBookQuery = new GetDetailsBookQuery(event.getBookId());
            BookResponseCommonModel bookResponseCommonModel = queryGateway
                    .query(getDetailsBookQuery, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
            if (bookResponseCommonModel.getIsReady()) {
                UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(), false,
                        event.getEmployeeId(), event.getId());
                commandGateway.sendAndWait(command);
            } else {
                throw new Exception("Sach Da co nguoi Muon");
            }
        } catch (Exception e) {
            rollBackBorrowRecord(event.getId());
            System.out.println(e.getMessage());
        }
    }

    private void rollBackBorrowRecord(String id){
        commandGateway.sendAndWait(new DeleteBorrowCommand(id));
    }
}