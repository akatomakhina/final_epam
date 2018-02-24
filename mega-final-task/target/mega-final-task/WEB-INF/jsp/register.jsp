<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty userId}">
    <c:redirect url="/nastichka/home-page"/>
</c:if>
<fmt:bundle basename="i18n">

    <fmt:message key="registration.label.register" var="register"/>
    <fmt:message key="registration.label.secondName" var="secondName"/>
    <fmt:message key="registration.label.userLogin" var="userLogin"/>
    <fmt:message key="registration.label.email" var="email"/>
    <fmt:message key="registration.label.password" var="password"/>
    <fmt:message key="registration.label.conPass" var="conPass"/>
    <fmt:message key="registration.label.firstName" var="firstName"/>
    <fmt:message key="base.button.submit" var="submit"/>
    <c:if test="${not empty firstNameEmpty}">
        <fmt:message key="registration.error.firstNameEmpty" var="varFirstNameEmpty"/></c:if>

    <c:if test="${not empty invalidFirstName}">
        <fmt:message key="registration.error.invalidFirstName" var="varInvalidFirstName"/></c:if>

    <c:if test="${not empty lastNameEmpty}">
        <fmt:message key="registration.error.lastNameEmpty" var="varLastNameEmpty"/></c:if>

    <c:if test="${not empty invalidLastName}">
        <fmt:message key="registration.error.invalidLastName" var="varInvalidLastName"/></c:if>

    <c:if test="${not empty loginEmpty}">
        <fmt:message key="registration.error.userLoginEmpty" var="varUserLoginEmpty"/></c:if>

    <c:if test="${not empty invalidLogin}">
        <fmt:message key="registration.error.invalidUserLogin" var="varInvalidUserLogin"/></c:if>

    <c:if test="${not empty emailEmpty}">
        <fmt:message key="registration.error.emailEmpty" var="varEmailEmpty"/></c:if>

    <c:if test="${not empty invalidEmail}">
        <fmt:message key="registration.error.invalidEmail" var="varInvalidEmail"/></c:if>

    <c:if test="${not empty busyEmail}">
        <fmt:message key="registration.error.busyEmail" var="varBusyEmail"/></c:if>

    <c:if test="${not empty passwordIsEmpty}">
        <fmt:message key="registration.error.passwordIsEmpty" var="varPasswordIsEmpty"/></c:if>

    <c:if test="${not empty invalidPassword}">
        <fmt:message key="registration.error.invalidPassword" var="varInvalidPassword"/></c:if>

    <c:if test="${not empty confirmError}">
        <fmt:message key="registration.error.confirmError" var="varConfirmError"/></c:if>

</fmt:bundle>
<link rel="stylesheet" href="<c:url value="/css/styles.css"/>"/>
<t:genericpage>

    <jsp:attribute name="content">


            <div class="free-registration">
                <div class="container">
                    <div class="registration">
                        <div class="title">
                            <p>Пожалуйста, заполните форму регистрации</p>
                        </div>
                        <form action="<c:url value="/nastichka/register"/>" method="POST">
                            <div class="registration__form">
                                <input type="text" name="first name" class="registration__input" placeholder="${firstName}" required>
                                    <span class="error"> ${varFirstNameEmpty}<br/>${varInvalidFirstName}<br/></span>

                                <input type="text" name="last name" class="registration__input" placeholder="${secondName}" required>
                                    <span class="error"> ${varLastNameEmpty}<br/>${varInvalidLastName}<br/></span>

                                <input  type="text" name="login" class="registration__input" placeholder="${userLogin}" required>
                                    <span class="error"> ${varLastNameEmpty}<br/>${varInvalidLastName}<br/></span>

                                <input type="text" name="email" class="registration__input" placeholder="${email}" required>
                                    <span class="error"> ${varEmailEmpty}<br/>${varInvalidEmail}${varBusyEmail}<br/></span>

                                <input type="password" name="password" class="registration__input" placeholder="${password}" required>
                                    <span class="error"> ${varPasswordIsEmpty}<br/>${varInvalidPassword}<br/></span>

                                <input  type="password" name="confirm password" class="registration__input" placeholder="${conPass}" required>
                                    <span class="error"> ${varConfirmError}</span>

                                <input type="submit" class="registration__button" value="${submit}"/>

                            </div>
                        </form>
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
