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

    <fmt:message key="home.footer.up" var="up"/>
    <fmt:message key="home.footer.down" var="down"/>
</fmt:bundle>

<html lang="${language}">
<head>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>">
    <script src="<c:url value="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/css/reset.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/styles.css"/>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>


</head>

<body>

<div class="wrapper">

    <div class="header">
        <div class="header__container">
            <div class="header__logo">
                <a class="logo__href" href="home_page.html"></a>
            </div>
            <ul class="header__language">
                <li class="language__item"><a href="<c:url value="/nk/locale?lang=ru"/>">RU</a></li>
                <li class="language__item"><a href="<c:url value="/nk/locale?lang=en"/>">EN</a></li>
            </ul>


            <c:if test="${empty loggedUserRole&&empty lock}">
                <form action="<c:url value="/nk/login"/>" method="POST">
                    <div class="header__center">

                        <input type="text" name="email" class="header__registration__input" placeholder="Email" required>
                        <span class="error"><br/></span>

                        <input type="password" name="password" class="header__registration__input" placeholder="Пароль" required>
                        <span class="error">${loginError}</span>
                        </br>

                        <c:remove var="loginError" scope="session"/>

                     </div>

                    <div class="header__right__login">
                        <input type="submit" class="header__login__button" value="${submit}"/>
                    </div>
                </form>

                    </br>
                    </br>

                <form action="<c:url value="/nk/register"/>">

                    <div class="header__right__registration">
                        <input type="submit" class="header__registration__button" value="${registerLabel}"/>
                    </div>

                 </form>
            </c:if>

            <div class="header__right">
                <c:if test="${not empty userId}">
                    <p><a href="<c:url value="/nk/logout"/>">${logout}</a></p>
                </c:if>
            </div>
        </div>
    </div>


    <div class = "menu__container">
        <ul class="menu">
            <li class="menu__item"><p><a class="main__href" href="<c:url value="/nk/home-page"/>"><b>${home}</b></a></p></li>
            <li class="menu__item"><p><a class="oder__href" href="<c:url value="/nk/products"/>">${catalog}</a></p>
                <ul class="sub__menu">
                    <li> <p><a href="#">Товары для детей</a></p> </li>
                    <li> <p><a href="#">Техника</a></p> </li>
                    <li> <p><a href="#">Аксессуары</a></p> </li>
                    <li> <p><a href="#">Одежда для женщин</a></p> </li>
                    <li> <p><a href="#">Одежда для мужчин</a></p> </li>
                    <li> <p><a href="#">Обувь</a></p> </li>
                </ul>
            </li>

            <c:if test="${not empty userId}">
                <li class="menu__item"><p><a class="oder__href" href="<c:url value="/nk/profile?id=${userId}"/>">${myProfile}</a></p></li>
            </c:if>

            <c:if test="${loggedUserRole.equals('User')}">
                <li class="menu__item"><p><a class="oder__href" href="<c:url value="/nk/orders"/>">${myOrders}</a></p></li>
                <li class="menu__item"><p><a class="oder__href" href="<c:url value="/nk/basket"/>">${myCart}</a></p></li>
            </c:if>

            <c:if test="${loggedUser.role.equals('Admin')}">
                <li class="menu__item"><p><a class="oder__href" href="<c:url value="/nk/catalog-manager"/>">${catalogManager}</a></p></li>
                <li class="menu__item"><p><a class="oder__href" href="<c:url value="/nk/catalog-manager"/>">${catalogManager}</a></p></li>
                <li class="menu__item"><p><a class="oder__href" href="<c:url value="/nk/admin-page"/>">${adminPage}</a></p></li>
            </c:if>

        </ul>
    </div>


    <main>
           <jsp:invoke fragment="content"/>
    </main>


    <div class="footer">
        <div class="container">
            <div class="footer__left">
                <p>© 2018 «Shop»</p>
                <p>+495 884-0005, info@shop.ru</p>
            </div>
            <div class="footer__right">
                <p><a href="#">${up}</a></p>
                <p><a href="#">${down}</a></p>
            </div>
        </div>
    </div>

</div>

</body>
</html>
