package com.vibe_guide.utils;

import com.vibe_guide.enums.sorting.SortDirection;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;

@UtilityClass
public class SharedTestData {
    public static final SortDirection SORT_DIRECTION = SortDirection.ASC;
    public static final int PAGE = 0;
    public static final int SIZE = 5;
}