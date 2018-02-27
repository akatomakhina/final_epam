<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:bundle basename="i18n">

    <fmt:message key="admin.label.usersList" var="usersListLabel"/>
    <fmt:message key="registration.label.firstName" var="firstName"/>
    <fmt:message key="registration.label.secondName" var="secondName"/>
    <fmt:message key="admin.label.deleteUser" var="deleteUser"/>
    <fmt:message key="base.label.role" var="roleLabel"/>
    <fmt:message key="registration.label.userLogin" var="login"/>

    <c:if test="${not empty successDelete}">
        <fmt:message key="admin.error.successDelete" var="VsuccessDelete"/></c:if>
    <c:if test="${not empty failDelete}">
        <fmt:message key="admin.error.failDelete" var="VfailDelete"/></c:if>
    <c:if test="${not empty emptyMessage}">
        <fmt:message key="admin.message.emptyMessage" var="varEmptyMessage"/></c:if>


</fmt:bundle>

<t:genericpage>
    <jsp:attribute name="content">


        <div class="admin-user-table container">
            <span class="label-success">${VsuccessDelete}</span> <span class="label-warning">${VfailDelete}</span> </br>
            <c:remove var="successDelete" scope="session"/>
            <c:remove var="failDelete" scope="session"/>
            <div class="admin-user-title">
                 ${usersListLabel} ${varEmptyMessage}
            </div>
            <table class="admin-table-fill">
                    <thead>
                    <tr>
                        <th class="text-left">Email</th>
                        <th class="text-left">Имя</th>
                        <th class="text-left">Фамилия</th>
                        <th class="text-left">Логин </th>
                        <th class="text-left">Роль</th>
                        <th class="text-left">Удалить</th>
                    </tr>
                    </thead>
                    <tbody class="table-hover">
                    <c:forEach items="${usersList}" var="user">
                    <tr>
                        <td class="text-left"><a href="profile?id=${user.id}"><c:out value="${user.email}"/></a></td>
                        <td class="text-left">${firstName} <c:out value="${user.firstName}"/></td>
                        <td class="text-left">${secondName} <c:out value="${user.lastName}"/></td>
                        <td class="text-left">${login} <c:out value="${user.login}"/></td>
                        <td class="text-left">${roleLabel} <c:out value="${user.role}"/></td>
                        <td class="text-left"><a href="<c:url value="/nk/delete-user?id=${user.id}"/>">${deleteUser}</a></td>
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
        </div>

    </jsp:attribute>
</t:genericpage>