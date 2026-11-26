<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<h1>Login</h1>
<p>Acesso apenas para gestores e técnicos da escola.</p>
<form method="post" action="${pageContext.request.contextPath}/login">
  <label for="identificador">Identificador</label>
  <input id="identificador" name="identificador" required value="${fn:escapeXml(param.identificador)}">
  <label for="password">Password</label>
  <input id="password" name="password" type="password" required>
  <p class="erro"><c:out value="${erro}"/></p>
  <button type="submit">Entrar</button>
</form>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
