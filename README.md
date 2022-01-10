# sgescolar

Sistema de Gestão Escolar

# como rodar o sistema

O sistema de gestão escolar utiliza Java, Spring Boot/Data/Security e autenticação stateles e autorização com Java Web Token (JWT). 

Para executar o sistema do zero, é necessário, primeiramente, criar a base de dados de nome "sgescolar" no SGBD PostgreSQL e, então 
rodar o projeto no spring tool suite ou via linha de comando com maven.

Após o sistema executado a primeira vez no servidor tomcat embutido, as tabelas foram criadas na base de dados de nome "sgescolar" e, agora, 
é necessário configurar o usuário padrão e os recursos que devem ser restritos aos devidos usuários.

Para alimentar a base de dados com os dados dos recursos, tipos de usuários e as permissões por grupo de usuário, basta navegar até 
a pasta "recursos" do projeto e, então, utilizar o seguinte comando para se autenticar no PostgreSQL:

    psql -U postgres sgescolar
    Senha: postgres

Para executar o script contido na pasta "recursos" faça o seguinte:

    \i script.sql

Após isto, execute o seguinte comando para configurar o charset, isto é, a codificação padrão do banco de dados:

    set client_encoding=ISO88591

Feito isto, execute o outro script da pasta "recursos", o que tem o nome de "functions.sql". Digite na linha de comandos o seguinte:

    \i functions.sql

Este comando carrega a função que permite a comparação de strings ignorando acentuação e se as letras estão em maiuscula ou minuscula.

Após executado o script de criação dos grupos, recursos e o super usuário padrão, se pode logar no sistema com os seguintes dados:

    Username: raiz
    Senha: raiz

Claro, para isto, é necessário rodar o sistema no servidor tomcat embutido e, então, acessar a seguinte página de login:

    localhost:8080/#!

Após logado como usuário raiz, se pode criar as contas de nível ADMIN, SECRETÁRIO, PROFESSOR E ALUNO. Esses são os cinco perfis
suportados pelo sistema e que têm associados os devidos recursos.
