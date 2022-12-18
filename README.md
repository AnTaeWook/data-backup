# 💾 데이터 송수신 소켓 서버
멀티 스레딩 기반 데이터 전송 서버와 클라이언트 입니다

---

### 구성 애플리케이션
- **resource**: 데이터 생성 애플리케이션
- **server**: 생성된 데이터를 읽어 클라이언트로 전달하는 서버
- **client**: 서버로부터 데이터를 받아 저장하는 클라이언트


**DB 연동** : **clone** 후 **src/main/resources/application.yml** 파일 내 mysql 연동 설정
>각 애플리케이션 모두 설정해야 함

---

## 빌드 및 실행

### macos(console)

```
./gradlew build  
cd build/libs  
java -jar {application-name}-SNAPSHOT.jar  
```

### windows(console)

```
gradlew.bat
gradlew build
cd build/libs
java -jar {application-name}-SNAPSHOT.jar
```

---

## 주의 사항

- 생성할 데이터 개수는 resource/src/main/java/assignment/resource/App.java 내 변수로 지정할 수 있습니다.
