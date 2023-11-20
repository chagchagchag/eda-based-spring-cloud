package grammar

import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class ExceptionTest {

    @Test
    fun test1(){
        hello()
    }

    fun hello(){
        helloUncheckedMethod()
    }

    fun helloUncheckedMethod(){
        throw IllegalArgumentException("hello")
    }

}