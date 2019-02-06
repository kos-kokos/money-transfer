package ru.einster.moneytransfer.feature.account;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import ru.einster.moneytransfer.GenericException;
import ru.einster.moneytransfer.GenericResponse;


@Controller("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Get("/{id}")
    public GenericResponse<Account> getAccount(Long id) {
        return new GenericResponse<>(
                accountService.getAccount(id)
                        .orElseThrow(() -> new GenericException("Account " + id + " not found")));
    }

    @Post
    public GenericResponse<Account> createAccount(NewAccountDto newAccountDto) {
        return new GenericResponse<>(accountService.createAccount(newAccountDto));
    }

}
