package com.ldd.home.operate.entity.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortMenuReq {
    @NotNull
    Long menuId;
    @NotNull
    Integer sortType;
}
