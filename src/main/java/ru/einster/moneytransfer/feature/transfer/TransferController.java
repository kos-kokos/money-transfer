package ru.einster.moneytransfer.feature.transfer;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import ru.einster.moneytransfer.GenericResponse;

@Controller("/transfers")
public class TransferController {

    private TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @Post
    public GenericResponse transfer(NewTransferDto newTransfer) {
        return new GenericResponse<TransferDto>(transferService.processTransfer(newTransfer.getFromAccountId(),
                newTransfer.getToAccountId(),
                newTransfer.getAmount()));
    }
}
