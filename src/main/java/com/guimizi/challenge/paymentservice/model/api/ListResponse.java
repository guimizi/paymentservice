package com.guimizi.challenge.paymentservice.model.api;

import java.util.List;

public class ListResponse<T> {

    private Integer size;
    private Integer page;
    private Boolean hasNext;
    private List<T> list;

    public ListResponse(Integer page, Boolean hasNext, List<T> list) {
        this.size = list != null ? list.size(): 0;
        this.page = page;
        this.hasNext = hasNext;
        this.list = list;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getPage() {
        return page;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public List<T> getList() {
        return list;
    }
}
