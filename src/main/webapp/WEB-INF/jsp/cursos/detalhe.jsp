<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<p><a href="${pageContext.request.contextPath}/cursos">← Voltar à oferta formativa</a></p>
<h1><c:out value="${curso.nome}"/></h1>
<dl>
  <dt>Área</dt><dd><c:out value="${curso.area}"/></dd>
  <dt>Modalidade</dt><dd><c:out value="${curso.modalidade}"/></dd>
  <dt>Nível</dt><dd><c:out value="${curso.nivel}"/></dd>
  <dt>Duração</dt>
  <dd><c:choose>
    <c:when test="${empty curso.horasTotal}">A confirmar — contactar a secretaria da ECL</c:when>
    <c:otherwise>${curso.horasTotal} horas</c:otherwise>
  </c:choose></dd>
  <dt>Componentes</dt><dd><c:out value="${curso.componentes}"/></dd>
  <dt>Perfil de saída</dt><dd><c:out value="${curso.perfilSaida}"/></dd>
  <dt>Saídas profissionais</dt><dd><c:out value="${curso.saidasProfissionais}"/></dd>
</dl>
<p class="info">Fonte: <a href="<c:out value='${curso.fonte}'/>" target="_blank" rel="noopener">página oficial do curso</a>
  · verificado em ${curso.dataVerificacao}</p>
<a class="botao" href="${pageContext.request.contextPath}/preinscricao?curso=${curso.idCurso}">Tenho interesse</a>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
