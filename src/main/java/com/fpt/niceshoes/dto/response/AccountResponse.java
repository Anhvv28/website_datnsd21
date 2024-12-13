package com.fpt.niceshoes.dto.response;

import com.fpt.niceshoes.entity.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(types = {Account.class})
public interface AccountResponse {
    @Value("#{target.indexs}")
    Integer getIndex();

    Long getId();

    String getName();

    String getEmail();

    String getPhoneNumber();

    LocalDateTime getCreateAt();

    Boolean getDeleted();

    String getAvatar();

    String getBirthday();

    String getGender();
}