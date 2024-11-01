<h2>1 - Introdução</h2>

Este microserviço foi desenvolvido no intuito de gerenciar processos juridicos utilizando o procolo **REST**.

A tarefa era:
<br>
Utilizando Spring Boot e Postgresql como banco de dados crie um microsserviço com as
funcionalidades descritas abaixo:
1. Como usuário preciso salvar meus números de processos no sistema, quero poder
enviar estes números através de uma requisição POST.
- Obs : Os números de processos devem ser únicos
2. Como usuário quero receber um erro ao tentar cadastrar um processo que já foi
cadastrado anteriormente.
3. Como usuário quero poder consultar os números de processos que salvei.
4. Como usuário quero poder excluir um número de processo que salvei.
5. Como usuário quero poder adicionar um Réu a um processo que já cadastrei
anteriormente.

São pontos adicionais
- Utilizar JUNIT para teste do endpoint de ponta a ponta
- Liquibase na geração da estrutura de dados
<br>

As tecnologias utilizadas foram: 
   - Java 17
   - Spring Boot
   - Spring Web
   - Spring DevTools
   - Spring Data Jpa
   - PostgreSql
   - Junit

<h2>2 - Endpoints da API</h2>

Método HTTP: **GET**<br>
URL do Endpoint: **/api/processos**.<br>
Descrição: lista todos os processos juridicos e se em um processo tiver um reu atrelado a ele ira listar também.

![salvarGet](https://github.com/user-attachments/assets/b2dbda38-715c-4cdc-89ce-83aae5e3c9d6)
![salvarGet2](https://github.com/user-attachments/assets/b2ef0bad-6a66-4456-908f-f453d4b1f67b)

Respostas:

**201 Created**<br>
**409 Conflict** - quando já existe um registro de processo juridico com o mesmo número de processo.

![conflito](https://github.com/user-attachments/assets/2232b90f-5e30-4149-bb3d-2705d451a36e)


Método HTTP: **POST**<br>
URL do Endpoint: **/api/processos**.<br>
Descrição: adiciona ao banco um registro de processo e tem a opção de atrelar reus na criação do registro de processo.

![svp](https://github.com/user-attachments/assets/3958ec8c-8bfe-4377-a5f3-3149ec99e635)
![salvarPost2](https://github.com/user-attachments/assets/3bceaafd-1235-49d4-9372-d6270e75e5cd)

Método HTTP: **POST**<br>
URL do Endpoint: **/api/processos/{processo_id}/reus**.<br>
Descrição: adiciona ao banco um registro de reu e tem a opção de atrelar um reu a processo juridico existente

![salv](https://github.com/user-attachments/assets/0c98fe20-4c6a-48e1-bfc2-0b64049f8a81)


Método HTTP: **DELETE**<br>
URL do Endpoint: **/api/processos/{processo_id}**.<br>
Descrição: remove do banco um registro de processo do banco pelo seu identificador.

![salvarDel](https://github.com/user-attachments/assets/4e6cad5b-b7fb-4b1b-8480-b408b53f4acc)


