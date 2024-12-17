- 클라이언트와 서버가 주고받는 데이터
    - Client <- `DTO` -> Controller <- `DTO` -> Service <- `DTO` -> Repository(DAO) <- `Domain`(Entity) -> DataBase
<!-- - [DTO와 Domain을 구분하는 이유](https://mangkyu.tistory.com/192) -->

- 기본형 / 참조형
    - default 값이 없는 경우 참조형으로 선언
        - `@ColumnDefalut`만 사용한 경우에도
        - ```java
            @Id
            @GeneratedValue
            private Integer no; // default 값이 없는 경우 참조형으로 선언
            ```
    - default 값이 있는 경우 기본형으로 선언
        - ```java
            @Column(nullable = false)
            @Builder.Default
            private int isPublic = 1; // default 값이 있는 경우 기본형으로 선언
            ```
- `@ColumnDefalut`
    - 테이블 생성 시 DDL에 포함될 컬럼의 기본 값 지정
- `@Builder.Default`
    - 객체 생성 시 기본 값 지정
