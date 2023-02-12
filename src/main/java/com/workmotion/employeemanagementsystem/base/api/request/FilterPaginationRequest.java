package com.workmotion.employeemanagementsystem.base.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterPaginationRequest<Filtration> extends PaginationRequest{
    private Filtration criteria;
    private Boolean deletedRecords;
}
