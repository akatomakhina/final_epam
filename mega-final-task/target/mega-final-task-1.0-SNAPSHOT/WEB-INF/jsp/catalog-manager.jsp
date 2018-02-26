<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:bundle basename="i18n">
    <fmt:message key="base.button.submit" var="submit"/>
    <fmt:message key="cart.label.name" var="nameLabel"/>
    <fmt:message key="cart.label.price" var="priceLabel"/>
    <fmt:message key="product.label.quantity" var="quantityLabel"/>
    <fmt:message key="product.label.description" var="descriptionLabel"/>
    <fmt:message key="product.label.category" var="categoryLabel"/>
    <fmt:message key="product.label.addNewProduct" var="addNewProductLabel"/>
    <fmt:message key="cart.label.price" var="price"/>

    <fmt:message key="product.label.vendor" var="vendor"/>
    <fmt:message key="product.label.selectCategory" var="selectCategory"/>

    <fmt:message key="user.label.addBalance" var="add"/>

    <c:if test="${not empty invalidName}">
        <fmt:message key="product.error.invalidName" var="invalidNameMsg"/>
    </c:if>
    <c:if test="${not empty successUpdate}">
        <fmt:message key="admin.catalog.successUpdate" var="successUpdateMsg"/>
    </c:if>
    <c:if test="${not empty emptyForm}">
        <fmt:message key="admin.catalog.emptyForm" var="emptyFormMsg"/>
    </c:if>
    <c:if test="${not empty productIsExist}">
        <fmt:message key="admin.catalog.productIsExist" var="productIsExistMsg"/>
    </c:if>
    <c:if test="${not empty invalidPrice}">
        <fmt:message key="admin.catalog.invalidPrice" var="invalidPriceMsg"/>
    </c:if>
    <c:if test="${not empty invalidQuantity}">
        <fmt:message key="admin.catalog.invalidQuantity" var="invalidQuantityMsg"/>
    </c:if>
</fmt:bundle>
<t:genericpage>
    <jsp:attribute name="content">

        <div class="catalog-manager container">
            <div class="catalog-manager-title">
                 ${addNewProductLabel}
            </div>
            <form action="<c:url value="/nk/create-product"/>" method="POST">
                <div class="catalog__add catalog-manager__name">
                    ${add} <span class="weight">${nameLabel}</span>:
                    <input placeholder="${nameLabel}" type="text" name="name" class="catalog-manager__input">
                    <p class="error">${emptyFormMsg} ${productIsExistMsg}${invalidNameMsg}</p>
                    <c:remove var="emptyForm" scope="session"/>
                    <c:remove var="productIsExist" scope="session"/>
                    <c:remove var="invalidNameMsg" scope="session"/>
                    <c:remove var="invalidQuantity" scope="session"/>
                    <c:remove var="invalidPrice" scope="session"/>
                    <c:remove var="success" scope="session"/>
                    <c:remove var="redundantUpdateMsg" scope="session"/>
                </div>

                <div class="catalog__add catalog-manager__value">
                    ${add} <span class="weight">${priceLabel}</span>:
                    <input placeholder="${priceLabel}" type="number" name="price" class="catalog-manager__input">
                </div>

                <div class="catalog__add catalog-manager__amount">
                    ${add} <span class="weight">${quantityLabel}</span>:
                    <input placeholder="${quantityLabel}" type="number" name="quantity" class="catalog-manager__input">
                    <p class="error">${invalidQuantityMsg}</p>
                </div>

                <div class="catalog__add catalog-manager__description">
                    ${add} <span class="weight">${descriptionLabel}</span>:
                    <input placeholder="${descriptionLabel}" name="description" type="text" class="catalog-manager__input">
                </div>

                <div class="catalog__add catalog-manager__vendor">
                    ${add} <span class="weight">${vendor}</span>:
                    <input placeholder="${vendor}" name="vendor" type="text" class="catalog-manager__input">
                </div>

                <div class="catalog__add catalog-manager__catalog">
                    ${add} <span class="weight">${categoryLabel}</span>:
                    <select name="category" class="catalog-manager__list">
                        <c:forEach items="${categories}" var="category">
                            <option disabled="" selected="">${selectCategory}</option>
                            <optgroup label="${category.name}" class="catalog">
                                <c:forEach items="${category.subCategories}" var="subcategory">
                                    <option value="${subcategory.id}">${subcategory.name}</option>
                                </c:forEach>
                            </optgroup>
                        </c:forEach>
                    </select>
                </div>

                <div class="label-success">
                        ${successUpdateMsg}
                    <c:remove var="successUpdate" scope="session"/>
                </div>

                <input type="submit" class="catalog-manager__button" value="${submit}">

            </form>
        </div>

    </jsp:attribute>
</t:genericpage>

