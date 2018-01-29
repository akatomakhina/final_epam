package by.katomakhina.epam.controller.handling;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class PaginationHandler<E> {
    private final List<E> allRecords;
    private final int numberOfPages;

    public PaginationHandler(List<E> allRecords, int numberOfPages) {
        this.allRecords = allRecords;
        this.numberOfPages = numberOfPages;
    }

    public List<Integer> getPageNumbers() {
        int iterations = allRecords.size() / numberOfPages;
        if (allRecords.size() % numberOfPages != 0) {
            iterations++;
        }
        List<Integer> pageNumbers = new ArrayList<>();
        int num = 1;
        for (int i = 0; i < iterations; i++) {
            pageNumbers.add(num);
            num++;
        }
        return pageNumbers;
    }

    public List<E> getPageContent(int pageNum) {
        List<E> fragmentList = new ArrayList<>();
        int begin = pageNum * numberOfPages - numberOfPages;
        for (int i = 0; i < numberOfPages; i++) {
            fragmentList.add(allRecords.get(begin));
            begin++;
            if (begin == allRecords.size()) break;
        }
        return fragmentList;
    }

    public int getPageNumber(HttpServletRequest request) {
        int pageNumber;
        String param = request.getParameter("page");
        if (param != null) {
            pageNumber = Integer.parseInt(param);
        } else {
            pageNumber = 1;
        }
        return pageNumber;
    }
}
