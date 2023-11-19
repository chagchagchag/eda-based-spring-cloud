## (spring-kafka) addTrustedPackage  관련 이슈

JsonDeserializer 를 사용할 때 class is not in the trusted packages 에러가 발생하면 아래의 구문을 추가해주면 된다.

- deserializer.addTrustedPackages("\*")



참고

- https://sup2is.tistory.com/102





