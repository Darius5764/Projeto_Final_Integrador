# Portal ECL — Serviços

Projeto Final Integrador · 3.º ATIS · Escola de Comércio de Lisboa

Aplicação web (Java + JSP) com três áreas:

- **Oferta formativa** — lista de cursos com pesquisa, filtro por modalidade e página de detalhe com fonte oficial.
- **Pré-inscrição** — formulário validado no browser e no servidor, com painel de gestão protegido.
- **Avarias nas salas** — registo de avarias com prioridade automática, consulta pelo código (ex.: `AV-00012`) e painel de ocorrências com estados e histórico.

> Projeto académico demonstrativo — não é um canal oficial da ECL. Usar só dados fictícios.

## Tecnologias

| Componente | Versão |
|---|---|
| Java (JDK) | 17 ou 21 (Eclipse Temurin) |
| Apache Tomcat | 10.1 (não usar o 9 nem o 11) |
| MariaDB | 10.11 ou 11.4 |
| Maven | 3.9 (incluído no IntelliJ / Eclipse) |
| Servidor | Windows Server 2022 (VM em VirtualBox, rede em bridge) |

## Como correr no teu PC

### 1. Criar a base de dados

Na consola do MariaDB, como `root`:

```sql
CREATE DATABASE IF NOT EXISTS portal_ecl CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'portal_app'@'localhost' IDENTIFIED BY 'uma-password-local';
GRANT SELECT, INSERT, UPDATE, DELETE ON portal_ecl.* TO 'portal_app'@'localhost';
SOURCE C:/caminho/para/Projeto_Final_Integrador/sql/schema.sql;
SOURCE C:/caminho/para/Projeto_Final_Integrador/sql/dados.sql;
```

Para começar do zero: `DROP DATABASE portal_ecl;` e repetir.

### 2. Configurar a ligação à BD

Copiar `config/portal-ecl.properties.exemplo` para a pasta `conf` do Tomcat com o nome `portal-ecl.properties`
(ex.: `C:\tomcat10\conf\portal-ecl.properties`) e pôr a password do passo 1:

```properties
db.url=jdbc:mariadb://localhost:3306/portal_ecl
db.user=portal_app
db.password=uma-password-local
```

Este ficheiro **nunca** vai para o GitHub (está no `.gitignore`).

### 3. Compilar e arrancar

```bat
mvn clean package
copy target\portal-ecl.war C:\tomcat10\webapps\
C:\tomcat10\bin\startup.bat
```

Abrir <http://localhost:8080/portal-ecl/>.

Se aparecer «Configuração da BD em falta», a mensagem mostra o caminho onde tem de estar o `portal-ecl.properties`.
Os erros ficam em `C:\tomcat10\logs\catalina.*.log`.

### 4. Criar o gestor (para entrar na Área reservada)

A password do gestor não está no repositório. Gerar o hash (depois do `mvn package`):

```bat
java -cp target\classes pt.ecl.portal.util.Passwords "password-do-gestor"
```

Copiar o hash que aparece e, no MariaDB:

```sql
USE portal_ecl;
INSERT INTO utilizador (nome, identificador, password_hash, perfil)
VALUES ('Gestor Demo', 'gestor', 'HASH-QUE-APARECEU', 'GESTOR');
```

Entrar em <http://localhost:8080/portal-ecl/login> com `gestor` e a password escolhida.

## Instalação no servidor (VM Windows Server)

Resumo; os comandos completos estão no guia de trabalho (Pessoa A, passos 3 a 7).

1. Instalar Temurin 21, Tomcat 10.1 (Windows Service Installer, **sem** Manager / Host Manager, porta 8080), MariaDB, Git e Maven.
2. Pôr os serviços a arrancar sozinhos: `Set-Service Tomcat10 -StartupType Automatic` e `Set-Service MariaDB -StartupType Automatic`.
3. Criar a BD, o utilizador `portal_app` e o utilizador `backup` (só leitura). No `my.ini`: `bind-address=127.0.0.1`.
4. Firewall: abrir só a porta **8080** e o ping; a 3306 fica fechada.
5. Criar `PASTA_TOMCAT\conf\portal-ecl.properties` e limitar as permissões com `icacls` à conta do serviço.
6. Deploy (PowerShell como Administrador):

   ```powershell
   cd C:\Projeto_Final_Integrador
   git pull origin main
   mvn clean package
   Copy-Item target\portal-ecl.war "C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps\" -Force
   ```

O portal fica em `http://IP-DO-SERVIDOR:8080/portal-ecl/`.

## Backup e reposição

Credenciais do backup em `C:\Backups\backup.cnf` (fora do repositório):

```ini
[client]
user=backup
password=PASSWORD-DO-BACKUP
```

```powershell
# backup manual (cria C:\Backups\portal-ecl\portal_ecl_AAAA-MM-DD_HHMM.sql.zip e guarda 7 dias)
Copy-Item scripts\backup.ps1 C:\Backups\backup.ps1
powershell -NoProfile -ExecutionPolicy Bypass -File C:\Backups\backup.ps1

# repor numa base de teste, sem estragar a verdadeira
powershell -ExecutionPolicy Bypass -File scripts\repor.ps1 -Ficheiro C:\Backups\portal-ecl\FICHEIRO.sql.zip -Base portal_ecl_teste
```

O backup diário às 13h é uma tarefa agendada (`Register-ScheduledTask`, ver guia, passo 8).

## Endereços da aplicação

| Endereço | O que é |
|---|---|
| `/` | Início |
| `/cursos`, `/cursos/detalhe?id=1` | Oferta formativa |
| `/preinscricao` | Formulário de pré-inscrição |
| `/avarias`, `/avarias/consultar` | Reportar e consultar avaria |
| `/login`, `/logout` | Entrar e sair da área reservada |
| `/admin/resumo` | Totais por estado |
| `/admin/preinscricoes` | Painel de pré-inscrições |
| `/admin/ocorrencias` | Painel de ocorrências |
| `/admin/cursos` | Cursos com fonte e data |

Tudo o que está em `/admin/...` exige login (`AuthFilter`).

## Estrutura

```
pom.xml
config/portal-ecl.properties.exemplo   modelo da configuração da BD
sql/schema.sql, sql/dados.sql          tabelas e dados iniciais
scripts/backup.ps1, scripts/repor.ps1  backup e reposição
docs/                                  relatório, DER e capturas dos testes
src/main/java/pt/ecl/portal/
  db/        ligação à BD
  model/     Curso, PreInscricao, Ocorrencia, Utilizador, Sala
  dao/       acesso à BD (sempre com PreparedStatement)
  servlet/   páginas e formulários
  filter/    AuthFilter (protege /admin/*)
  util/      Passwords (PBKDF2), Rotulos, Datas
src/main/webapp/
  index.jsp, css/estilo.css
  WEB-INF/web.xml, WEB-INF/jsp/...
```

## Equipa

| Elemento | Área |
|---|---|
| Darius | Infraestrutura e servidor |
| Salvador | Base de dados e catálogo de cursos |
| Afonso Dias | Pré-inscrição e área reservada |
| Elves | Avarias e layout comum |
