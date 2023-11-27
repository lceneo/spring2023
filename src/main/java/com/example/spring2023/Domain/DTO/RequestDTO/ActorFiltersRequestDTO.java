package com.example.spring2023.Domain.DTO.RequestDTO;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class ActorFiltersRequestDTO {
    /**
     * substring to filter with
     */
    @Nullable String searchStr;
    /**
     * age to filter with
     */
    @Nullable Integer age;

    public void setSearchStr(@Nullable String searchStr) {
        this.searchStr = searchStr;
    }

    public void setAge(@Nullable Integer age) {
        this.age = age;
    }

    public ActorFiltersRequestDTO(@Nullable String searchStr, @Nullable Integer age) {
        this.searchStr = searchStr;
        this.age = age;
    }
}
