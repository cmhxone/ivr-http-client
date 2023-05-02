# IVR(Java 7) HTTP 클라이언트 라이브러리

---

### 라이브러리 메소드

[`com.ivr.util.makeJsonString`](lib/src/main/java/com/ivr/util/JsonUtil.java)
[`com.ivr.rest.request`](lib/src/main/java/com/ivr/rest/Client.java)

---

### 빌드

`$> gradle shadowJar`

### 테스트 실행

Java 클래스패스에 [`restclient.properties`](lib/src/main/resources/restclient.properties) 파일 추가 후

- 리눅스: `$> java -cp "{클래스패스}:./lib/build/libs/rest-client-1.0.0.jar" com.ivr.Application`
- 윈도우: `$> java -cp "{클래스패스};./lib/build/libs/rest-client-1.0.0.jar" com.ivr.Application`
