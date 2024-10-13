package utils;
import lombok.Data;

import java.util.List;
@Data
public class PageResult<T> {
    private final List<T> data; // 当前页数据
    private final int currentPage; // 当前页码
    private final int totalItems; // 总记录数
    private final int itemsPerPage; // 每页显示的记录数
    private final int totalPages; // 总页数


    public PageResult(List<T> data, int currentPage, int totalItems, int itemsPerPage) {
        this.data = data;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
    }
}
