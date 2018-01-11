package com.beehyv.wareporting.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by beehyv on 28/9/17.
 */
public class AggregateResponseDto {

    private List<BreadCrumbDto> breadCrumbData;

    private Object tableData;

    public List<BreadCrumbDto> getBreadCrumbData() {
        return breadCrumbData;
    }

    public void setBreadCrumbData(List<BreadCrumbDto> breadCrumbData) {
        this.breadCrumbData = breadCrumbData;
    }

    public Object getTableData() {
        return tableData;
    }

    public void setTableData(Object tableData) {
        this.tableData = tableData;
    }
}
