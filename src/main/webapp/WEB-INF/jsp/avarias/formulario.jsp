<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<h1>Reportar avaria</h1>
<p>Já reportou? <a href="${pageContext.request.contextPath}/avarias/consultar">Consultar o estado pelo código</a>.</p>
<form method="post" action="${pageContext.request.contextPath}/avarias" novalidate>
  <label for="nome">Nome *</label>
  <input id="nome" name="nome" required minlength="3" maxlength="100" value="${fn:escapeXml(param.nome)}">
  <span class="erro">${erros.nome}</span>

  <label for="perfil">Perfil *</label>
  <select id="perfil" name="perfil" required>
    <option value="">Escolha</option>
    <option value="FORMANDO" ${param.perfil == 'FORMANDO' ? 'selected' : ''}>Formando</option>
    <option value="FORMADOR" ${param.perfil == 'FORMADOR' ? 'selected' : ''}>Formador</option>
  </select>
  <span class="erro">${erros.perfil}</span>

  <label for="sala">Sala *</label>
  <select id="sala" name="sala" required>
    <option value="">Escolha a sala</option>
    <c:forEach var="s" items="${salas}">
      <option value="${s.id}" ${param.sala == s.id.toString() ? 'selected' : ''}><c:out value="${s.codigo}"/></option>
    </c:forEach>
  </select>
  <span class="erro">${erros.sala}</span>

  <label for="categoria">Equipamento *</label>
  <select id="categoria" name="categoria" required>
    <option value="">Escolha o equipamento</option>
    <c:forEach var="cat" items="${['PC','PROJETOR','REDE','WIFI','IMPRESSORA','SOFTWARE','MOBILIARIO','OUTRO']}">
      <option value="${cat}" ${param.categoria == cat ? 'selected' : ''}>${rotulo[cat]}</option>
    </c:forEach>
  </select>
  <span class="erro">${erros.categoria}</span>

  <label for="descricao">Descrição do problema * (10 a 1000 caracteres)</label>
  <textarea id="descricao" name="descricao" required minlength="10" maxlength="1000">${fn:escapeXml(param.descricao)}</textarea>
  <span class="erro">${erros.descricao}</span>

  <label><input type="checkbox" name="impedeAula" ${not empty param.impedeAula ? 'checked' : ''}>
    Este problema impede a aula de decorrer</label>

  <p class="info">Como é definida a prioridade: <strong>Elevada</strong> se impede a aula;
    <strong>Baixa</strong> para mobiliário ou outro; <strong>Normal</strong> nos restantes casos.</p>

  <button type="submit">Registar avaria</button>
</form>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
