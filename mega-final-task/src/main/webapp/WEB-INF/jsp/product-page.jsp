<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:bundle basename="i18n">
    <fmt:message key="product.label.productInfo" var="productInfo"/>
    <fmt:message key="product.button.add" var="add"/>
    <fmt:message key="product.button.buy" var="buy"/>

    <fmt:message key="product.label.setQuantity" var="setQuantity"/>
    <fmt:message key="product.label.toCart" var="toCart"/>
    <fmt:message key="product.label.it" var="it"/>

    <fmt:message key="admin.label.editProduct" var="editProductLabel"/>
    <fmt:message key="cart.label.name" var="nameLabel"/>
    <fmt:message key="cart.label.price" var="priceLabel"/>
    <fmt:message key="product.label.quantity" var="quantityLabel"/>
    <fmt:message key="product.label.description" var="descriptionLabel"/>
    <fmt:message key="product.label.category" var="categoryLabel"/>
    <fmt:message key="product.label.addNewProduct" var="addNewProductLabel"/>
    <fmt:message key="cart.label.price" var="price"/>
    <fmt:message key="base.button.submit" var="submit"/>
    <fmt:message key="product.label.description" var="descriptionLabel"/>
    <fmt:message key="base.label.delete" var="deleteLabel"/>


    <fmt:message key="product.label.productName" var="productName"/>
    <fmt:message key="product.label.productVendor" var="productVendor"/>


    <c:if test="${invalidName}">
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


    <c:if test="${not empty addMessage}">
        <fmt:message key="product.message.add" var="addMsg"/></c:if>
    <c:if test="${not empty invalidQuantity}">
        <fmt:message key="admin.catalog.invalidPrice" var="varInvalidQuantity"/></c:if>
    <c:if test="${not empty invalidQuantityAdd}">
        <fmt:message key="buy.error.invalidQuantity" var="varInvalidQuantityAdd"/></c:if>
    <c:if test="${not empty redundantUpdate}">
        <fmt:message key="admin.product.redundantUpdate" var="redundantUpdateMsg"/>
    </c:if><c:if test="${not empty success}">
    <fmt:message key="admin.base.successfulUpdate" var="successMsg"/>
</c:if>

</fmt:bundle>


