<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<%@ include file="/WEB-INF/jsp/inc/menu-admin.jspf" %>
<h1>Ocorrências</h1>
<form method="get" class="filtros">
  <select name="sala">
    <option value="">Todas as salas</option>
    <c:forEach var="s" items="${salas}">
      <option value="${s.id}" ${param.sala == s.id.toString() ? 'selected' : ''}><c:out value="${s.codigo}"/></option>
    </c:forEach>
  </select>
  <select name="estado">
    <option value="">Todos os estados</option>
    <c:forEach var="e" items="${['ABERTA','EM_ANALISE','EM_RESOLUCAO','RESOLVIDA']}">
      <option value="${e}" ${param.estado == e ? 'selected' : ''}>${rotulo[e]}</option>
    </c:forEach>
  </select>
  <select name="prioridade">
    <option value="">Todas as prioridades</option>
    <c:forEach var="e" items="${['ELEVADA','NORMAL','BAIXA']}">
      <option value="${e}" ${param.prioridade == e ? 'selected' : ''}>${rotulo[e]}</option>
    </c:forEach>
  </select>
  <button>Filtrar</button>
</form>
<table>
  <tr><th>Código</th><th>Data</th><th>Sala</th><th>Equipamento</th><th>Prioridade</th><th>Estado</th><th></th></tr>
  <c:forEach var="o" items="${lista}">
    <tr>
      <td>${o.codigo}</td>
      <td>${o.criadoEmTexto}</td>
      <td><c:out value="${o.salaCodigo}"/></td>
      <td>${rotulo[o.categoria]}</td>
      <td class="${o.prioridade == 'ELEVADA' ? 'elevada' : ''}">${rotulo[o.prioridade]}</td>
      <td>${rotulo[o.estado]}</td>
      <td><a href="${pageContext.request.contextPath}/admin/ocorrencias/detalhe?id=${o.id}">Abrir</a></td>
    </tr>
  </c:forEach>
  <c:if test="${empty lista}"><tr><td colspan="7">Sem ocorrências.</td></tr></c:if>
</table>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
