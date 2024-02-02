Objetivo:
    desenvolver uma aplicação Java para navegar por um website em busca de um termo fornecido pelo usuário e listar as URLs onde o termo foi encontrado.


run testes: mvn test

build: docker build . -t axreng/backend

run in docker: docker run -e BASE_URL=http://hiring.axreng.com/ -p 4567:4567 --rm axreng/backend

import file postman.json for call endpoints in browser:

    1) call post http://localhost:4567/crawl
        with body like {"keyword": "license"}
        and response like {"id":"BDOHJ6NW"}

    2) call get http://localhost:4567/crawl/BDOHJ6NW
        and get response with list of urls with keyword
        in the base url especific in enviroment BASE_URL