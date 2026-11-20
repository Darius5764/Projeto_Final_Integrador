<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<%@ include file="/WEB-INF/jsp/inc/menu-admin.jspf" %>
<h1>Pré-inscrições</h1>
<form method="get" class="filtros">
  <select name="curso">
    <option value="">Todos os cursos</option>
    <c:forEach var="k" items="${cursos}">
      <option value="${k.idCurso}" ${param.curso == k.idCurso.toString() ? 'selected' : ''}><c:out value="${k.nome}"/></option>
    </c:forEach>
  </select>
  <select name="estado">
    <option value="">Todos os estados</option>
    <c:forEach var="e" items="${['RECEBIDA','EM_ANALISE','CONTACTADA']}">
      <option value="${e}" ${param.estado == e ? 'selected' : ''}>${rotulo[e]}</option>
    </c:forEach>
  </select>
  <button>Filtrar</button>
</form>
<table>
  <tr><th>N.º</th><th>Data</th><th>Curso</th><th>Nome</th><th>Email</th><th>Estado</th><th>Mudar estado</th></tr>
  <c:forEach var="p" items="${lista}">
    <tr>
      <td>${p.id}</td>
      <td>${p.criadoEmTexto}</td>
      <td><c:out value="${p.nomeCurso}"/></td>
      <td><c:out value="${p.nome}"/></td>
      <td><c:out value="${p.email}"/></td>
      <td>${rotulo[p.estado]}</td>
      <td>
        <form method="post" action="${pageContext.request.contextPath}/admin/preinscricoes">
          <input type="hidden" name="id" value="${p.id}">
          <select name="estado">
            <option value="EM_ANALISE">Em análise</option>
            <option value="CONTACTADA">Contactada</option>
          </select>
          <button>Atualizar</button>
        </form>
      </td>
    </tr>
  </c:forEach>
  <c:if test="${empty lista}"><tr><td colspan="7">Sem pré-inscrições.</td></tr></c:if>
</table>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
