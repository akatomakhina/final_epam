<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:bundle basename="i18n">
    <fmt:message key="order.label.orders" var="ordersLabel"/>
    <fmt:message key="order.button.changeStatus" var="changeStatus"/>
    <c:if test="${not empty emptyMessage}">
        <fmt:message key="product.message.emptyList" var="varEmptyMessage"/>
    </c:if>
    <fmt:message key="cart.label.price" var="price"/>
    <fmt:message key="order.label.date" var="dateLabel"/>
    <fmt:message key="cart.label.totalAmount" var="amountLabel"/>
    <fmt:message key="order.label.status" var="statusLabel"/>

    <fmt:message key="registration.label.userUser" var="user"/>

    <c:if test="not empty successUpdate">
        <fmt:message key="admin.catalog.successUpdate" var="successUpdateMsg"/>
    </c:if>


</fmt:bundle>
<t:genericpage>
    <jsp:attribute name="content">



        <div class="container">
            <div class="order-manager-table">
                <div class="order-manager-title">
                    ${ordersLabel} ${varEmptyMessage}</br><span class="label-success">${successUpdateMsg}
                </div>

                <c:remove var="successUpdateMsg" scope="session"/>

                <c:if test="${empty emptyMessage}">
                    <table class="order-manager-table-fill">
                        <thead>
                        <tr>
                            <th class="text-left">${dateLabel}</th>
                            <th class="text-left">${amountLabel}</th>
                            <th class="text-left">${user}</th>
                            <th class="text-left">${statusLabel}</th>
                        </tr>
                        </thead>
                        <tbody class="table-hover">
                        <tr>
                            <td class="text-left">
                                <div class="order-manager-date">
                                    <a href="<c:url value="/nk/chek?id=${order.id}"/>">
                                        <fmt:formatDate value="${order.date}" type="both" dateStyle="medium" timeStyle="medium"/>
                                    </a>
                                </div>
                            </td>

                            <td class="text-left"><fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="$"/></td>

                            <td class="text-left">
                                <div class="order-manager-user">
                                    <a href="<c:url value="/nk/profile?id=${order.userId}"/>">${user}</a>
                                </div>
                            </td>

                            <td class="text-left">
                                <form action="<c:url value="/nk/change-status?order=${order.id}"/>" method="POST">
                                    <p><select name="status" class="order-manager__list" onchange="this.form.submit()">
                                        <option disabled="" selected="">${statusLabel}</option>
                                        <c:forEach items="${statusList}" var="item">
                                            <option value="${item.id}">${order.status.id == item.id ? 'selected="selected"' : ''}>${item.name}</option>
                                        </c:forEach>
                                    </select></p>
                                </form>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </c:if>
                <table class="paginator">
                    <tr>
		                    <c:forEach items="${pagesList}" var="page">
		                        <td><a href="products?page=${page}">${page} </a></td>
		                    </c:forEach>
                    </tr>
                </table>
            </div>
        </div>

  </jsp:attribute>
</t:genericpage>