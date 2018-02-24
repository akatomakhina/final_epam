<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:bundle basename="i18n">
    <fmt:message key="product.label.productList" var="productListLabel"/>
    <c:if test="${not empty emptyMsg}">
        <fmt:message key="product.message.emptyList" var="varEmptyMessage"/></c:if>
    <fmt:message key="base.button.submit" var="submit"/>

    <fmt:message key="cart.label.name" var="nameLabel"/>
    <fmt:message key="cart.label.price" var="priceLabel"/>
    <fmt:message key="product.label.quantity" var="quantityLabel"/>
    <fmt:message key="product.label.description" var="descriptionLabel"/>
    <fmt:message key="product.label.category" var="categoryLabel"/>
    <fmt:message key="product.label.addNewProduct" var="addNewProductLabel"/>
    <fmt:message key="cart.label.price" var="price"/>
    <fmt:message key="product.label.categories" var="categoriesLabel"/>
    <fmt:message key="catalog.label.sortByName" var="sortByName"/>
    <fmt:message key="catalog.label.sortByPrice" var="sortByPrice"/>
    <fmt:message key="base.label.delete" var="deleteLabel"/>
    <fmt:message key="catalog.label.searchResult" var="searchResult"/>
    <fmt:message key="catalog.label.searchLabel" var="searchLabel"/>
    <fmt:message key="catalog.label.allCategories" var="allCategories"/>

    <fmt:message key="product.label.vendor" var="vendor"/>
    <fmt:message key="cart.label.deleting" var="delete"/>

    <c:if test="${not empty deleteSuccess}">
        <fmt:message key="base.label.deleteMessage" var="deleteMessage"/>
    </c:if>


</fmt:bundle>

<t:genericpage>
    <jsp:attribute name="content">



                <c:forEach items="${categories}" var="category">
                    <ul class="nav nav-list-main">
                        <li class="nav-divider">

                        </li>
                        <li>
                            <label class="nav-toggle nav-header">
                                <span>${category.name}</span>
                            </label>
                            <ul class="nav nav-list nav-left-ml">
		                        <c:forEach items="${category.subCategories}" var="subcategory">
                                    <li>
                                        <a href="/do/products?category=${subcategory.id}">${subcategory.name}</a>
                                    </li>
		                        </c:forEach>
                        </li>
                            </ul>
                </c:forEach>



        <div class="container">
            <div class="subcatalog">
                <div class="subcatalog-title">
                    ${categoriesLabel}
                </div>


                <c:forEach items="${categories}" var="category">
                    <table class="sub-table">
                        <thead>
                            <tr>
                                <th class="sub-table-name">${category.name}</th>
                            </tr>
                        </thead>
                        <tbody class="sub-table-body">
                        <c:forEach items="${category.subCategories}" var="subcategory">
                            <tr>
                                <td class="sub-string"><p><a href="/do/products?category=${subcategory.id}">${subcategory.name}</a></p></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="sub-catalog__line"></div>
                </c:forEach>
            </div>





            <div class="catalog-table">
                <div class="catalog-title">
                     ${productListLabel}
                </div>
                </br>
                <span class="label-info">${varEmptyMessage}</span></br><span class="label-success">${deleteMessage}</span>
                <c:remove var="deleteMessage" scope="session"/>
                <div class="catalog-sorting">
                    <c:if test="${empty emptyMsg}">
                        <p><a href="<c:url value="/nastichka/change-order?sort-order=byName"/>">${sortByName}</a></p>
                        <p><a href="<c:url value="/nastichka/change-order?sort-order=byPrice"/>">${sortByPrice}</a></p>
                    </c:if>
                </div>




                <c:if test="${not empty subcategory}">
                    <div class="catalog-subcatalog">
                        ${categoryLabel}: ${subcategory}
                    </div>
                </c:if>

                <c:if test="${empty subcategory}">

                </c:if>




                <div class="catalog-search">
                    <form action="<c:url value="/nastichka/products"/>" method="GET">
                        <c:if test="${not empty param.category}">
                            <input type="hidden" name="category" value="${param.category}">
                        </c:if>
                        <input name="search" class="catalog__input" placeholder="${searchLabel}">
                        <input type="submit" class="catalog__button" value="${searchLabel}">
                    </form>
                    <c:if test="${not empty searchQuery}">
                        ${searchResult}: ${searchQuery}</br>
                    </c:if>
                </div>



                <c:if test="${empty emptyMsg}">
                    <table class="table-fill">
                        <thead>
                        <tr>
                            <th class="text-left">${nameLabel}</th>
                            <th class="text-left">${priceLabel}</th>
                            <th class="text-left">${descriptionLabel}</th>
                            <th class="text-left">${vendor}</th>
                            <c:if test="${loggedUserRole.equals('Admin')}">
                                <th class="text-left">${delete}</th>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody class="table-hover">
                        <c:forEach items="${productList}" var="product">
                            <tr>
                                <td class="text-left">
                                    <a href="product-page?id=${product.id}"><c:out value="${product.name}"/></a>
                                </td>
                                <td class="text-left">${price} <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="$"/></td>
                                <td class="text-left"><c:out value="${product.description}"/></td>
                                <td class="text-left"><c:out value="${product.vendor}"/></td>
                                <c:if test="${loggedUserRole.equals('Admin')}">
                                    <td class="text-left">
                                        <form action="<c:url value="/nastichka/delete-product"/>" method="POST">
                                            <input type="hidden" name="productId" value="${product.id}"/>
                                            <input type="submit" name="name" class="catalog__delete__button" value="${deleteLabel}"/>
                                        </form>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <table>
                        <tr>
                            <c:forEach items="${pagesList}" var="page">
                                <td><a href="products?page=${page}">${page} </a></td>
                            </c:forEach>
                        </tr>
                    </table>
                </c:if>
            </div>
        </div>


  </jsp:attribute>
</t:genericpage>


