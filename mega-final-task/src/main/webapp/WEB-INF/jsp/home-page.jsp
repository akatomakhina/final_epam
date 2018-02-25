<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:bundle basename="i18n">

    <fmt:message key="home.about.bigTitleOne" var="titleOne"/>
    <fmt:message key="home.about.bigTitleTwo" var="titleTwo"/>
    <fmt:message key="home.about.paragraph" var="paragraph"/>

</fmt:bundle>

<t:genericpage>
    <jsp:attribute name="content">
        <div class="main">
            <div class="main__about container">
                <div class="about__title">${titleOne} <p>${titleTwo}</p></div>
                <p>${paragraph}е™</p>
            </div>
        </div>
    </jsp:attribute>
</t:genericpage>
