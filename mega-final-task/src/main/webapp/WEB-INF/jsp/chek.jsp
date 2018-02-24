<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:bundle basename="i18n">
    <fmt:message key="cart.label.totalAmount" var="labelAmount"/>
    <fmt:message key="check.label.date" var="labelDate"/>
    <fmt:message key="cart.label.name" var="labelName"/>
    <fmt:message key="cart.label.price" var="labelPrice"/>
    <fmt:message key="check.label.check" var="labelCheck"/>
    <fmt:message key="registration.label.email" var="labelEmail"/>
</fmt:bundle>
<c:remove var="orderID" scope="session"/>
<t:genericpage>
    <jsp:attribute name="content">

        <div class="chek-table container">
            <div class="chek-title">
                 ${labelCheck}
            </div>

            <div class="chek__summa">
                 ${labelAmount}: <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="$"/>
            </div>
            <div class="chek__date">
                 ${labelDate}: <fmt:formatDate value="${order.date}" type="both" dateStyle="medium" timeStyle="medium"/>
            </div>
            <div class="chek__email">
                 ${labelEmail}: <a href="<c:url value="/nastichka/profile?id=${user.id}"/>">${user.email}</a>
            </div>

            <table class="chek-table-fill">
                <thead>
                <tr>
                    <th class="text-left">${labelName}</th>
                    <th class="text-left">${labelPrice}</th>
                </tr>
                </thead>
                <tbody class="table-hover">
                <c:forEach items="${items}" var="item">
                    <tr>
                        <td class="text-left"><c:out value="${item.product.name}"/></td>
                        <td class="text-left"><c:out value="${item.product.price}"/> x <c:out value="${item.quantity}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </jsp:attribute>
</t:genericpage>
