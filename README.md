# Система управления заказами

REST API для управления заказами в небольшом магазине.

## Стек технологий

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL

## Сущности

- **User** — id, имя, email
- **Product** — id, название, цена
- **Order** — id, пользователь, список товаров, статус, дата создания

## Статусы заказа

`NEW` → `PAID` → `SHIPPED` → `COMPLETED`

## Функции

- Управление пользователями, товарами и заказами
- Добавление/удаление товаров из заказа
- Подсчёт суммы заказа
- Изменение статуса заказа

## Структура проекта

```
src/main/java/mminc/netask/
├── model/        # Сущности
├── repository/   # Доступ к данным
├── service/      # Логика
└── controller/   # REST-эндпоинты
```


## API Эндпоинты

### Пользователи `/api/users`

```http
### Создать пользователя
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "name": "Иван Иванов",
  "email": "ivan@example.com"
}

### Получить всех пользователей
GET http://localhost:8080/api/users

### Получить пользователя по ID
GET http://localhost:8080/api/users/1

### Удалить пользователя
DELETE http://localhost:8080/api/users/1
```

---

### Товары `/api/products`

```http
### Создать товар
POST http://localhost:8080/api/products
Content-Type: application/json

{
  "name": "Ноутбук",
  "price": 75000.00
}

### Получить все товары
GET http://localhost:8080/api/products

### Получить товар по ID
GET http://localhost:8080/api/products/1

### Удалить товар
DELETE http://localhost:8080/api/products/1
```

---

### Заказы `/api/orders`

```http
### Создать заказ (userId — параметр, productIds — тело)
POST http://localhost:8080/api/orders?userId=1
Content-Type: application/json

[1, 2, 3]

### Получить все заказы
GET http://localhost:8080/api/orders

### Получить заказ по ID
GET http://localhost:8080/api/orders/1

### Получить заказы пользователя
GET http://localhost:8080/api/orders/user/1

### Добавить товар в заказ
POST http://localhost:8080/api/orders/1/products/2

### Удалить товар из заказа
DELETE http://localhost:8080/api/orders/1/products/2

### Изменить статус заказа (NEW, PAID, SHIPPED, COMPLETED)
PATCH http://localhost:8080/api/orders/1/status?orderStatus=PAID

### Получить сумму заказа
GET http://localhost:8080/api/orders/1/total
```
