<%--
  Created by IntelliJ IDEA.
  User: Админ
  Date: 04.09.16
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@attribute name="content" fragment="true" %>
<%@attribute name="sidebar" fragment="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:bundle basename="i18n">
    <fmt:message key="nav.label.about" var="about"/>
    <fmt:message key="nav.label.catalog" var="catalog"/>
    <fmt:message key="nav.label.home" var="home"/>
    <fmt:message key="base.label.authorize" var="authorize"/>
    <fmt:message key="registration.label.email" var="email"/>
    <fmt:message key="registration.label.password" var="password"/>
    <fmt:message key="base.button.submit" var="submit"/>
    <fmt:message key="registration.label.register" var="registerLabel"/>
    <fmt:message key="base.nav.adminPage" var="adminPage"/>
    <fmt:message key="base.nav.orderManager" var="orderManager"/>
    <fmt:message key="base.nav.catalogManager" var="catalogManager"/>
    <fmt:message key="base.nav.myProfile" var="myProfile"/>
    <fmt:message key="base.nav.logout" var="logout"/>

    <fmt:message key="base.nav.myOrders" var="myOrders"/>
    <fmt:message key="base.nav.myCart" var="myCart"/>
</fmt:bundle>

<html lang="${language}">
<head>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>">
    <script src="<c:url value="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/css/reset.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/styles.css"/>">
    <%--<link rel="stylesheet" href="<c:url value="/css/base.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/font.css"/>"/>--%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>


</head>

<body>

<%--<header>

    <ul class="topnav" id="myTopnav">
        <li><a href="<c:url value="/nastichka/products"/>">${catalog}</a></li>
        <li><a href="<c:url value="/nastichka/home-page"/>">${home}</a></li>
        <li class="icon">
            <a href="javascript:void(0);" onclick="myFunction()">&#9776;</a>
        </li>
        <span class="logo">
        <table>

            <tr>
            <td class="language">

             <a href="<c:url value="/nastichka/locale?lang=en"/>" style="color:white">EN</a>
                <a href="<c:url value="/nastichka/locale?lang=ru"/>" style="color:white">RU</a>

            </td>

        </table>
        </span>
    </ul>
</header>--%>
<div class="wrapper">

    <div class="header">
        <div class="header__container">
            <div class="header__logo">
                <a class="logo__href" href="#"></a>
            </div>
            <div class="header__language">
                <p><a href="<c:url value="/nastichka/locale?lang=ru"/>">RU</a></p>
                <p><a href="<c:url value="/nastichka/locale?lang=en"/>" >EN</a></p>
            </div>
            <div class="header__right">
                <p><a href="#">Войти</a></p>
                <p><a href="registration.html">Зарегистрироваться</a></p>
            </div>
        </div>
    </div>


    <div class = "menu__container">
        <ul class="menu">
            <li class="menu__item"><p><a class="main__href" href="<c:url value="/nastichka/home-page"/>"><b>${home}</b></a></p></li>
            <li class="menu__item"><p><a class="oder__href" href="<c:url value="/nastichka/products"/>">${catalog}</a></p>
                <ul class="sub__menu">
                    <li> <p><a href="#">Товары для детей</a></p> </li>
                    <li> <p><a href="#">Техника</a></p> </li>
                    <li> <p><a href="#">Аксессуары</a></p> </li>
                    <li> <p><a href="#">Одежда для женщин</a></p> </li>
                    <li> <p><a href="#">Одежда для мужчин</a></p> </li>
                    <li> <p><a href="#">Обувь</a></p> </li>
                </ul>
            </li>
            <li class="menu__item"><p><a class="oder__href" href="#">Доставка</a></p></li>
            <li class="menu__item"><p><a class="oder__href" href="#">Профиль</a></p></li>
            <li class="menu__item"><p><a class="oder__href" href="#">Контакты</a></p></li>
        </ul>
    </div>


    <main>
        <div id="content">
            <div class="innertube">

                <jsp:invoke fragment="content"/>
            </div>
        </div>
    </main>

    <nav>
        <div class="innertube">
            <c:if test="${loggedUser.role.equals('Admin')}">
                <a href="<c:url value="/nastichka/admin-page"/>">${adminPage}</a></br>
                <a href="<c:url value="/nastichka/order-manager"/>">${orderManager}</a></br>
                <a href="<c:url value="/nastichka/catalog-manager"/>">${catalogManager}</a></br>
            </c:if>
            <c:if test="${not empty userId}">
                <a href="<c:url value="/nastichka/profile?id=${userId}"/>">${myProfile}</a></br>
                <a href="<c:url value="/nastichka/logout"/>">${logout}</a></br>
            </c:if>
            <c:if test="${loggedUserRole.equals('User')}">
                <a href="<c:url value="/nastichka/my-orders"/>">${myOrders}</a></br>
                <a href="<c:url value="/nastichka/cart"/>">${myCart}</a></br>
            </c:if>
            <c:if test="${empty loggedUserRole&&empty lock}">
                <h1 style="font-size: 200%">${authorize}</h1>
                <table>
                    <form action="<c:url value="/nastichka/login"/>" method="POST">


                        <tr>
                            <td align="right" valign="top">${email}</td>
                            <td><input type="text" name="email"><span class="error"><br/></span>
                            </td>
                        </tr>
                        <tr>
                            <td align="right" valign="top">${password}</td>
                            <td><input type="password" name="password"><span class="error">${loginError}</span>
                                <c:remove var="loginError" scope="session"/></td>
                        </tr>

                        <tr>
                            <td></td>
                            <td><br/><input type="submit" value="${submit}"/></td>
                        </tr>

                    </form>
                    <tr>
                        <td></td>
                        <form action="<c:url value="/nastichka/register"/>">
                            <td>
                                <input type="submit" value="${registerLabel}"/>
                            </td>
                        </form>
                    </tr>
                </table>

            </c:if>
            <jsp:invoke fragment="sidebar"/>
        </div>
    </nav>



<div class="footer">
    <div class="container">
        <div class="footer__left">
            <p>© 2018 «Shop»</p>
            <p>+495 884-0005, info@shop.ru</p>
        </div>
        <div class="footer__right">
            <p><a href="#">Политика конфеденциальности</a></p>
            <p><a href="#">Условия использования</a></p>
        </div>
    </div>
</div>

</div>

</body>
</html>
