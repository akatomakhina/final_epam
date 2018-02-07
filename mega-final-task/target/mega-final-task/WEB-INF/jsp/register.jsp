<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty idUser}">
    <c:redirect url="/nastichka/home-page"/>
</c:if>
<fmt:bundle basename="i18n">

    <fmt:message key="registration.label.register" var="register"/>
    <fmt:message key="registration.label.secondName" var="secondName"/>
    <fmt:message key="registration.label.login" var="login"/>
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

        <fmt:message key="registration.error.invalidLogin" var="varInvalidLogin"/></c:if>
    <c:if test="${not empty loginEmpty}">
        <fmt:message key="registration.error.loginEmpty" var="varLoginEmpty"/></c:if>
    <c:if test="${not empty invalidLogin}">
        <fmt:message key="registration.error.invalidLogin" var="varInvalidLogin"/></c:if>

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
<link rel="stylesheet" href="<c:url value="/css/font.css"/>"/>
<t:genericpage>



    <jsp:attribute name="content">

<%--<h1>${register}</h1></br>--%>
        <form action="<c:url value="/nastichka/register"/>" method="POST">


            <div class="free-registration">
                <div class="container">
                    <div class="registration">
                        <div class="title">
                            <p>Пожалуйста, заполните форму регистрации</p>
                        </div>
                        <div class="registration__form">
                            <form name="userForm" method="POST" onsubmit="return sendForm()">
                                <input name="userName" class="registration__input" placeholder="Имя пользователя" name="first name"/><span
                                    class="error"> ${varFirstNameEmpty}<br>${varInvalidFirstName}<br></span><br>

                                <input name="userName" class="registration__input" placeholder="Фамилия пользователя" name="last name"/>
                                <span class="error"> ${varLastNameEmpty}<br>${varInvalidLastName}<br></span><br>

                                <input name="userName" class="registration__input" placeholder="Логин пользователя" name="login"/>
                                <span class="error"> ${varLogin}<br>${varInvalidLogin}<br></span><br>

                                <input name="userEmail" class="registration__input" placeholder="Email" name="email"/>
                                <span class="error"> ${varEmailEmpty}<br/>${varInvalidEmail}${varBusyEmail}<br/></span><br>

                                <input name="userPassword" class="registration__input" placeholder="Пароль" name="password"/>
                                <span class="error"> ${varPasswordIsEmpty}<br/>${varInvalidPassword}<br/></span><br>

                                <input name="userPassword" class="registration__input" placeholder="Повторите пароль"name="confirm password"/>
                                <span class="error"> ${varConfirmError}</span><br>

                                <button class="registration__button">Отправить</button>
                            </form>
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

                <%--<table>
                    <tr>
                        <td align="right" valign="top">${firstName}</td>
                        <td><input type="text" name="first name"/><span
                                class="error"> ${varFirstNameEmpty}<br/>${varInvalidFirstName}<br/></span></td>
                    </tr>
                    <tr>
                        <td align="right" valign="top">${secondName}</td>
                        <td><input type="text" name="last name"/><span
                                class="error"> ${varLastNameEmpty}<br/>${varInvalidLastName}<br/></span></td>
                    </tr>
                    <tr>
                        <td align="right" valign="top">${login}</td>
                        <td><input type="text" name="login"/><span
                                class="error"> ${varLogin}<br/>${varInvalidLogin}<br/></span></td>
                    </tr>
                    <tr>
                        <td align="right" valign="top">${email}</td>
                        <td><input type="text" name="email"/><span
                                class="error"> ${varEmailEmpty}<br/>${varInvalidEmail}${varBusyEmail}<br/></span></td>
                    </tr>
                    <tr>
                        <td align="right" valign="top">${password}</td>
                        <td><input type="password" name="password"/><span class="error"> ${varPasswordIsEmpty}<br/>${varInvalidPassword}<br/></span></td>
                    </tr>
                    <tr>
                        <td align="right" valign="top">${conPass}</td>
                        <td><input type="password" name="confirm password"/><span class="error"> ${varConfirmError}</span></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><br/><input type="submit" value="${submit}"/></td>
                    </tr>
                </table>--%>
        </form>
    </jsp:attribute>

</t:genericpage>
