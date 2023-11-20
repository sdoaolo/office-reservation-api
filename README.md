# office-reservation-api
Assignment, jieun kang 2023-11 (Lotte Healthcare)



<br><br>

### 📖 노션 문서로 이동하시면 좀 더 가독성있게 읽으실 수 있습니다. 😄 
[https://www.notion.so/79dad5303f20457c82a06798534e4f4a?pvs=4](https://capable-ghost-869.notion.site/79dad5303f20457c82a06798534e4f4a?pvs=4)


<br><br>


## 🗂 **INDEX**   
- ✅ 사용 스택
- ✅ 프로젝트 개요
- ✅ 요구사항 세부정의
- ✅ 디렉터리 구조
- ✅ API
  - Demo를 위한 추가 API (직원추가, 좌석추가)
  - 요구사항에 정의된 API (직원근무상태조회, 좌석예약, 예약취소)
    1. 직원 근무 상태 조회 API - Request, Response
    2. 좌석 예약 API - Request, Response, Exception
    3. 예약 취소 API - Request, Response, Exception
    	
- ✅ MySQL 테이블 설계
  - ER Diagram
    1. 직원 테이블 (employee)
    2. 직원-좌석 테이블 (employee-seat)
    3. 좌석 테이블 (seat)
  - 테이블간 관계
  - 복합 UNIQUE 제약 조건
- ✅ Test Coverage
- ✅ 환경설정 및 데모 방법
  - setting
  - Demo (using swagger)
     - 데모 시나리오 및 사용자 가이드 
- ✅ Convention

  
<br>

---

<br><br>

# ✅ 사용 스택

![image](https://github.com/sdoaolo/office-reservation-api/assets/48430781/2322ab02-9c47-4fe1-a4af-3b81c18dcf22)

Language

- Kotlin

Server

- SpringBoot  2.7.17
- Gradle - Kotlin

DB 

- MySQL
- Spring Data JPA (Hibernate)
- QueryDsl

Document 

- Swagger 3.0.0 (API 문서 제작)

Testing

- MockK (Mockiing Library)
- IntelliJ (Test Coverage)

<br>

# ✅ 프로젝트 개요

- Office Reservation System 서버 API 개발
    - 직원들의 현재 근무 상태 확인
    - 좌석 예약
    - 예약 취소

<br>

# ✅ 요구사항 세부 정의

- 좌석 번호는 1부터 100까지 있으며 좌석은 총 100개가 있습니다.
- 직원은 1개의 좌석만 예약이 가능합니다.
- 좌석은 1명의 직원만 사용 가능합니다.
- 총 직원 수는 150명 입니다.
- 근무형태는 오피스 출근, 재택, 휴가 세가지가 있습니다.
    
    → 근무형태에 미출근을 추가했습니다.
    
    - 예약 취소: 취소한 직원의 상태를 미출근으로 변경
    - 예약 성공: 성공한 직원의 상태를 오피스출근으로 변경
    - 좌석이 모두 예약됨: 미출근 상태의 직원을 재택으로 변경
    - 매일 오전 12시: 모든 직원의 상태를 미출근으로 변경
    - 직원 추가 : 직원 데이터 추가될 때 기본 상태를 미출근으로 설정
- 좌석이 모두 예약되는 경우, 예약하지 못한 직원은 자동으로 재택근무 형태가 지정됩니다.
- 좌석이 남는 경우는 가능합니다.
    
    → “좌석이 남는 경우에 좌석 예약이 가능합니다”로 이해했습니다.
    
<br>

---

<br>

# ✅ 디렉터리 구조

```bash
├── main
│   ├── kotlin
│   │   └── com
│   │       └── lottehealthcare.officereservationsystem
│   │           ├── OfficeReservationSystemApplication.kt
│   │           ├── aop
│   │           ├── common
│   │           │   └── mapping
│   │           ├── configuration
│   │           │   └── querydsl
│   │           │   └── swagger
│   │           ├── employee
│   │           │   ├── controller
│   │           │   ├── dto
│   │           │   ├── entity
│   │           │   ├── repository
│   │           │   └── service
│   │           ├── error
│   │           │   └── exception
│   │           ├── scheduling
│   │           └── seat
│   │               ├── controller
│   │               ├── dto
│   │               ├── entity
│   │               ├── repository
│   │               └── service
│   └── resources
│       ├── application.yml
│       ├── static
│       └── templates
└── test
    ├── kotlin
    │   └── com
    │       └── lottehealthcare.officereservationsystem
    │           ├── employee
    │           │   ├── controller
    │           │   └── service
    │           └── seat
    │               ├── controller
    │               └── service
    └── resources
```

<br>

# ✅ API

- 모든 api endpoingt는  `/api/v1/` 으로 시작합니다.
- ApplicationResponseDto - 공통 응답 형태
    
    ```kotlin
    data class ApplicationResponseDto<T>(
        val status: ResponseStatus, //Enum class
        val message: String, //응답 커스텀 메세지 
        val code : Long?, //ResponseStatus의 code 넣어주기
        val isSuccess: Boolean, // true/false
        val data: T 
    )
    ```
    
<br>

### ✚ DEMO를 위한 추가 API

| name | Method | End point | param / body |
| --- | --- | --- | --- |
| 직원 등록 | POST | /seats | { "name" : "강지은" } |
| 좌석 등록 | POST  | /seats | { "seatLocation" : "롯데월드타워 27F Room A” } |

<br>

### 📝 요구사항에 정의 된 API

| name | Method | End point | param / body |
| --- | --- | --- | --- |
| 전 직원 근무형태 현황 조회  | GET | /employees/work-status | ?page={페이지번호} |
| 좌석 예약 | POST | /seats/reservations | { "employeeNumber": 7, "seatNumber": 19 } |
| 예약 취소 | DELETE  | /seats/reservations | { "employeeNumber": 7, "seatNumber": 19 } |

<br>

## 1️⃣ 전 직원 근무형태 현황 조회 API

- 모든 직원의 근무형태를 조회합니다.
    - 오피스 출근 직원의 경우 좌석번호를 함께 제공합니다.
- 20명씩 페이지네이션 처리합니다.

<img width="2026" alt="API Flow 01" src="https://github.com/sdoaolo/office-reservation-api/assets/48430781/b2afc158-7de1-4490-84b4-606514c8b7b0">

### ⏺ Request

Header

<aside>
➡️ **GET    /employees/work-status?page=1**

</aside>

### ⏺ Response

```json
{
    "status": "SUCCESS",
    "message": "현재 직원들의 근무 상태입니다.",
    "code": 200,
    "isSuccess": true,
    "data": [
        {
            "name": "a",
            "employeeNumber": 1,
            "currentWorkType": "재택"
        },
        {
            "name": "b",
            "employeeNumber": 2,
            "currentWorkType": "오피스출근"
						"seatNumber": 3
        },
        {
            "name": "c",
            "employeeNumber": 3,
            "currentWorkType": "오피스출근"
		        "seatNumber": 2
        },
        {
            "name": "d",
            "employeeNumber": 4,
            "currentWorkType": "재택"
        },
        {
            "name": "f",
            "employeeNumber": 6,
            "currentWorkType": "오피스출근",
            "seatNumber": 1
        }
        --- 생략 ---
    ]
}
```

<br>

---

<br>

## 2️⃣ 좌석 예약 API

- 직원은 본인이 사용할 좌석을 예약합니다.
- 여러 직원이 동시에 같은 좌석을 예약할 수 없습니다.
    - 동시성 제어 구현 PR (https://github.com/sdoaolo/office-reservation-api/pull/12)

<img width="2026" alt="API Flow 2" src="https://github.com/sdoaolo/office-reservation-api/assets/48430781/79195e5f-b0ff-4d49-9c2f-ad84fb40aa03">

### ⏺ Request

Header

<aside>
➡️ **POST    /seats/reservations**

</aside>

Body

```json
{
    "employeeNumber": 1, //예약자의 번호 
		"seatNumber": 2 //예약하려는 좌석 번호
}
```

### ⏺ Response

```json
{
  "status": "SUCCESS",
  "message": "좌석 예약 완료",
  "code": 200,
  "isSuccess": true,
  "data": {
		"employee_id" : 1,
		"seat_id": 2
  }
}
```

### ⏺ Exception

1. 유효 범위를 벗어났을 때
    
    employeeNumber (1~150), seatNumber(1~100)
    
    - request
        
        ```json
        {
        	"employeeNumber": 333,
        	"seatNumber": 444
        }
        ```
        
    - response
        
        ```json
        {
            "status": "BAD_REQUEST",
            "message": "employeeNumber: 150 이하여야 합니다, seatNumber: 100 이하여야 합니다",
            "code": 400,
            "isSuccess": false
        }
        ```
        

1. 데이터 자체를 잘못 넣었을 때
    - request
        
        ```json
        {
          "employeeNumber": "안녕하세요",
        	"seatNumber": 1
        }
        ```
        
    - response
        
        ```json
        {
            "timestamp": "2023-11-19T05:59:42.762+00:00",
            "status": 400,
            "error": "Bad Request",
            "path": "/api/v1/seats/reservations"
        }
        ```
        
2. 데이터가 존재하지 않음
    
    ```json
    {
    		"status": "NOT_FOUND",
        "message": "Employee Not Found",
        "code": 404,
        "isSuccess": false
    }
    ```
    
    ```json
    {
    		"status": "NOT_FOUND",
        "message": "Seat Not Found",
        "code": 404,
        "isSuccess": false
    }
    ```
    
3. 남은 좌석이 없음
    
    ```json
    {
        "status": "UNPROCESSABLE_ENTITY",
        "message": "There are no remaining seats. ",
        "code": 422,
        "isSuccess": false
    }
    ```
    
4. 이미 예약된 좌석
    
    ```json
    {
        "status": "CONFLICT",
        "message": "This seat is already reserved. Please choose a different seat",
        "code": 409,
        "isSuccess": false
    }
    ```
    
5. 이미 예약이 완료된 사용자
    
    ```json
    {
        "status": "CONFLICT",
        "message": "This user has already completed a reservation",
        "code": 409,
        "isSuccess": false
    }
    ```
    
6. 이미 예약이 완료된 사용자
    
    ```json
    {
        "status": "CONFLICT",
        "message": "This user has already completed a reservation",
        "code": 409,
        "isSuccess": false
    }
    ```
    
7. 오늘 예약했다가 취소한 좌석은 재예약 불가능
    
    ```json
    {
        "status": "BAD_REQUEST",
        "message": "Previously reserved seats cannot be re-reserved",
        "code": 400,
        "isSuccess": false
    }
    ```
    
<br>

---

<br>

## 3️⃣ 예약 취소 API

- 예약을 취소하면 다른 직원이 해당 좌석을 예약할 수 있습니다.
- 동일한 좌석은 하루에 1번만 예약이 가능합니다.
  
<img width="2026" alt="API Flow 3" src="https://github.com/sdoaolo/office-reservation-api/assets/48430781/408ef04c-607f-4519-a72e-6ce623caa30a">


### ⏺ Request

Header

<aside>
➡️ **DELETE    /seats/reservations**

</aside>

Body

```json
{
    "employeeNumber": 1, //예약자의 번호 
		"seatNumber": 1 //취소하려는 좌석 번호
}
```

### ⏺ Response

```json
{
  "status": "SUCCESS",
  "message": "예약이 취소되었습니다.",
  "code": 200,
  "isSuccess": true,
  "data": {
		"employee_id" : 1,
		"seat_id": 13
  }
}
```

### ⏺ Exception

1. 예약 정보가 없음 
    
    ```json
    {
        "status": "NOT_FOUND",
        "message": "Reservation Not Found",
        "code": 404,
        "isSuccess": false
    }
    ```

<br>


# ✅ MYSQL 테이블 설계

### ⏺ ER-Diagram

![image](https://github.com/sdoaolo/office-reservation-api/assets/48430781/5a41c21e-69cb-4ddc-96e3-ae76fdac9bb1)

### a. 직원(Employee) 테이블

- employeeId: 고유 ID
    - Primary Key (별도키)
    - BIGINT AUTO INCREMENT
- name: 성이름
    - VARCHAR(20)
    - NOT NULL
- employeeNumber - 직원 고유 번호 (1~150)
    - NOT NULL
    - SMALLINT
- currentWorkType: 현재 근무 형태
    - VARCHAR(255)
    - ‘오피스’, ‘재택’, ‘휴가’, ‘미출근’
- created_date : 직원이 입사한 날짜
    - DATE

### b. 직원-좌석(employee-seat) 테이블

- id
    - Primary Key
    - BIGINT AUTO_INCREMENT
- employee_id : FK 
(from Employees테이블- employeeId)
    - INT
    - employee : employee_seat = 1: N
- seatId : FK (from seats테이블- seatId)
    - INT
    - seat : employee_seat = 1: N
        - 직원은 예약데이터가 여러개 있을 수 있다 (UNIQUE(날짜/직원/좌석)에 의해)
        - 예약데이터 1개는 좌석 1개에 매핑된다.
- isValid: 유효한 예약 상태인지
    - BOOLEAN
    - 예약 = 1, 취소된 예약 = 0
- reserve_date : 예약이 된 날짜
    - DATE

### c. 좌석(seat) 테이블

- seatId: 좌석 ID
    - PK (별도키)
    - BIGINT AUTO INCREMENT
- seatLocation
    - VARCHAR(50)
        - 방 번호는 숫자/문자 혹은 둘의 조합일 수도 있다.
            - ex) 에비뉴엘 6F 30, 롯데월드타워 27F 4

- seatNumber 좌석 고유 번호 (1~100)
    - NOT NULL
    - SMALLINT
- created_date : 좌석이 생겨난 날짜
    - DATE

### ⏺ 테이블 간 관계

Ref: employee_seat.employee_id > employees.employeeId

- many-to-one
- 직원은 여러개의 예약정보를 만들 수 있다.
- 예약데이터 1개는 직원 1개에 매핑된다.

Ref: employee_seat.seatId > seats.seatId

- many-to-one
- 좌석은 여러개의 예약정보에 들어갈 수 있다.
- 예약데이터 1개는 좌석 1개에 매핑된다.

### ⏺ 복합 UNIQUE 제약 조건

- 직원이 하루에 한 좌석에 대해서만 유효한 예약을 할 수 있도록 설정
- UNIQUE 설정 : reserve_date, employee_id, seat_id

![image](https://github.com/sdoaolo/office-reservation-api/assets/48430781/a4a0573a-5fe3-445f-87c4-1b34e741426c)

> "직원"이 다른 "좌석"은 "같은날"에 예약하는것은 가능합니다.

> 비즈니스 로직을 통해 isValis를 체크함으로써 직원이 여러개의 좌석을 예약할 수 없도록 구현했습니다.


<br>

---

<br>

# ✅ Test Coverage

- Controller와 Service단의 단위테스트를 진행했습니다.
    
![image](https://github.com/sdoaolo/office-reservation-api/assets/48430781/8eadad79-214b-4dd6-a30a-9537c7987d63)

- 테스트 커버리지는 IntelliJ에서 확인했습니다.
    
![image](https://github.com/sdoaolo/office-reservation-api/assets/48430781/52f6e094-fb22-495c-94b1-79fbce326cc9)

<br>

---

<br>

# ✅ 환경설정 및 데모 방법

## ⏺ setting

### Local setting

- MySQL
    - **Schema:** **office_reservation_system  를 추가해주세요**
        
        ```sql
        CREATE SCHEMA 'office_reservation_system' DEFAULT CHARACTER SET utf8;
        ```
        

- SpringBoot
    - src > main > resources 아래에 application.yml을 추가해주세요
    ![image](https://github.com/sdoaolo/office-reservation-api/assets/48430781/a54541de-582c-47c8-9334-eed7893a501a)

    - application.yml
        - `username`과 `password` 설정을 해주세요
        
```bash
        spring:
          datasource:
            driver-class-name: com.mysql.cj.jdbc.Driver
            url: jdbc:mysql://localhost:3306/office_reservation_system?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
            username: {username 설정}
            password: {password 설정}
          jpa:
            open-in-view: true
            hibernate:
              ddl-auto: update
              naming:
                physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
              use-new-id-generator-mappings: false
            show-sql: true
            properties:
              hibernate.format_sql: true
              dialect: org.hibernate.dialect.MySQL8InnoDBDialect
        
        paging:
          defaultSize: 20 
```
        

## ⏺ Demo (using swagger)

1. 프로젝트를 빌드합니다.
2. 아래 Swagger 페이지로 이동합니다.
    
    http://localhost:8080/swagger-ui/index.html
    
3. [데모 시나리오 및 사용자 가이드]를 따라 데모를 진행합니다.

<img width="989" alt="스크린샷 2023-11-20 오후 6 36 40" src="https://github.com/sdoaolo/office-reservation-api/assets/48430781/826daf5b-d51d-4bfd-9be9-9be8ead1fe1d">


### 📝  데모 시나리오 및 사용자 가이드

간단한 데모를 위해서 다음과 같은 순서를 거치면 됩니다.

1. `새 직원 등록`, `새 좌석 등록`
    1. 이름과 좌석을 입력하면 DB에 데이터가 추가됩니다. 
2. 1에서 반환된 employeeNumber, seatNumber를 바탕으로 `좌석 예약`, `좌석 예약 취소` 요청
3. `현재 직원 근무 상태 조회`
    1. 데이터 추가 후 page = 1로 설정해주세요.

<br>

---

<br>

# ✅ Convention

**프로젝트 개발 관련 규칙**

## ⏺ Github

### **◾️ Commit**

| Tag Name | Description |
| --- | --- |
| Feat | 새로운 기능 추가 |
| Fix | 요구사항/설계 변경에 따른 수정 |
| Refactor | 프로덕션 코드 리팩토링 |
| Bug | 버그 수정 |

### ◾️  Branch

- merge 전략
- main
    - dev에서 main으로 병합
    - 배포/운영 환경에서 사용될 브랜치
- dev
    - 하위 브랜치 (feat/자세한-작업-내용) 에서 dev로 병합
- feat/자세한-작업-내용
    - 최하위 브랜치
