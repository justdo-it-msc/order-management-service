## 간단한 주문 관리 서비스

### 상품 CRUD

- 상품을 등록할 수 있어야 합니다.

- 상품을 단건/목록 조회할 수 있어야 합니다.

- 상품을 수정할 수 있어야 합니다.

- 상품을 삭제할 수 있어야 합니다.

### 주문 생성 및 조회

- 이미 등록된 상품의 ID를 이용해서 주문을 생성할 수 있어야 합니다.
  - 주문 시 상품은 1개만 선택할 수 있습니다.

- 주문을 단건 조회할 수 있어야 합니다.
  - 주문을 조회할 때 주문한 상품의 이름이 함께 노출되어야 합니다.

  - 상품 수정에서 상품 이름을 변경했을 때, 해당 상품으로 이미 생성된 주문을 조회하면 주문 조회 결과의 상품 이름도 변경된 이름으로 보여야 합니다.

### 주문 목록 조회

한 주문에 여러 주문을 페이지네이션 조회할 수 있도록 구성합니다.

- 주문 목록 조회 API를 구현합니다.

- 응답 데이터에는 상품 이름이 포함되어야 합니다.

- 목록 조회 시 N + 1 문제가 발생하지 않도록 구성해야 합니다.

### 상품 재고 차감

상품 도메인에 재고(stock) 정보를 추가하고, 주문 생성 시 재고가 차감되도록 구현합니다.

- 주문 생성 시 주문한 수량에 따라 stock이 감소하도록 처리합니다.

- 재고가 0일 때는 주문이 생성되지 않도록 처리합니다.

- 재고가 원자적으로(Atomic) 차감될 수 있도록 합니다.

## Request / Response

### 상품 CRUD

- CREATE

  Request

  ```
  http://localhost:8080/api/products
  ```

  ```
  curl -X 'POST' \
    'http://localhost:8080/api/products' \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
    "name": "자전거",
    "price": 600000,
    "stock": 10
  }'
  ```

  Response

  ```
  {
    "createdAt": "2026-02-03T14:48:28.344709",
    "id": 1,
    "name": "자전거",
    "price": 600000,
    "stock": 10,
    "updatedAt": "2026-02-03T14:48:28.344732"
  }
  ```

- UPDATE

  Request

  ```
  http://localhost:8080/api/products/1
  ```

  ```
  curl -X PUT \
    'http://localhost:8080/api/products/1' \
    -H 'Content-Type: application/json' \
    -d '{
      "name": "자전거",
      "price": 800000,
      "stock": 10
  }'
  ```

  Response

  ```
  {
    "createdAt": "2026-02-03T14:48:28.344709",
    "id": 1,
    "name": "자전거",
    "price": 800000,
    "stock": 10,
    "updatedAt": "2026-02-03T14:48:28.344732"
  }
  ```

- READ (단건 조회)

  Request

  ```
  http://localhost:8080/api/products/1
  ```

  ```
  curl -X 'GET' \
    'http://localhost:8080/api/products/1' \
    -H 'accept: */*'
  ```

  Response

  ```
  {
    "createdAt": "2026-02-03T14:48:28.344709",
    "id": 1,
    "name": "자전거",
    "price": 800000,
    "stock": 10,
    "updatedAt": "2026-02-03T15:13:58.347199"
  }
  ```

- READ (목록 조회)

  Request

  ```
  curl -X 'GET' \
    'http://localhost:8080/api/products' \
    -H 'accept: */*'
  ```

  Response

  ```
  [
    {
      "createdAt": "2026-02-03T14:48:28.344709",
      "id": 1,
      "name": "자전거",
      "price": 800000,
      "stock": 10,
      "updatedAt": "2026-02-03T15:13:58.347199"
    },
    {
      "createdAt": "2026-02-03T15:22:59.102329",
      "id": 2,
      "name": "오토바이",
      "price": 2000000,
      "stock": 5,
      "updatedAt": "2026-02-03T15:22:59.102423"
    }
  ]
  ```

- DELETE

  Request

  ```
  http://localhost:8080/api/products/2
  ```

  ```
  curl -X 'DELETE' \
    'http://localhost:8080/api/products/2' \
    -H 'accept: */*'
  ```

### 주문 CRUD

