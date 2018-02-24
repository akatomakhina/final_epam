package by.katomakhina.epam.controller.handling;

import by.katomakhina.epam.controller.action.EditBasketAction;
import by.katomakhina.epam.entity.Product;
import by.katomakhina.epam.entity.Subcatalog;
import by.katomakhina.epam.service.catalog.impl.CatalogServiceImpl;
import by.katomakhina.epam.service.exception.ServiceException;
import by.katomakhina.epam.service.product.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CatalogHandler {

    private static final Logger Logger = LogManager.getLogger(CatalogHandler.class);
    private static final int PAGE_VOLUME = 10;

    public List<Product> getRequestedList(HttpServletRequest request) throws ServiceException {
        ProductServiceImpl productService = new ProductServiceImpl();
        CatalogServiceImpl catalogService = new CatalogServiceImpl();
        List<Product> productList;
        List<Product> fragment = new ArrayList<>();

        String sortOrder = (String) request.getSession().getAttribute("sortOrder");

        if (request.getParameter("category") != null) {
            int idCatalog = Integer.parseInt(request.getParameter("category"));
            Subcatalog subcatalog = catalogService.getSubcatalogById(idCatalog);
            request.setAttribute("subcategory", subcatalog.getName());
            productList = productService.getSubcatalogProduct(idCatalog);
        } else {
            productList = productService.getAllProduct();
        }

        if (request.getParameter("search") != null) {
            productList = selectProducts(request.getParameter("search"), productList);
            request.setAttribute("searchQuery", request.getParameter("search"));
        }

        if (!productList.isEmpty()) {
            PaginationHandler<Product> paginationHandler = new PaginationHandler<>(productList, PAGE_VOLUME);
            int pageNumber = paginationHandler.getPageNumber(request);
            fragment = paginationHandler.getPageContent(pageNumber);
            List<Integer> pagesList = paginationHandler.getPageNumbers();
            request.setAttribute("pagesList", pagesList);

        } else request.setAttribute("emptyMsg", "empty");


        if (sortOrder != null) {
            if (sortOrder.equals("byName")) {
                fragment = sortByName(fragment);
            }
            if (sortOrder.equals("byPrice")) {
                fragment = sortByPrice(fragment);
            }
        }
        request.setAttribute("productList", fragment);
        return fragment;
    }

    public List<Product> sortByName(List<Product> list) {
        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product one, Product another) {
                return one.getTitle().compareToIgnoreCase(another.getTitle());
            }
        });
        return list;

    }

    public List<Product> sortByPrice(List<Product> list) {
        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product one, Product another) {
                return Double.compare(one.getPrice(), another.getPrice());
            }
        });
        return list;
    }

    public List<Product> selectProducts(String string, List<Product> list) {
        List<Product> listToReturn = new ArrayList<>();
        for (Product product : list) {
            if (product.getTitle().toLowerCase().contains(string.toLowerCase())
                    || product.getDescription().toLowerCase().contains(string.toLowerCase())
                    || product.getVendor().toLowerCase().contains(string.toLowerCase())) {
                listToReturn.add(product);
            }
        }
        return listToReturn;
    }
}
