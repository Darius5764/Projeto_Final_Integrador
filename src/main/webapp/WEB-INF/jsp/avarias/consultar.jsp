<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<h1>Consultar avaria</h1>
<form method="get" class="filtros">
  <input name="codigo" placeholder="Código, ex.: AV-00012" value="<c:out value='${param.codigo}'/>" required>
  <button>Consultar</button>
</form>
<c:if test="${naoEncontrado}"><p class="erro">Pedido não encontrado.</p></c:if>
<c:if test="${not empty o}">
  <dl>
    <dt>Código</dt><dd>${o.codigo}</dd>
    <dt>Sala</dt><dd><c:out value="${o.salaCodigo}"/></dd>
    <dt>Equipamento</dt><dd>${rotulo[o.categoria]}</dd>
    <dt>Prioridade</dt><dd>${rotulo[o.prioridade]}</dd>
    <dt>Estado</dt><dd>${rotulo[o.estado]}</dd>
    <dt>Registado em</dt><dd>${o.criadoEmTexto}</dd>
    <dt>Resolvido em</dt><dd>${empty o.resolvidoEm ? '—' : o.resolvidoEmTexto}</dd>
  </dl>
</c:if>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
