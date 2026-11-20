<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<%@ include file="/WEB-INF/jsp/inc/menu-admin.jspf" %>
<p><a href="${pageContext.request.contextPath}/admin/ocorrencias">← Voltar às ocorrências</a></p>
<h1>Ocorrência ${o.codigo}</h1>
<dl>
  <dt>Reportado por</dt><dd><c:out value="${o.nomeReportante}"/> (${rotulo[o.perfilReportante]})</dd>
  <dt>Sala</dt><dd><c:out value="${o.salaCodigo}"/></dd>
  <dt>Equipamento</dt><dd>${rotulo[o.categoria]}</dd>
  <dt>Descrição</dt><dd><c:out value="${o.descricao}"/></dd>
  <dt>Impede a aula</dt><dd>${o.impedeAula ? 'Sim' : 'Não'}</dd>
  <dt>Prioridade</dt><dd>${rotulo[o.prioridade]}</dd>
  <dt>Estado</dt><dd>${rotulo[o.estado]}</dd>
  <dt>Resolução</dt><dd><c:out value="${empty o.resolucao ? '—' : o.resolucao}"/></dd>
  <dt>Registado em</dt><dd>${o.criadoEmTexto}</dd>
  <dt>Última alteração</dt><dd>${empty o.atualizadoEm ? '—' : o.atualizadoEmTexto}</dd>
  <dt>Resolvido em</dt><dd>${empty o.resolvidoEm ? '—' : o.resolvidoEmTexto}</dd>
</dl>

<h2>Mudar estado</h2>
<form method="post" action="${pageContext.request.contextPath}/admin/ocorrencias/detalhe">
  <input type="hidden" name="id" value="${o.id}">
  <label for="estado">Novo estado</label>
  <select id="estado" name="estado">
    <c:forEach var="e" items="${['ABERTA','EM_ANALISE','EM_RESOLUCAO','RESOLVIDA']}">
      <option value="${e}" ${o.estado == e ? 'selected' : ''}>${rotulo[e]}</option>
    </c:forEach>
  </select>
  <label for="resolucao">Resolução / observações técnicas (obrigatória para «Resolvida»)</label>
  <textarea id="resolucao" name="resolucao"><c:out value="${o.resolucao}"/></textarea>
  <p class="erro"><c:out value="${erro}"/></p>
  <button type="submit">Guardar</button>
</form>

<h2>Histórico de estados</h2>
<table>
  <tr><th>Data</th><th>De</th><th>Para</th><th>Por</th></tr>
  <c:forEach var="h" items="${historico}">
    <tr>
      <td>${h.quando}</td>
      <td>${empty h.anterior ? '—' : rotulo[h.anterior]}</td>
      <td>${rotulo[h.novo]}</td>
      <td><c:out value="${h.quem}"/></td>
    </tr>
  </c:forEach>
  <c:if test="${empty historico}"><tr><td colspan="4">Ainda sem alterações.</td></tr></c:if>
</table>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
