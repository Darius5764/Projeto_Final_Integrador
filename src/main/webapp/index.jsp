<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jsp/inc/cabecalho.jspf" %>
<h1>Portal de Serviços da ECL</h1>
<p>Conheça a oferta formativa da Escola de Comércio de Lisboa, faça a sua pré-inscrição ou reporte uma avaria numa sala.</p>
<div class="grelha">
  <article class="cartao">
    <h3>Oferta formativa</h3>
    <p>Cursos profissionais e de aprendizagem, com duração, saídas profissionais e fonte oficial.</p>
    <a class="botao" href="${pageContext.request.contextPath}/cursos">Ver cursos</a>
  </article>
  <article class="cartao">
    <h3>Pré-inscrição</h3>
    <p>Manifeste interesse num curso. A escola entra em contacto consigo.</p>
    <a class="botao" href="${pageContext.request.contextPath}/preinscricao">Fazer pré-inscrição</a>
  </article>
  <article class="cartao">
    <h3>Reportar avaria</h3>
    <p>Algum equipamento numa sala não funciona? Registe o problema e acompanhe pelo código.</p>
    <a class="botao" href="${pageContext.request.contextPath}/avarias">Reportar avaria</a>
  </article>
</div>
<%@ include file="/WEB-INF/jsp/inc/rodape.jspf" %>
