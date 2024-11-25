package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.entity.Account;
import com.fpt.niceshoes.entity.Address;
import com.fpt.niceshoes.infrastructure.common.GenCode;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.constant.AccountRoles;
import com.fpt.niceshoes.infrastructure.converter.AccountConvert;
import com.fpt.niceshoes.infrastructure.converter.AddressConvert;
import com.fpt.niceshoes.infrastructure.exception.RestApiException;
import com.fpt.niceshoes.dto.request.AccountRequest;
import com.fpt.niceshoes.dto.response.AccountResponse;
import com.fpt.niceshoes.repository.IAccountRepository;
import com.fpt.niceshoes.repository.IAddressRepository;
import com.fpt.niceshoes.repository.IRoleRepository;
import com.fpt.niceshoes.service.AccountService;
import com.fpt.niceshoes.util.CloudinaryUtils;
import com.fpt.niceshoes.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IAddressRepository addressRepository;
    @Autowired
    private AccountConvert accountConvert;
    @Autowired
    private AddressConvert addressConvert;
    @Autowired
    private MailUtils mailUtils;
    @Autowired
    private CloudinaryUtils cloudinaryUtils;

    @Override
    public PageableObject<AccountResponse> getAll(AccountRequest request) {
        Pageable pageable = PageRequest.of(request.getPage()-1, request.getSizePage());
        return new PageableObject<>(accountRepository.getAll(request, pageable));
    }

    @Override
    public Account getOne(Long id, String roleName) {
        return accountRepository.getOne(id, roleName);
    }

    @Override
    @Transactional
    public Account create(AccountRequest request, String roleName) {
        if (accountRepository.existsByUsernameAndUsernameNot(request.getUsername(), ""))
            throw new RestApiException("Username đã tồn tại!");
        if (accountRepository.existsByEmailAndEmailNot(request.getEmail(), ""))
            throw new RestApiException("Email đã tồn tại!");
        if (accountRepository.existsByPhoneNumberAndPhoneNumberNot(request.getPhoneNumber(), ""))
            throw new RestApiException("SDT đã tồn tại!");
        if (accountRepository.existsByCccdAndCccdNot(request.getCccd(), ""))
            throw new RestApiException("Mã định danh đã tồn tại!");

        String randomPassword = GenCode.randomPassword();
        Account account = accountConvert.convertRequestToEntity(request);
        account.setRole(roleRepository.findByName(roleName));
        account.setAccountRoles(roleName.equals("Khách hàng") ? AccountRoles.ROLE_USER : AccountRoles.ROLE_EMLOYEE);
        account.setPassword(randomPassword);
        account.setAvatar("defaultAvatar.jpg");
        Account accountSave = accountRepository.save(account);
        if (accountSave != null) {
            Address address = addressConvert.convertRequestToEntity(request.getAddress());
            address.setAccount(accountSave);
            addressRepository.save(address);
//            Upload Images
            if (request.getAvatar() != null)
                accountSave.setAvatar(String.valueOf(cloudinaryUtils.uploadSingleImage(request.getAvatar(), "account")));
//            Send Mail
            String emailContent = "Chào " + accountSave.getEmail() + "\n" + "Bạn vừa dùng email này để đăng ký tài khoản cho hệ thống Nice Shoes Store\n" + "Tài khoản của bạn là: " + accountSave.getUsername() + "\n" + "Mật khẩu đăng nhập là: " + accountSave.getPassword() + "\n\n" + "Đây là email tự động, vui lòng không reply email này.\nCảm ơn.\n\n";
            mailUtils.sendEmail(accountSave.getEmail(), "Thư xác thực tài khoản", emailContent);
        }
        return accountSave;
    }

    @Override
    public Account update(Long id, AccountRequest request) {
        Account old = accountRepository.findById(id).get();
        if (accountRepository.existsByUsernameAndUsernameNot(request.getUsername(), old.getUsername()))
            throw new RestApiException("Username đã tồn tại!");
        if (accountRepository.existsByEmailAndEmailNot(request.getEmail(), old.getEmail()))
            throw new RestApiException("Email đã tồn tại!");
        if (accountRepository.existsByPhoneNumberAndPhoneNumberNot(request.getPhoneNumber(), old.getPhoneNumber()))
            throw new RestApiException("SDT đã tồn tại!");
        if (accountRepository.existsByCccdAndCccdNot(request.getCccd(), old.getCccd()))
            throw new RestApiException("Mã định danh đã tồn tại!");
        Account accountSave = accountRepository.save(accountConvert.convertRequestToEntity(id, request));
        if (accountSave != null) {
            if (request.getAvatar() != null) {
                accountSave.setAvatar(String.valueOf(cloudinaryUtils.uploadSingleImage(request.getAvatar(), "account")));
                accountRepository.save(accountSave);
            }
        }
        return accountSave;
    }

    @Override
    public Account delete(Long id) {
        return null;
    }
}
