### 공개키, 비공개키 암호화 tool

Java 에서는 public key, private key 를 생성하기 위한 도구로 keytool 이라는 것을 제공해주고 있다. Java 8 버전을 사용하는 경우 key size 오류, 지원되지 않는 알고리즘 등과 같은 에러 메시지를 접할 수 있고 추가적으로 별도의 Java Cryptography Extension (자바 암호화 확장판) 을 설치해야 한다. Java 11 버전 이상 부터는 별도로 keytool 관련해서 작업을 해주지 않아도 된다.<br>

<br>



### Java 8

java 8 버전 이하를 사용하면 아래와 같은 에러를 접하게 될 수 있다. (키 size 오류, 지원되지 않는 알고리즘)

- Illegal key size or default parameters

- Unsupported keysize or algorithm parameters

이 경우 아래 링크에서 Java Cryptography Extension (자바 암호화 확장판)을 다운로드 받아서 설치해줘야 한다.

https://www.oracle.com/java/technologies/javase-jce-all-downloads.html 



Window

- Oracle JDK
- {user.home}\Program Files\Java\jdk-버전\conf\security

Mac

- Oracle JDK
  - {user.home}/Library/Java/JavaVirtualMachines/jdk-버전/Contents/Home/jre/lib/security
- Open JDK
  - {user.home}/Library/Java/JavaVirtualMachines/openjdk-버전/Contents/Home/conf/security



<br>



### Java 11 이상

Java 11 버전 이상 부터는 keytool 관련해서 별도로 신경쓰지 않아도 된다.