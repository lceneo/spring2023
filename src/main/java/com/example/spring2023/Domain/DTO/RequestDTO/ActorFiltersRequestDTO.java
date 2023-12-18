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

    /**
     * number of actors to skip from the resulted value
     */
    @Nullable Integer skip;
    /**
     * number of actors to take from the resulted value
     */
    @Nullable Integer take;

    public void setSkip(@Nullable Integer skip) {
        this.skip = skip;
    }

    public void setTake(@Nullable Integer take) {
        this.take = take;
    }

    public void setSearchStr(@Nullable String searchStr) {
        this.searchStr = searchStr;
    }

    public void setAge(@Nullable Integer age) {
        this.age = age;
    }

    public ActorFiltersRequestDTO(@Nullable String searchStr, @Nullable Integer age, @Nullable Integer skip, @Nullable Integer take) {
        this.searchStr = searchStr;
        this.age = age;
        this.skip = skip;
        this.take = take;
    }
}
