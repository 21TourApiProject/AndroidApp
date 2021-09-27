package com.starrynight.tourapiproject.searchPage.searchPageRetrofit;

import java.io.Serializable;

public class SearchKey implements Serializable {
    Filter filter;
    String keyword;

    public SearchKey(Filter filter, String keyword) {
        this.filter = filter;
        this.keyword = keyword;
    }
}
