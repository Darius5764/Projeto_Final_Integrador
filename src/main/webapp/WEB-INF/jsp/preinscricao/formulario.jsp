<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<h1>Pré-inscrição</h1>
<form method="post" action="${pageContext.request.contextPath}/preinscricao" novalidate>
  <label for="nome">Nome completo *</label>
  <input id="nome" name="nome" required minlength="3" maxlength="120" value="${fn:escapeXml(param.nome)}">
  <span class="erro">${erros.nome}</span>

  <label for="email">Email *</label>
  <input id="email" name="email" type="email" required maxlength="150" value="${fn:escapeXml(param.email)}">
  <span class="erro">${erros.email}</span>

  <label for="telefone">Telefone (opcional)</label>
  <input id="telefone" name="telefone" type="tel" pattern="(\+351 ?)?[29][0-9]{8}" value="${fn:escapeXml(param.telefone)}">
  <span class="erro">${erros.telefone}</span>

  <label for="curso">Curso pretendido *</label>
  <select id="curso" name="curso" required>
    <option value="">Escolha um curso</option>
    <c:forEach var="k" items="${cursos}">
      <option value="${k.idCurso}" ${k.idCurso == cursoEscolhido ? 'selected' : ''}><c:out value="${k.nome}"/></option>
    </c:forEach>
  </select>
  <span class="erro">${erros.curso}</span>

  <label for="observacoes">Observações (máx. 500 caracteres)</label>
  <textarea id="observacoes" name="observacoes" maxlength="500">${fn:escapeXml(param.observacoes)}</textarea>
  <span class="erro">${erros.observacoes}</span>

  <div class="privacidade">
    <p>Os dados indicados servem apenas para simular uma manifestação de interesse neste protótipo
       académico. Não são partilhados nem usados para qualquer outro fim. Use apenas dados fictícios.</p>
    <label><input type="checkbox" name="privacidade" required ${not empty param.privacidade ? 'checked' : ''}>
      Li e tomei conhecimento da informação de privacidade *</label>
    <span class="erro">${erros.privacidade}</span>
  </div>

  <button type="submit">Enviar pré-inscrição</button>
</form>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
