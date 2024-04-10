# office-reservation-api


## 🗂 **INDEX**   
- ✅ Architecture & Stack
- ✅ Project : Server of Office Reservation System
- ✅ Detailed Requirements Definition
- ✅ Structure of Directory 
- ✅ API
  - Additional API (for DEMO) (Employee Registration, Seat Registration)
  - Defined API
    1. View All Employee Work Status API - Request, Response
    2. Seat Reservation API - Request, Response, Exception
    3. Cancel Seat Reservation API - Request, Response, Exception
    	
- ✅ Database Design (MySQL)
  - ER Diagram
    1. Table : employee
    2. Table : employee-seat
    3. Table : seat
  - Relationships between Tables
  - Compound UNIQUE Constraints
- ✅ Test Coverage
- ✅ 환경설정 및 데모 방법
  - setting
  - Demo (using swagger)
     - 데모 시나리오 및 사용자 가이드 
- ✅ Convention

  
<br>

---

<br><br>

# ✅ Architecture & Stack


<img width="600" alt="Architecture" src="https://github.com/sdoaolo/office-reservation-api/assets/48430781/81eea9c1-46ac-4e24-86aa-c0d3bd4398a0">


<br><br>

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

<br><br>

# ✅ **Server of Office Reservation System**

- View All Employee Work Status API
- Seat Reservation API
- Cancel Seat Reservation API

<br>

# ✅ **Detailed Requirements Definition**

- There are a total of **100 seats** available (1~100).
- Each **employee can reserve** only **[one seat].**
- Each **seat can be used** by only **[one employee]**.
- Total number of **employees: 150.**
- **Work Status: In-office / Remote / Vacation / No show.**
    - **Additinal Definition**
    - Reservation cancellation: Change the status of the employee who cancelled to [No show].
    - Successful reservation: Change the status of the employee who succeeded to [In-office].
    - When all seats are reserved: Change the status of employees who didn't show up to [Remote].
    - Every day at 12 AM: Change the status of all employees to [No show].
    - Adding an employee: When adding employee data, set the default status to [No show].
- When all seats are reserved, employees who did not reserve a seat are automatically assigned to work remotely.
- If there are remaining seats, employees can reserve a seat.

    
<br>

---

<br>

# ✅ Structure of Directory 

