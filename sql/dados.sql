USE portal_ecl;
SET NAMES utf8mb4;

INSERT INTO curso (nome, area, modalidade, nivel, horas_total, componentes, perfil_saida,
                   saidas_profissionais, fonte, data_verificacao) VALUES
('Técnico de Comércio (Gestão de Negócios)', 'Comércio', 'Profissional', 'Nível 4 QNQ / 12.º ano', NULL,
 'A confirmar — contactar a secretaria da ECL',
 'Organiza e faz a comercialização de produtos e serviços em canais convencionais e online, garantindo a satisfação dos clientes.',
 'A confirmar — contactar a secretaria da ECL',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-comercio/', '2026-11-20'),
('Técnico de Comunicação e Serviço Digital', 'Comércio', 'Profissional', 'Nível 4 QNQ / 12.º ano', NULL,
 'A confirmar — contactar a secretaria da ECL',
 'Assegura a comunicação comercial e o serviço ao cliente através de meios interativos ou digitais.',
 'A confirmar — contactar a secretaria da ECL',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-comunicacao-e-servico-digital/', '2026-11-20'),
('Técnico de Vitrinismo', 'Comércio', 'Profissional', 'Nível 4 QNQ / 12.º ano', NULL,
 'A confirmar — contactar a secretaria da ECL',
 'Concebe, organiza e executa a exposição e decoração de espaços de venda para promover produtos e serviços.',
 'A confirmar — contactar a secretaria da ECL',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-vitrinismo/', '2026-11-20'),
('Técnico de Distribuição', 'Comércio', 'Aprendizagem', 'Nível 4 QNQ / 12.º ano', NULL,
 'A confirmar — contactar a secretaria da ECL',
 'Assegura as atividades operacionais da loja, contribuindo para a otimização da venda.',
 'A confirmar — contactar a secretaria da ECL',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-distribuicao/', '2026-11-20'),
('Técnico de Operações Turísticas', 'Turismo', 'Profissional', 'Nível 4 QNQ / 12.º ano', NULL,
 'A confirmar — contactar a secretaria da ECL',
 'Concebe, organiza, promove e vende serviços turísticos e presta informação sobre recursos turísticos.',
 'A confirmar — contactar a secretaria da ECL',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-operacoes-turisticas/', '2026-11-20'),
('Técnico de Receção Hoteleira', 'Hotelaria', 'Profissional', 'Nível 4 QNQ / 12.º ano', NULL,
 'A confirmar — contactar a secretaria da ECL',
 'Executa o serviço de receção e acolhimento em estabelecimentos hoteleiros e alojamento turístico.',
 'Hotéis; pousadas; aldeamentos e apartamentos turísticos',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-rececao-hoteleira/', '2026-11-20'),
('Técnico de Restaurante-Bar', 'Hotelaria', 'Profissional', 'Nível 4 QNQ / 12.º ano', NULL,
 'A confirmar — contactar a secretaria da ECL',
 'Planifica, dirige e efetua o serviço de alimentos e bebidas à mesa e ao balcão, respeitando as normas de higiene alimentar.',
 'Restaurantes e bares, integrados ou não em unidades hoteleiras',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-restaurante-bar/', '2026-11-20'),
('Técnico de Informática — Instalação e Gestão de Redes', 'Informática', 'Aprendizagem', 'Nível 4 QNQ / 12.º ano', NULL,
 'A confirmar — contactar a secretaria da ECL',
 'Instala, configura e mantém redes informáticas e os equipamentos de apoio, aplicando os níveis de segurança adequados.',
 'A confirmar — contactar a secretaria da ECL',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-informatica-redes-2/', '2026-11-20');

INSERT INTO sala (codigo, edificio, piso) VALUES
  ('Sala 1', 'Principal', '0'),
  ('Sala 2', 'Principal', '1'),
  ('Sala 3', 'Principal', '1'),
  ('Lab. Informática', 'Principal', '1');

-- Dados FICTÍCIOS para testes (nunca dados reais)
INSERT INTO pre_inscricao (id_curso, nome, email, telefone, observacoes) VALUES
  (1, 'Teste Fictício Um', 'teste1@exemplo.pt', '912345678', 'Registo de teste'),
  (5, 'Teste Fictício Dois', 'teste2@exemplo.pt', NULL, NULL);

INSERT INTO ocorrencia (nome_reportante, perfil_reportante, id_sala, categoria, descricao, impede_aula, prioridade) VALUES
  ('Formador Fictício', 'FORMADOR', 4, 'PROJETOR', 'O projetor não liga e a aula precisa de apresentação.', TRUE, 'ELEVADA');

