package net.spring.cloud.prototype.orderservice.auth.jwt

import io.jsonwebtoken.*
import jakarta.servlet.http.HttpServletResponse
import java.security.Key
import java.util.*

class JwtProcessor {
    companion object{
        fun generateToken(key: Key, jwtCreateCommand: JwtCreateCommand): String{
            return Jwts.builder()
                .setSubject(jwtCreateCommand.name)
                .setExpiration(Date(System.currentTimeMillis() + 864000000))
                .claim("id", jwtCreateCommand.id)
                .claim("email", jwtCreateCommand.email)
                .claim("username", jwtCreateCommand.name)
                .claim("password", jwtCreateCommand.encryptedPassword)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()
        }

        fun degenerateToken(key: Key, token: String): ParsedJwt {
            val parser: JwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()

            val claimsJws : Jws<Claims> = parser.parseClaimsJws(token)

            return ParsedJwt.of(
                id = claimsJws.body["id"] as String,
                email = claimsJws.body["email"] as String,
                username = claimsJws.body["name"] as String,
                password = claimsJws.body["password"] as String,
                expiration = claimsJws.body.expiration
            )
        }

        fun checkContainsBearer(header: String) : Boolean {
            val len = "Bearer ".length
            return header.substring(0, len).equals("Bearer", ignoreCase = true)
        }

        // 만료됐으면 true
        // 만료되지 않으면 false
        fun checkIfExpired(expiration: Date): Boolean {
            return expiration.after(Date())
        }

        fun addJwtAtResponseHeader(jwt: String, response: HttpServletResponse){
            response.addHeader("Authorization", "Bearer $jwt")
        }
    }
}