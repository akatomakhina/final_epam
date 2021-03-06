<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:bundle basename="i18n">
    <fmt:message key="order.label.myOrder" var="myOrderLabel"/>
    <c:if test="${not empty emptyMessage}">
        <fmt:message key="product.message.emptyList" var="varEmptyMessage"/></c:if>

    <fmt:message key="order.label.date" var="date"/>
    <fmt:message key="cart.label.totalAmount" var="total"/>
    <fmt:message key="order.label.status" var="item"/>
    <fmt:message key="product.message.emptyList" var="emptyList"/>



</fmt:bundle>
<t:genericpage>
    <jsp:attribute name="content">

        <div class="container">
            <div class="orders-table">
                <div class="order-title">
                    ${myOrderLabel} ${varEmptyMessage}
                </div>
                <c:if test="${empty emptyMsg}">
                    <table class="order-table-fill">
                        <thead>
                        <tr>
                            <th class="text-left">${date}</th>
                            <th class="text-left">${total}</th>
                            <th class="text-left">${item}</th>
                        </tr>
                        </thead>

                            <tbody class="table-hover">
                            <c:forEach items="${orderList}" var="order">
                            <tr>
                                <td class="text-left">
                                    <a href="chek?id=${order.id}">
                                        <fmt:formatDate value="${order.date}" type="both" dateStyle="medium" timeStyle="medium"/>
                                    </a>
                                </td>
                                <td class="text-left">
                                    <fmt:formatNumber value="${order.amount}" type="currency" currencySymbol="$"/>
                                </td>
                                <td class="text-left"><c:out value="${order.status.name}"/></td>
                            </tr>
                            </c:forEach>
                            </tbody>

                    </table>
                    <table class="paginator">
                        <tr>
		                    <c:forEach items="${pagesList}" var="page">
		                        <td><a href="products?page=${page}">${page} </a></td>
		                    </c:forEach>
                        </tr>
                    </table>
                </c:if>
                <c:if test="${not empty emptyMsg}">
                    ${emptyList}
                </c:if>
            </div>
        </div>

  </jsp:attribute>
</t:genericpage>