<t:genericpage>
    <jsp:attribute name="content">

    <div class="container">
        <div class="product">
            <div class="product__title">
                <p>${productInfo}:</p>
            </div>
            <div class="product__paragraph-left">
                <div class="paragraph">
                    <div class="product__sprite name__sprite">
                    </div>
                    <p><span class="weight">${nameLabel}</span> ${productName}: ${requestScope.product.title}</p>
                </div>

                <div class="paragraph">
                    <div class="product__sprite value__sprite">
                    </div>
                    <p><span class="weight">${price}</span> ${productName}: <fmt:formatNumber value="${requestScope.product.price}" type="currency" currencySymbol="$"/></p>
                </div>

                <div class="paragraph">
                    <div class="product__sprite amount__sprite">
                    </div>
                    <p><span class="weight">${quantityLabel}</span> ${productName}: ${requestScope.quantityNum}</p>
                </div>

                <div class="paragraph">
                    <div class="product__sprite description__sprite">
                    </div>
                    <p><span class="weight"> ${descriptionLabel}</span> ${productName}: ${requestScope.product.description}</p>
                </div>

                <div class="paragraph">
                    <div class="product__sprite vendor__sprite">
                    </div>
                    <p><span class="weight">${productVendor}</span> ${productName}: ${requestScope.product.vendor}</p>
                </div>
            </div>
        </div>
    </div>

        <c:if test="${empty userId}">
            ${secMessage}
        </c:if>

        <c:if test="${loggedUser.role.equals('User')}">
            <div class="container">
				<div class="payment__basket">
                    <div class="payment__basket__inf">
                            ${setQuantity} <span class="weight">${buy}</span>:
                    </div>
                    <form action="<c:url value="/nk/buy-product"/>" method="POST">
                        <div class="payment__basket__input">
                            <input type="hidden" name="productId" value="${product.id}">
                            <input name="buyQuantity" type="number" class="product__payment__basket__input"><br>
                            <input class="payment__basket__button" type="submit" name="Submit" value="${buy}">
                        </div>
                    </form>
                    <span class="error">${varInvalidQuantity}</span>
                    <c:remove var="invalidQuantity" scope="session"/>

                    <div class="payment__basket__inf">
                            ${setQuantity} <span class="weight">${add}</span> ${productName} <span class="weight">${toCart}</span>:
                    </div>

                    <form action="<c:url value="/nk/add-product"/>" method="POST">
                        <div class="payment__basket__input">
                            <input type="hidden" name="productId" value="${product.id}">
                            <input name="addQuantity" type="number" class="product__payment__basket__input"><br>
                            <input class="payment__basket__button" type="submit" name="Submit" value="${add}">
                        </div>
                    </form>
                    <span class="error">${varInvalidQuantityAdd}</span><span class="label-success">${addMsg}</span>
                    <c:remove var="invalidQuantityAdd" scope="session"/>
                    <c:remove var="addMessage" scope="session"/>
                </div>
            </div>
        </c:if>



        <c:if test="${loggedUser.role.equals('Admin')}">

            <form action="/do/delete-product" method="POST">
                <input type="hidden" name="productId" value="${product.id}"/>
                <input type="hidden" name="fromProductPage" value="fromProductPage"/>
                <input class="payment__basket__button" type="submit" value="${deleteLabel}"/>
            </form>


            <div class="container">
				<div class="product__admin">
                    <div class="product__title">
                        <p>${editProductLabel}:</p>
                    </div>


                    <form action="<c:url value="/nk/update-product"/>" method="POST">
                        <input type="hidden" name="productId" value="${requestScope.product.id}"/>
                        <div class="product__admin__paragraph-left">
                            <div class="paragraph__admin">
                                <span class="weight">${nameLabel}</span> ${productName}:
                                <input type="text" name="name" placeholder="Название" class="product__admin__input" value="${requestScope.product.title}">
                                    ${emptyFormMsg} ${productIsExistMsg}${invalidNameMsg}
                                <c:remove var="emptyForm" scope="session"/>
                                <c:remove var="productIsExist" scope="session"/>
                                <c:remove var="invalidNameMsg" scope="session"/>
                            </div>

                            <div class="paragraph__admin">
                                <span class="weight">${priceLabel}</span> ${productName}:
                                <input type="number" placeholder="Стоимость" name="price" value="${requestScope.product.price}" class="product__admin__input"><br>
                                    ${invalidPriceMsg}
                                <c:remove var="invalidPrice" scope="session"/>
                            </div>

                            <div class="paragraph__admin">
                                <span class="weight">${quantityLabel}</span> ${productName}:
                                <input type="number" placeholder="Количество" name="quantity" value="${requestScope.quantityNum} class="product__admin__input"><br>
                                    ${varInvalidQuantity}
                                <c:remove var="invalidQuantity" scope="session"/>
                            </div>

                            <div class="paragraph__admin">
                                <span class="weight">${descriptionLabel}</span> ${productName}:
                                <input type="text" name="description" value="${requestScope.product.description}" placeholder="Описание" class="product__admin__input"><br>
                            </div>

                            <div class="paragraph__admin">
                                <span class="weight">${productVendor}</span> ${productName}:
                                <input type="text" name="vendor" value="${requestScope.product.vendor}" placeholder="Поставщик" class="product__admin__input"><br>
                            </div>

                            <p><select name="category" class="product__list">
                                <c:forEach items="${categories}" var="category">
                                    <option disabled="" selected="">Выберите каталог</option>
                                    <optgroup label="${category.name}" class="catalog">
                                        <c:forEach items="${category.subCatalog}" var="subcategory">
                                            <option value="${subcategory.id}" ${subcategory.id == product.id_catalog ? 'selected="selected"' : ''}>${subcategory.name}</option>
                                        </c:forEach>
                                    </optgroup>
                                </c:forEach>
                            </select></p>

                            <input class="product__button" type="submit" value="${submit}"/>

                        </div>
                        <span class="label-success">${successMsg}</span><span
                            class="label-warning">${redundantUpdateMsg}</span>
                        <c:remove var="success" scope="session"/>
                        <c:remove var="redundantUpdate" scope="session"/>
                    </form>
                </div>
            </div>
        </c:if>

</jsp:attribute>
</t:genericpage>
