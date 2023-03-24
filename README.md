# interpark-assignment

## 기술 스펙
jdk 17, springboot, h2, mysql

## 어플리케이션 구동 방법
1. 프로젝트를 build 합니다.
2. (intellij의 경우) AssignmentApplication -> Edit Configuration 클릭합니다.
2-1. (H2 Database 사용시) 'development' 기재합니다.
2-2. (MySQL 사용시) 'production' 기재합니다. 그 후 application.yml 파일 중 on-profile이 'proddb'인 datasource에 MySQL 정보를 입력합니다.
3. 'Run AssignmentApplication' 클릭합니다.
4. 'http://localhost:10101'로 접속합니다.
5. 맨 처음 member-id를 받기 위해 [POST] http://localhost:10101/member 호출합니다.
```json
{
  "name" : "test"
}
```
6. 도시 생성, 여행 생성, 단일 도시 조회, 사용자 도시 조회 API 호출시에는 header에 key값을 member-id 지정한 후 1번에서 받은 member-id를 value에 넣어줍니다.

## API 명세
1. 회원가입 : [POST] http://localhost:10101/member
```json
{
  "name" : "test"
}
```

2. 도시 등록 : [POST] http://localhost:10101/city
```json
// [header] key: member-id, value: memberId

{
  "name" : "test"
}
```

3. 도시 수정 : [POST] http://localhost:10101/city/{cityId}
```json
{
  "name" : "test"
}
```

4. 도시 삭제 : [DELETE] http://localhost:10101/city/{cityId}

5. 단일 도시 조회 : [GET] http://localhost:10101/city/{cityId}
```json
[header] key: member-id, value: memberId
```

6. 사용자별 도시 조회 : [GET] http://localhost:10101/city
```json
// [header] key: member-id, value: memberId
```

7. 여행 등록 : [POST] http://localhost:10101/travel
```json
// [header] key: member-id, value: memberId

{
  "cityId":4,
  "startDate":"2023-03-01",
  "endDate":"2023-03-05"
}
```

8. 여행 수정 : [POST] http://localhost:10101/travel/{travelId}
```json
{
  "cityId":4,
  "startDate":"2023-03-01",
  "endDate":"2023-03-05"
}
```

9. 여행 삭제 : [DELETE] http://localhost:10101/travel/{travelId}

10. 단일 여행 조회 : [GET] http://localhost:10101/travel/{travelId}

## Test Code
- API 통합 테스트 (Acceptance Test)의 경우 src -> test -> integration 패키지 밑에 위치 
- 단위 테스트의 경우 src -> test -> unit 패키지 밑에 위치 
