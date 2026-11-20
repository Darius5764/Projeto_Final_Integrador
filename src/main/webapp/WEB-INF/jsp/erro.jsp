<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<h1>Ocorreu um erro</h1>
<c:choose>
  <c:when test="${pageContext.errorData.statusCode == 404}"><p>A página pedida não existe.</p></c:when>
  <c:otherwise><p>Ocorreu um erro. Tente novamente mais tarde.</p></c:otherwise>
</c:choose>
<p><a href="${pageContext.request.contextPath}/">Voltar ao início</a></p>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
