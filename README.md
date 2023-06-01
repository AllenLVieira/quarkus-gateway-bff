# Gateway
Este projeto utiliza o Quarkus, o Supersonic Subatomic Java Framework.

Se você quiser saber mais sobre o Quarkus, visite o site: https://quarkus.io/.

## Executando a aplicação em modo de desenvolvimento
Você pode executar a aplicação em modo de desenvolvimento, que permite o live coding, utilizando o seguinte comando:

```bash
./mvnw compile quarkus:dev
```
> NOTA: O Quarkus agora inclui uma interface de usuário de desenvolvimento (Dev UI), disponível apenas em modo de desenvolvimento em http://localhost:8080/q/dev/.

## Empacotando e executando a aplicação
A aplicação pode ser empacotada utilizando o seguinte comando:

```bash
./mvnw package
```
Isso produzirá o arquivo **'quarkus-run.jar'** no diretório **'target/quarkus-app/'**.
Lembre-se de que não é um über-jar, pois as dependências são copiadas para o diretório **'target/quarkus-app/lib/'**.

A aplicação pode ser executada utilizando o seguinte comando: **'java -jar target/quarkus-app/quarkus-run.jar'**.

Se você desejar construir um über-jar, execute o seguinte comando:

```bash
./mvnw package -Dquarkus.package.type=uber-jar
```
A aplicação, empacotada como um über-jar, pode ser executada utilizando o seguinte comando: **'java -jar target/*-runner.jar'**.

## Criando um executável nativo
Você pode criar um executável nativo utilizando o seguinte comando:

```bash
./mvnw package -Pnative
```
Ou, se você não tiver o GraalVM instalado, pode executar a compilação nativa em um contêiner utilizando o seguinte comando:

```bash
./mvnw package -Pnative -Dquarkus.native.container-build=true
```
Em seguida, você pode executar seu executável nativo com o comando: **'./target/gateway-1.0-SNAPSHOT-runner'**

Se você quiser saber mais sobre a criação de executáveis nativos, consulte https://quarkus.io/guides/maven-tooling.

## Controllers

### ProposalController

O **ProposalController** é responsável por manipular as requisições relacionadas às propostas.

**GET /api/trade/{id}** - Obtém detalhes de uma proposta pelo ID. Requer autenticação com as roles "user" ou "manager".

**POST /api/trade** - Cria uma nova proposta. Requer autenticação com a role "proposal-customer".

**DELETE /api/trade/remove/{id}** - Remove uma proposta pelo ID. Requer autenticação com a role "manager".
### ReportController
O **ReportController** é responsável por manipular as requisições relacionadas aos relatórios.

**GET /api/opportunity/report** - Gera um relatório em formato CSV. Requer autenticação com as roles "user" ou "manager".

**GET /api/opportunity/data** - Obtém dados das oportunidades em formato JSON. Requer autenticação com as roles "user" ou "manager".

## Autenticação com Keycloak
Este projeto utiliza o Keycloak como serviço de autenticação.

O Keycloak é responsável por emitir tokens de acesso que devem ser incluídos nos cabeçalhos das requisições para autenticar as chamadas ao Gateway.

Certifique-se de configurar corretamente o Keycloak no frontend para obter um token válido antes de fazer chamadas ao Gateway.

Os endpoints dos controllers estão protegidos com anotações **@RolesAllowed** para especificar as roles necessárias para acessá-los.

Certifique-se de configurar corretamente as roles e permissões no Keycloak para permitir o acesso adequado aos endpoints do Gateway.

Consulte a documentação do Keycloak para obter mais informações sobre como configurar a autenticação e as roles: https://www.keycloak.org/documentation.

Certifique-se de incluir o token de acesso nos cabeçalhos das requisições ao Gateway, utilizando a seguinte estrutura:

```
Authorization: Bearer {token}
```
Substitua {token} pelo token de acesso válido obtido do Keycloak.
