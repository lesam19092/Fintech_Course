# Проект для составления личного меню

## Модули

### 1. Edadil MicroService

#### Краткое описание сервиса
Edadil MicroService отвечает за управление данными компаний, фирм, продуктов и магазинов.
Реализует кастомное api edadil [Edadeal](https://edadeal.ru/) . Также на стороне этого микросервиса происходит подсчет себе-стоимости продукта
#### Стек технологий
- Java 21
- Spring Boot 3.3.4
- Maven
- Liquibase
- PostgreSQL 17.0
- kafka
- sprig data 
- elk 
- grafana 
- prometheus

#### Open api 
Эндпоинты можно найти в Swagger-документации по адресу `localhost:4040/swagger-ui.html`.

#### Краткая инфа по базе
- Таблицы: `companies`, `firms`, `users`, `products`, `shops`, `shop_product`
- Связи между таблицами через внешние ключи

#### Ссылки на мониторинг и логирование
- Мониторинг: [Prometheus](http://localhost:9090)
- Логирование: [ELK Stack](http://localhost:5601)

### 2. Authentication Service

#### Краткое описание сервиса
Authentication Service отвечает за аутентификацию и авторизацию пользователей в два микросервиса , позволил вынести определенный функционал .Избавил 2 сервиса от однотипного кода

#### Стек технологий
- Java 21
- Spring Boot 3.3.4
- Maven
- Liquibase
- PostgreSQL 17.0
- Spring Security 
- elk
- grafana prometheus

#### Open api
Эндпоинты можно найти в Swagger-документации по адресу `localhost:1010/swagger-ui.html`.

#### Краткая инфа по базе
- Таблицы: `users`, `tokens` , `password_reset_token`,`instance`,`confirmation_token`
- Связи между таблицами через внешние ключи

#### Ссылки на мониторинг и логирование
- Мониторинг: [Prometheus](http://localhost:9090)
- Логирование: [ELK Stack](http://localhost:5601)

### 3. [FoodRu_MicroService]

#### Краткое описание сервиса
Foodru отвечает за сохранение и составления личного меню для каждого пользователя .На стороне сервиса есть список блюд . Также присутсвует "покупка" , после которой идет сохранение файла на яндекс облка + приходит сообщение на почту

#### Стек технологий
- Java 21
- Spring Boot 3.3.4
- Maven
- Liquibase
- PostgreSQL 17.0
- kafka
- amazonwaws 1.3
- Spring data

#### Open api
Эндпоинты можно найти в Swagger-документации по адресу `localhost:7070/swagger-ui.html`.

#### Краткая инфа по базе
- Таблицы: `ingredients` , `meals` , `meals_ingredietns`,`menu`,`menu_meals`,`users`,`users_meals`
- Связи между таблицами через внешние ключи


#### Ссылки на мониторинг и логирование
- Мониторинг: [Prometheus](http://localhost:9090)
- Логирование: [ELK Stack](http://localhost:5601)

## CI/CD

CI/CD настроен с использованием GitHub Actions. Включает в себя:
- Сборку и тестирование проекта
- Отчет по покрытию тестов и качеству кода
- Выгрузку в package на Github




### Инструкция по локальному запуску
1. Убедитесь, что на вашем компьютере установлены Java(21 или новее), Maven и Docker. Вы можете проверить их наличие, выполнив следующие команды в терминале или командной строке:

    ```bash
    java -version
    mvn -version
    ```

2. Клонируйте репозиторий

    ```bash
    git clone https://github.com/lesam19092/Fintech_Course
    ```
3. Также скачайте образы Docker для всех модулей:
   ```bash
   docker pull ghcr.io/lesam19092/edadil:latest
   docker pull ghcr.io/lesam19092/foodru:latest
   docker pull ghcr.io/lesam19092/auth:latest

4. Перейдите в директорию проекта

    ```bash
    cd путь_к_директории_проекта
    ```

5. Сборка проекта
   Выполните следующую команду Maven для сборки проекта:

    ```bash
    mvn clean install
    ```
6. Запустите проект
   Выполните следующую команду для запуска проекта:

    ```bash
    mvn spring-boot:run
    ```
7. Также не забудьте подключить все необходимые данные для работы приложения(корпопартивную почту ,yandex storage)   
