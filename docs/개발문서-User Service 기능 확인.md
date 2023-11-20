## User Service 기능 확인

로그인, JWT 헤더 기반 인증 등의 로직을 모두 완료했지만, 현재 주문서비스/조회서비스 프로젝트를 EDA 기반으로 전환중에 있어서 User Service 기능의 경우 로컬에서만 동작됩니다.<br>

User Service 의 기능은 로컬에서 아래와 같이 확인 가능합니다.<br>

<br>



> **User Container 기능 확인**<br>

- POST `/signup`

  - curl

    - ```bash
      curl -d '{"name": "asdf", "email": "asdf@gmail.com", "password": "aaaa"}' -H "Content-Type : application/json" -X POST http://localhost:8000/user-service/signup
      ```

  - .http

    - 인텔리제이에서 user-service/user-container/src/test/kotlin 으로 이동후 `net.spring.cloud.prototype.user` 패키지 아래의 integration\_test 디렉터리에서 아래의 파일을 실행
    - `signup.http`

  - postman

    - POST http://localhost:8000/user-service/signup
    - Request Body (Raw, Json) : \{"name": "asdf", "email": "asdf@gmail.com", "password": "aaaa"\}

- POST `/login`

  - curl 

    - ```bash
      curl -d '{"name": "asdf", "email": "asdf@gmail.com", "password": "aaaa"}' -H "Content-Type : application/json" -X POST http://localhost:8000/user-service/login
      ```

  - .http

    - 인텔리제이에서 user-service/user-container/src/test/kotlin 으로 이동후 `net.spring.cloud.prototype.user` 패키지 아래의 integration\_test 디렉터리에서 아래의 파일을 실행
    - `login.http`

  - postman

    - POST http://localhost:8000/user-service/login
    - Request Body (Raw, Json) : \{"name": "asdf", "email": "asdf@gmail.com", "password": "aaaa"\}

<br>



