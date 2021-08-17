package com.netcracker.auto.util;

import lombok.*;

import java.util.List;

/**
 * @author Anton Popkov
 */
@Data
public class SearchCriteria {
    private String key;
    private SearchOperation searchOperation;
    private boolean isOrOperation;
    private List<Object> arguments;

    enum SearchOperation {
        EQUALITY, LIKE, GREATER_THAN, IN
    }
}
