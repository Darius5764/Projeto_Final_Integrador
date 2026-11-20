<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<h1>Oferta formativa</h1>
<form method="get" class="filtros">
  <input name="q" placeholder="Pesquisar curso ou área" value="<c:out value='${param.q}'/>">
  <select name="modalidade">
    <option value="">Todas as modalidades</option>
    <option ${param.modalidade == 'Profissional' ? 'selected' : ''}>Profissional</option>
    <option ${param.modalidade == 'Aprendizagem' ? 'selected' : ''}>Aprendizagem</option>
  </select>
  <button>Filtrar</button>
</form>
<c:if test="${empty cursos}"><p>Nenhum curso encontrado.</p></c:if>
<div class="grelha">
  <c:forEach var="k" items="${cursos}">
    <article class="cartao">
      <h3><c:out value="${k.nome}"/></h3>
      <p><c:out value="${k.area}"/> · <c:out value="${k.modalidade}"/></p>
      <p><c:choose>
        <c:when test="${empty k.horasTotal}">Duração: A confirmar</c:when>
        <c:otherwise>Duração: ${k.horasTotal} horas</c:otherwise>
      </c:choose></p>
      <a href="${pageContext.request.contextPath}/cursos/detalhe?id=${k.idCurso}">Ver detalhe</a>
    </article>
  </c:forEach>
</div>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
