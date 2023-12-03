package net.spring.cloud.prototype.userservice.application.rest

//@RestController
//@RequestMapping("/")
class UserControllerInflearnVersion (
//    val userSagaHelper: UserSagaHelper,
//    val userResponseMapper: UserResponseMapper,
//    val environment: Environment,
){
//    @GetMapping("/welcome")
//    fun welcome(request: HttpServletRequest, response: HttpServletResponse): String{
//        return "Welcome"
//    }

//    @GetMapping("/users")
//    fun getUsers(): ResponseEntity<List<ResponseUser>> {
//        return ResponseEntity.status(HttpStatus.OK).body(userSagaHelper.getUserByAll())
//    }

//    @PostMapping("/users")
//    fun createUser(@RequestBody user: RequestUser): ResponseEntity<ResponseUser> {
//        val createUserCommand = userRequestMapper.ofUserCommand(user)
//        val userDto = userApplicationService.createUser(createUserCommand)
//        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseMapper.ofResponseUser(userDto))
//    }

//    @GetMapping("/users/{userId}")
//    fun getUser(@PathVariable("userId") userId: String): ResponseEntity<ResponseUser> {
//        val userDto = userSagaHelper.findUserByUserId(userId)
//        return ResponseEntity.status(HttpStatus.OK).body(userResponseMapper.ofResponseUser(userDto))
//    }

    // spring cloud config 를 통해 얻어오는 정보다.
//    @GetMapping("/health-check")
//    fun status(): String {
//        return StringBuilder()
//            .append("user-service at /health-check")
//            .append(", port(local.server.port) = ").append(environment.getProperty("local.server.port"))
//            .append(", port(server.port) = ").append(environment.getProperty("server.port"))
//            .append(", gateway ip = ").append(environment.getProperty("gateway.ip"))
//            .append(", message = ").append(environment.getProperty("greeting.message"))
//            .append(", token.secret = ").append(environment.getProperty("token.secret"))
//            .append(", token expiration time = ").append(environment.getProperty("token.expiration_time"))
//            .toString()
//    }
}