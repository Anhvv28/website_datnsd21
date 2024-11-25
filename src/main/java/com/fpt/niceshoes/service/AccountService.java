package com.fpt.niceshoes.service;

import com.fpt.niceshoes.entity.Account;
import com.fpt.niceshoes.dto.request.AccountRequest;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.dto.response.AccountResponse;

public interface AccountService {
    PageableObject<AccountResponse> getAll(AccountRequest request);

    Account getOne(Long id, String roleName);

    Account create(AccountRequest request, String roleName);

    Account update(Long id, AccountRequest request);

    Account delete(Long id);
}
