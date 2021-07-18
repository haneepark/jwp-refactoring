# 키친포스
## 제품(Product)
- 생성(POST /api/products)
  - 요청 본문:
    ```json
    {
      "name": "강정치킨",
      "price": 17000
    }
    ```
  - 프로세스:
    - 제품의 가격이 0 미만 혹은 빈값인지 확인
    - 저장 후 반환

- 조회(GET /api/products)
  - 요청 본문: 없음
  - 프로세스:
    - 전체 제품 조회 후 반환

---

## 메뉴 그룹(MenuGroup)
- 생성(POST /api/menu-groups)
  - 요청 본문:
    ```json
    {
      "name": "추천메뉴"
    }
    ```
  - 프로세스: 
  - name으로 된 메뉴 그룹 삽입
- 조회(GET /api/menu-groups)
  - 요청 본문: 없음
  - 프로세스: 
    - 전체 메뉴 그룹 조회 반환

---

## 메뉴(Menu)
- 생성(POST /api/menus)
  - 요청 본문: 
    ```json
    {
      "name": "후라이드+후라이드",
      "price": 19000,
      "menuGroupId": 1,
      "menuProducts": [
        {
          "productId": 1,
          "quantity": 2
        }
      ]
    }

    ```
  - 프로세스:
    - 가격 확인: null or 0미만 여부
    - 메뉴 그룹 존재 여부 확인: 없으면 예외 던지기
    - 메뉴 제품을 조회한 후 총가격 계산(각 메뉴에 속한 메뉴 제품의 수량과 가격) 
    - 메뉴 제품 가격이 0 초 과인지 확인: 유효하지 않으면 예외
    - 메뉴 저장
    - 메뉴 제품 추출 후 저장
    - 저장된 메뉴 반환
    - 저장된 메뉴 정보를 반환
- 조회(GET /api/menus)
  - 요청 본문: 없음
  - 프로세스:
    - 등록된 전체 메뉴와 메뉴 제품을 조회한 후 반환

---

## 주문(Order)
- 생성(POST /api/orders)
  - 요청 본문:
    ```json
    {
      "orderTableId": 1,
      "orderLineItems": [
       {
          "menuId": 1,
          "quantity": 1
        } 
      ]
    }
    ```
  - 프로세스: 
    - 주문 라인 아이템 비었는지 확인
    - 주문 라인 아이템에서 메뉴 아이디 추출
    - 메뉴 아이디로 디비에 있는 데이터 카운트 후 주문 라인 아이템의 사이즈와 비교를 통해 실제 존재하는 데이터 인지 검증
    - 주문 테이블 조회: 없을 시 예외
    - 주문 테이블이 비었는지 확인
    - 주문 테이블의 아이디와 조리중 상태, 현재 시간을 적용
    - 주문을 저장
    - 주문 라인 아이템 저장
    - 주문과 주문 라인 아이템을 반환
  
- 조회(GET /api/orders)
  - 요청 본문: 없음
  - 프로세스: 
    - 주문 전체 조회
    - 해당 주문에 해당하는 라인 아이템 조회
    - 반환

- 수정(PUT /api/orders/1/order-status)
  - 요청 본문: 혹은 COMPLETION
    ```json
    {
      "orderStatus": "MEAL"
    }
    ```
  - 프로세스: 
    - 특정 아이디에 해당 하는 주문 조회: 없을 시 예외
    - 해당 주문이 완료 상태인 경우 예외
    - 요청받은 상태를 주문에 적용
    - 주문 저장
    - 관련된 전체 주문 라인 아이템을 조회 후 반환
    
---

## 주문테이블(OrderTable)
- 생성(POST /api/tables)
  - 요청 본문:
    ```json
    {
    "numberOfGuests": 0,
    "empty": true
    }
    ```
  - 프로세스:
    - 테이블 그룹 아이디를 0으로 하고 주문 테이블 저장
- 조회(GET /api/tables)
  - 요청 본문: 없음
  - 프로세스:
      - 전체 주문 테이블 저장
- 빈 상태로 변경(PUT /api/tables/1/empty)
    - 요청 본문: 없음
    - 프로세스:
      - ID 기반 주문 테이블 조회
      - 테이블 그룹 NULL 여부 조회
      - 조리 중이거나 식사 중인 것이 있으면 예외 반환
      - 빈 상태로 변경 후 저장
- 고객 수 변경(PUT /api/tables/1/number-of-guests)
    - 요청 본문: 없음
    - 프로세스:
      - 고객 수 GET
      - 0 미만이면 예외
      - 주문 테이블 ID 기반 조회 없으면 예외
      - 주문 테이블이 비어있으면 예외
      - 주문 테이블의 고객 수 변경
      - 주문 테이블 저장

---

## 테이블 그룹
- 생성(POST /api/table-groups)
  - 요청 본문:
    ```json
    {
      "orderTables": [
        {
          "id": 1
        },
        {
          "id": 2
        }
      ]
    }
    ```
  - 프로세스:
    - 테이블 그룹의 주문 테이블 꺼내기
    - 주문 테이블이 비었거나 2개 미만인 경우
    - 주문 테이블의 아이디만 추출
    - 저장된 주문 테이블 모두 조회
    - 저장된 주문 테이블과 요청 받은 주문 테이블 사이즈가 다른 경우 예외
    - 주문 테이블이 비어있지 않거나, 주문 테이블이 널이 아니면 예외
    - 생성일 현재 시점 적용
    - 테이블 그룹 저장
    - 주문 테이블 저장
    - 테이블 그룹에 주문 테이블 적용 및 반환

- 삭제(DELETE /api/table-groups/1)
  - 요청 본문: 없음
  - 프로세스:
    - 테이블 그룹 아이디로 전체 주문 테이블 조회
    - 주문 테이블 중 조리 중이거나 식사 중인 것이 있는지 확인
    - 오더 테이블의 테이블 그룹 아이디를 NULL로 바꾼 뒤 저장

---