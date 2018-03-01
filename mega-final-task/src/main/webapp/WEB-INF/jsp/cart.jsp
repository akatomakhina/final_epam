<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:bundle basename="i18n">

    <fmt:message key="cart.label.cart" var="cart"/>
    <fmt:message key="cart.label.name" var="name"/>
    <fmt:message key="cart.label.price" var="price"/>
    <fmt:message key="cart.label.totalAmount" var="LabelTotalAmount"/>
    <fmt:message key="cart.label.removeItem" var="removeItem"/>
    <fmt:message key="cart.label.buyAll" var="buyAll"/>
    <fmt:message key="order.label.status" var="status"/>

    <fmt:message key="cart.label.editing" var="editAmount"/>
    <fmt:message key="cart.label.deleting" var="delete"/>

    <fmt:message key="product.label.quantity" var="quantity"/>
    <fmt:message key="product.label.inWarehouse" var="inWarehouse"/>
    <fmt:message key="cart.label.edit" var="edit"/>
    <c:if test="${not empty emptyMessage}">
        <fmt:message key="cart.message.emptyMessage" var="varEmptyMessage"/></c:if>
    <c:if test="${not empty deleteSuccess}">
        <fmt:message key="cart.message.deleteSuccess" var="varDeleteSuccess"/>
    </c:if>
    <c:if test="${not empty deleteFail}">
        <fmt:message key="cart.error.deleteFail" var="varDeleteFail"/>
    </c:if>
    <c:if test="${not empty invalidCart}">
        <fmt:message key="cart.error.InvalidCart" var="varInvalidCart"/>
    </c:if>
    <c:if test="${not empty successUpdate}">
        <fmt:message key="cart.error.successUpdate" var="successUpdateMsg"/>
    </c:if>
    <c:if test="${not empty cartItemUpdateFail}">
        <fmt:message key="cart.error.cartItemUpdateFail" var="cartItemUpdateFailMsg"/>
    </c:if>
    <c:if test="${not empty redundantUpdate}">
        <fmt:message key="cart.error.redundantUpdate" var="redundantUpdateMsg"/>
    </c:if>

</fmt:bundle>

<t:genericpage>
    <jsp:attribute name="content">

        <div class="container">
            <div class="basket-table">
                <div class="catalog-title">
                    ${cart} ${varEmptyMessage}
                </div>
                <c:if test="${empty emptyMessage}">
                    <table class="basket-table-fill">
                        <thead>
                        <tr>
                            <th class="text-left">${name}</th>
                            <th class="text-left">${price} x ${quantity}</th>
                            <th class="text-left">${inWarehouse}</th>
                            <th class="text-left">${editAmount}</th>
                            <th class="text-left">${delete}</th>
                        </tr>
                        </thead>
                        <tbody class="table-hover">
                        <c:forEach items="${items}" var="item">
                            <tr>
                                <td class="text-left"><c:out value="${item.product.title}"/></td>
                                <td class="text-left"><c:out value="${item.product.price}"/> x <c:out value="${item.amount}"/></td>
                                <td class="text-left"><c:out value="${item.amountInWarehouse}"/></td>
                                <td class="text-left">
                                    <form action="/nk/update-cart" method="POST">
                                        <div class="basket-editing">
                                            <input type="hidden" name="cartItemId" value="${item.id}"/>
                                            <input type="hidden" name="productId" value="${item.product.id}"/>
                                            <input type="number" name="quantity" class="editing__input" placeholder="Редактировать">
                                            <input type="submit" class="editing__button" value="${edit}"/>
                                        </div>
                                    </form>
                                </td>
                                <td class="text-left">
                                    <div class="basket-delete">
                                        <a href="<c:url value="/nk/remove-from-cart?id=${item.id}"/>">${removeItem}</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <div class="basket__total-amount">
                     ${LabelTotalAmount}: <fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="$"/>
                </div>

                <c:if test="${empty emptyMessage}">
                    <div class="basket__buy-all">
                        <p><a href="<c:url value="/nk/buy-cart?id=${userId}"/>">${buyAll}</a></p>
                    </div>
                </c:if>
                <span class="label-success">${varDeleteSuccess}</span></br><span class="label-warning">${varDeleteFail}</br>${varInvalidCart}</span>
                <span class="label-success">${successUpdateMsg}</span><span class="error">${cartItemUpdateFailMsg}${redundantUpdateMsg}</span>
                <c:remove var="deleteSuccess" scope="session"/>
                <c:remove var="deleteFail" scope="session"/>
                <c:remove var="invalidCart" scope="session"/>
                <c:remove var="redundantUpdate" scope="session"/>
                <c:remove var="successUpdate" scope="session"/>
                <c:remove var="cartItemUpdateFail" scope="session"/>
            </div>
        </div>


 </jsp:attribute>

</t:genericpage>
