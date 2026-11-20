<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<%@ include file="/WEB-INF/jsp/inc/menu-admin.jspf" %>
<h1>Cursos</h1>
<table>
  <tr><th>Curso</th><th>Modalidade</th><th>Horas</th><th>Fonte</th><th>Verificado em</th></tr>
  <c:forEach var="k" items="${cursos}">
    <tr>
      <td><c:out value="${k.nome}"/></td>
      <td><c:out value="${k.modalidade}"/></td>
      <td>${empty k.horasTotal ? 'A confirmar' : k.horasTotal}</td>
      <td><a href="<c:out value='${k.fonte}'/>" target="_blank" rel="noopener">abrir</a></td>
      <td>${k.dataVerificacao}</td>
    </tr>
  </c:forEach>
</table>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
