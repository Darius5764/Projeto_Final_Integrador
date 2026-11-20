<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<%@ include file="/WEB-INF/jsp/inc/menu-admin.jspf" %>
<h1>Resumo</h1>
<div class="grelha">
  <section class="cartao">
    <h3>Pré-inscrições</h3>
    <table>
      <tr><th>Estado</th><th>Total</th></tr>
      <c:forEach var="e" items="${preinscricoes}">
        <tr><td>${rotulo[e.key]}</td><td>${e.value}</td></tr>
      </c:forEach>
      <c:if test="${empty preinscricoes}"><tr><td colspan="2">Sem registos.</td></tr></c:if>
    </table>
  </section>
  <section class="cartao">
    <h3>Ocorrências</h3>
    <table>
      <tr><th>Estado</th><th>Total</th></tr>
      <c:forEach var="e" items="${ocorrencias}">
        <tr><td>${rotulo[e.key]}</td><td>${e.value}</td></tr>
      </c:forEach>
      <c:if test="${empty ocorrencias}"><tr><td colspan="2">Sem registos.</td></tr></c:if>
    </table>
  </section>
</div>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