```bash
├── main
│   ├── kotlin
│   │   └── com
│   │       └── sdoaolo.officereservationsystem
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
    │       └── sdoaolo.officereservationsystem
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

- All API endpoints start with   `/api/v1/`
- `ApplicationResponseDto`  (common response type)
    
    ```kotlin
    data class ApplicationResponseDto<T>(
        val status: ResponseStatus, //Enum class
    	val message: String, //custom message
    	val code : Long?, //ResponseStatus' code
    	val isSuccess: Boolean, // true/false
    	val data: T 
    )
    ```
    
<br>

### Additional API  (for DEMO)

| name | Method | End point | param / body |
| --- | --- | --- | --- |
| Employee Registration | POST | /seats | { "name" : "강지은" } |
| Seat Registration | POST  | /seats | { "seatLocation" : "롯데월드타워 27F Room A” } |

<br>

### 📝 Defined API
| name | Method | End point | param / body |
| --- | --- | --- | --- |
| View All Employee Work Status   | GET | /employees/work-status | ?page=1 |
| Seat Reservation | POST | /seats/reservations | { "employeeNumber": 7, "seatNumber": 19 } |
| Cancel Seat Reservation | DELETE  | /seats/reservations | { "employeeNumber": 7, "seatNumber": 19 } |

<br>

## 1️⃣ View All Employee Work Status  API

- Check the work status of all employees.
    - For employees whose work mode is "in-office," their seat numbers are also provided.
- Implement pagination to display 20 people per page.


<img width="800" alt="API Flow 전 직원 근무형태 조회" src="https://github.com/sdoaolo/office-reservation-api/assets/48430781/63d8f2f2-c819-466d-be91-3aca728e19b0">


### ⏺ Request

Header
```
GET    /employees/work-status?page=1
```

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

## 2️⃣ Seat Reservation API

- Employees reserve seats for today.
- Multiple employees cannot reserve the same seat at the same time
    - Implement concurrency control.  

	PR (https://github.com/sdoaolo/office-reservation-api/pull/12)
        https://jie0025.tistory.com/604

  
<img width="800" alt="API Flow 좌석 예약" src="https://github.com/sdoaolo/office-reservation-api/assets/48430781/d0d80c09-5bc1-49eb-b729-95e064266fbf">


### ⏺ Request

Header

```
POST    /seats/reservations
```

Body

```json
{
	"employeeNumber": 1, //Number of Employee
	"seatNumber": 2 //Number of the seat the employee wants to reserve
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

1. Out of the effective range
    
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
        

2. Data entered incorrectly    
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
        
3. No data exists    
    
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
    
4. No seats available 
    
    ```json
    {
        "status": "UNPROCESSABLE_ENTITY",
        "message": "There are no remaining seats. ",
        "code": 422,
        "isSuccess": false
    }
    ```
    
5. Seat already reserved
    
    ```json
    {
        "status": "CONFLICT",
        "message": "This seat is already reserved. Please choose a different seat",
        "code": 409,
        "isSuccess": false
    }
    ```
    
6. User has already made a reservation
    
    ```json
    {
        "status": "CONFLICT",
        "message": "This user has already completed a reservation",
        "code": 409,
        "isSuccess": false
    }
    ```
    
7. Seats canceled today cannot be rebooked
    
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

## 3️⃣ **API : Cancel Seat Reservation**

- If an employee cancels their reservation, another employee can reserve the seat.
- If a seat is canceled by any employee, it cannot be re-booked on the same day.

<br>

 <img width="800" alt="API Flow 좌석 예약 취소" src="https://github.com/sdoaolo/office-reservation-api/assets/48430781/ac009f2a-318a-464e-8fa9-31d934567889">

<br>


### ⏺ Request

Header

```
DELETE    /seats/reservations
```

Body

```json
{
    "employeeNumber": 1, //The Employee Number
    "seatNumber": 2 //The seat number the employee wishes to cancel
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

1.  No data(reservation) exists 
    
    ```json
    {
        "status": "NOT_FOUND",
        "message": "Reservation Not Found",
        "code": 404,
        "isSuccess": false
    }
    ```

<br>


# ✅ **Database Design (MySQL)**

### ⏺ ER-Diagram

![image](https://github.com/sdoaolo/office-reservation-api/assets/48430781/5a41c21e-69cb-4ddc-96e3-ae76fdac9bb1)


### **. Table : employee**

- employeeId
    - Primary Key
    - BIGINT AUTO INCREMENT
- name
    - VARCHAR(20)
    - NOT NULL
- employeeNumber - (1~150)
    - NOT NULL
    - SMALLINT
- currentWorkType
    - VARCHAR(255)
    - **In-office / Remote / Vacation / No show**
- created_date : Date the employee's account was created
    - DATE

### **b. Table : employee-seat**

- id
    - Primary Key
    - BIGINT AUTO_INCREMENT
- employee_id : FK (from Employees_employeeId)
    - INT
    - employee : employee_seat = 1: N
- seatId : FK (from seats_seatId)
    - INT
    - seat : employee_seat = 1: N
- isValid (It will notify you if the reservation is valid.)
    - BOOLEAN
    - Current Reservation (Valid)
    - Canceled Reservation (Invalid)
- reserve_date
    - DATE

### **c. Table : seat**

- seatId
    - PK
    - BIGINT AUTO INCREMENT
- seatLocation
    - VARCHAR(50)
        - Room numbers can consist of numbers, letters, or a combination of both.
            - Example) AA Building 3F 20, BBB Tower 6F 4
- seatNumber (1~100)
    - NOT NULL
    - SMALLINT
- created_date (Date the seat was created)
    - DATE
 

<br>


### **⏺ Relationships between Tables**

**Ref: 
employee_seat.employee_id > employees.employeeId**

- Many-to-One Relationship:
    - Employees can create multiple entries of reservation information.
    - Each reservation record is associated with a single employee.

**Ref: employee_seat.seatId > seats.seatId**

- Many-to-One Relationship:
    - A single seat can be associated with multiple reservation records.
    - Each reservation record is associated with a single seat.

### ⏺ **Compound UNIQUE Constraints**

- To ensure that employees can make valid reservations for only one seat per day, a compound UNIQUE constraint has been applied.
- UNIQUE Constraint Settings: reserve_date, employee_id, seat_id.

![image](https://github.com/sdoaolo/office-reservation-api/assets/48430781/a4a0573a-5fe3-445f-87c4-1b34e741426c)


> It is possible to reserve different seats for different employees on the same day.
> 

> Through business logic validation (isValid check), we have implemented a system that prevents employees from making multiple reservations for seats
>







<br>

---

<br>

# ✅ Test Coverage

- Unit testing of the Controller and Service units is conducted.

<img width="600" alt="test_contents" src="https://github.com/sdoaolo/office-reservation-api/assets/48430781/8eadad79-214b-4dd6-a30a-9537c7987d63">

- Test coverage has been verified using the IntelliJ
    
<img width="800" alt="test_intelliJ" src="https://github.com/sdoaolo/office-reservation-api/assets/48430781/52f6e094-fb22-495c-94b1-79fbce326cc9">

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
<img width="600" alt="springboot_yml" src="https://github.com/sdoaolo/office-reservation-api/assets/48430781/a54541de-582c-47c8-9334-eed7893a501a">


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

<img width="1000" alt="group" src="https://github.com/sdoaolo/office-reservation-api/assets/48430781/4f54b91f-e383-4205-9287-14c822903c5b">


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