- CREATE

  Request

  ```
  http://localhost:8080/api/orders
  ```

  ```
  curl -X 'POST' \
    `'http://localhost:8080/api/orders' \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
    "productId": 1,
    "quantity": 5,
    "status": "PENDING"`
  }'
  ```

  Response

  ```
  {
    "createdAt": "2026-02-03T15:41:40.211866",
    "id": 1,
    "productId": 1,
    "productName": "자전거",
    "quantity": 5,
    "status": "PENDING",
    "updatedAt": "2026-02-03T15:41:40.211926"
  }
  ```

  5개 주문 후 해당 상품 재고를 확인하면 5개가 남은 것을 확인할 수 있다.

  ```
  {
    "createdAt": "2026-02-03T14:48:28.344709",
    "id": 1,
    "name": "자전거",
    "price": 800000,
    "stock": 5,
    "updatedAt": "2026-02-03T15:41:40.230608"
  }
  ```

- UPDATE

  Request

  ```
  http://localhost:8080/api/orders/1
  ```

  ```
  curl -X 'PUT' \
    'http://localhost:8080/api/orders/1' \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
    "productId": 1,
    "quantity": 6,
    "status": "PENDING"
  }'
  ```

  Response

  ```
  {
    "createdAt": "2026-02-03T15:50:01.6548",
    "id": 1,
    "productId": 1,
    "productName": "자전거",
    "quantity": 6,
    "status": "PENDING",
    "updatedAt": "2026-02-03T15:50:01.654811"
  }
  ```

  주문을 수정하면 재고도 함꼐 수정되는 것을 볼 수 있다.

  ```
  {
    "createdAt": "2026-02-03T15:49:52.448181",
    "id": 1,
    "name": "자전거",
    "price": 600000,
    "stock": 4,
    "updatedAt": "2026-02-03T15:50:26.465106"
  }
  ```

- READ (단건 조회)

  Request

  ```
  http://localhost:8080/api/orders/1
  ```

  ```
  curl -X 'GET' \
    'http://localhost:8080/api/orders/1' \
    -H 'accept: */*'
  ```

  Response

  ```
  {
    "createdAt": "2026-02-03T15:50:01.6548",
    "id": 1,
    "productId": 1,
    "productName": "자전거",
    "quantity": 6,
    "status": "PENDING",
    "updatedAt": "2026-02-03T15:50:26.463462"
  }
  ```

- READ (목록 조회)

  Request

  ```
  http://localhost:8080/api/orders?page=0&size=3&sort=createdAt,DESC
  ```

  ```
  curl -X 'GET' \
    'http://localhost:8080/api/orders?page=0&size=3&sort=createdAt,DESC' \
    -H 'accept: */*'
  ```

  Response

  ```
  {
    "content": [
      {
        "createdAt": "2026-02-03T15:57:35.528682",
        "id": 5,
        "productId": 1,
        "productName": "자전거",
        "quantity": 1,
        "status": "PENDING",
        "updatedAt": "2026-02-03T15:57:35.528699"
      },
      {
        "createdAt": "2026-02-03T15:57:34.939204",
        "id": 4,
        "productId": 1,
        "productName": "자전거",
        "quantity": 1,
        "status": "PENDING",
        "updatedAt": "2026-02-03T15:57:34.93923"
      },
      {
        "createdAt": "2026-02-03T15:57:34.270058",
        "id": 3,
        "productId": 1,
        "productName": "자전거",
        "quantity": 1,
        "status": "PENDING",
        "updatedAt": "2026-02-03T15:57:34.270076"
      }
    ],
    "empty": false,
    "first": true,
    "last": false,
    "number": 0,
    "numberOfElements": 3,
    "pageable": {
      "offset": 0,
      "pageNumber": 0,
      "pageSize": 3,
      "paged": true,
      "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
      },
      "unpaged": false
    },
    "size": 3,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "totalElements": 5,
    "totalPages": 2
  }
  ```

- DELETE

  Request

  ```
  http://localhost:8080/api/orders/1
  ```

  ```
  curl -X 'DELETE' \
    'http://localhost:8080/api/orders/1' \
    -H 'accept: */*'
  ```

### 주문 목록 조회 화면 캡쳐

<img width="2784" height="1764" alt="Image" src="https://github.com/user-attachments/assets/3f8d5932-fe0d-412f-b6fa-f8f52c093291" />

### 재고 부족 시 화면 캡쳐

<img width="2784" height="1764" alt="Image" src="https://github.com/user-attachments/assets/0f72205b-a967-4ada-9e13-93084c1efc6f" />
