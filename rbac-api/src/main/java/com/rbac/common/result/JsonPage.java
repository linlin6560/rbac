package com.rbac.common.result;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * 分页数据封装
 */
@Data
public class JsonPage<T> {
    private Long pageNum;
    private Long pageSize;
    private Long totalPage;
    private Long total;
    private List<T> list;

    public static <T> JsonPage<T> restPage(Page<T> page) {
        JsonPage<T> jsonPage = new JsonPage<>();
        jsonPage.setPageNum(page.getCurrent());
        jsonPage.setPageSize(page.getSize());
        jsonPage.setTotal(page.getTotal());
        jsonPage.setTotalPage(page.getPages());
        jsonPage.setList(page.getRecords());
        return jsonPage;
    }
}
