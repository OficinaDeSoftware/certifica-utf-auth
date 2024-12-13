# Certifica UTF Auth

## Scripts nescessários

No repositório do seu projeto, você pode executar:

### `mvnw clean install`

Limpa todas as dependências e baixa elas novamente, e compila o código.\
Além de gerar a pasta "target" com o artefato do projeto.

Utilizado na hora que termina de clonar o projeto do git ou quando teve altração nas dependências.

### `mvnw compile`

Apenas compila o código.

Utilizado quando a alteação apenas no código e não nas dependências. 

### `mvnw spring-boot:run`

Roda a aplicação.

Deve ser utilizado de pois de ter executado o camando "mvnw compile" ou "mvnw clean install"
