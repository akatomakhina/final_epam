<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:bundle basename="i18n">
    <fmt:message key="registration.label.firstName" var="firstName"/>
    <fmt:message key="registration.label.secondName" var="secondName"/>
    <fmt:message key="registration.label.userLogin" var="userLogin"/>
    <fmt:message key="admin.label.deleteUser" var="deleteUser"/>
    <fmt:message key="registration.label.email" var="email"/>
    <fmt:message key="base.label.profile" var="profile"/>
    <fmt:message key="user.label.addFunds" var="addFunds"/>
    <fmt:message key="user.label.balance" var="balanceLabel"/>

    <fmt:message key="registration.label.first" var="first"/>
    <fmt:message key="registration.label.last" var="last"/>
    <fmt:message key="registration.label.nameName" var="name"/>
    <fmt:message key="registration.label.userUser" var="user"/>
    <fmt:message key="registration.label.loginLogin" var="login"/>
    <fmt:message key="user.label.addBalance" var="add"/>

    <c:if test="${not empty success}">
        <fmt:message key="user.label.success" var="successMsg"/>
    </c:if>
    <c:if test="${not empty invalidUpdate}">
        <fmt:message key="user.label.invalidUpdate" var="invalidUpdateMsg"/>
    </c:if>
</fmt:bundle>




<t:genericpage>
    <jsp:attribute name="content">

        <div class="my__profile">
            <div class="profile container">
                <div class="title">
                    <p>${profileLabel}</p>
                </div>
                <div class="profile__paragraph-left">
                    <div class="paragraph">
                        <div class="sprite profile__sprite">
                        </div>
                        <p><span class="weight">${first}</span> ${name}: ${requestScope.user.firstName}</p>
                    </div>

                    <div class="paragraph shift">
                        <p><span class="weight">${last}</span> ${name}: ${requestScope.user.lastName}</p>
                    </div>

                    <div class="paragraph">
                        <div class="sprite email__sprite">
                        </div>
                        <p><span class="weight">${login}</span> ${user}: ${requestScope.user.login}</p>
                    </div>

                    <div class="paragraph shift">
                        <p><span class="weight">${email}</span> ${user}: ${requestScope.user.email}</p>
                    </div>

                    <c:if test="${loggedUserRole.equals('Admin') || userId == requestScope.user.id}">
                        <div class="paragraph">
                            <div class="sprite balance__sprite">
                            </div>
                            <p><span class="weight">${balanceLabel}</span> ${user}:
                                <fmt:formatNumber value="${requestScope.user.balance}" type="currency" currencySymbol="$"/>
                            </p>
                        </div>
                    </c:if>


                    <c:if test="${userId.equals(requestScope.user.id)&&loggedUserRole.equals('User')}">
                        <div class="balance__paragraph shift">
                            <p>${add} <span class="weight">${balanceLabel}</span>: </p>
                        </div>
                        <form action="/nk/update-balance" method="POST">
                            <div class="balance__input shift">
                                <input type="number" name="balance" class="balance__registration__input"><br>
                                <input type="submit" class="balance__button" value="${addFunds}">
                            </div>
                        </form>
                    </c:if>

                    <c:if test="${loggedUserRole.equals('Admin')&&requestScope.user.role.equals('User')}">
                        <div class="delete__user shift">
                            <a href="<c:url value="/nk/delete-user?id=${requestScope.user.id}"/>">${deleteUser}</a>
                        </div>
                    </c:if>
                </div>
                <span class="label-success">${successMsg}</span>
                <span class="error">${invalidUpdateMsg}</span>
                <c:remove var="success" scope="session"></c:remove>
                <c:remove var="invalidUpdate" scope="session"></c:remove>
            </div>
        </div>

    </jsp:attribute>
</t:genericpage>

