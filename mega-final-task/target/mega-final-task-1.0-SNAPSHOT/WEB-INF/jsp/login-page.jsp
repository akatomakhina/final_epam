<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<link rel="stylesheet" href="/css/font.css">
<fmt:bundle basename="i18n">
    <fmt:message key="registration.label.email" var="email"/>
    <fmt:message key="registration.label.password" var="password"/>
    <fmt:message key="base.button.submit" var="submit"/>
    <c:if test="${not empty message}">
        <fmt:message key="registration.message.message" var="varMessage"/></c:if>
    <c:if test="${not empty loginError}">
        <fmt:message key="login.error.loginError" var="varLoginError"/>
    </c:if>
    <fmt:message key="base.label.authorize" var="authorize"/>
    <fmt:message key="registration.label.register" var="registerLabel"/>
</fmt:bundle>
<t:genericpage>
    <jsp:attribute name="content">


            <div class="free-registration">
                <div class="container">
                    <div class="registration">
                        <div class="title"> ${varMessage}</div><br/>
                        <c:remove var="message" scope="session"/>
                        <c:remove var="loginError" scope="session"/>
                        <div class="title">
                            <p>${authorize}</p>
                        </div>
                        <div class="registration__form">
                            <form action="<c:url value="/nk/login"/>" method="POST">
                                <input type="text" name="email" class="registration__input" placeholder="${email}" required><br>
                                <input type="password" name="password" class="registration__input" placeholder="${password}" required><br>
                                <span class="error"> ${varLoginError}</span>
                                <input class="registration__button" type="submit" value="${submit}"/>
                            </form>
                        </div>
                        <div class="registration__reference">
                            <a class="registration__reference__ref" href="<c:url value="/nk/register"/>">${registerLabel}</a>
                        </div>
                    </div>
                </div>
            </div>


			<div class="container">
                <ul class="loader">
                    <li class="line"></li>
                    <li class="line"></li>
                    <li class="line"></li>
                </ul>
            </div>

</jsp:attribute>
</t:genericpage>