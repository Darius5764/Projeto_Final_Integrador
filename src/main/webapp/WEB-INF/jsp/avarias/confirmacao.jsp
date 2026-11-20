<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<h1>Avaria registada</h1>
<p class="sucesso">Avaria <strong>${o.codigo}</strong> registada. Prioridade: ${rotulo[o.prioridade]}.
  Guarde este código para consultar o estado.</p>
<p><a href="${pageContext.request.contextPath}/avarias/consultar?codigo=${o.codigo}">Consultar o estado</a></p>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
