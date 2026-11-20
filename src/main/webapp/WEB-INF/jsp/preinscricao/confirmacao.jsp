<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<h1>Pré-inscrição enviada</h1>
<p class="sucesso">Pré-inscrição n.º ${numero} recebida. Estado: Recebida.</p>
<p><a href="${pageContext.request.contextPath}/cursos">Ver outros cursos</a></p>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
