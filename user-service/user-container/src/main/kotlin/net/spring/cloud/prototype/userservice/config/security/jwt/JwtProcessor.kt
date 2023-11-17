package net.spring.cloud.prototype.userservice.config.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletResponse
import net.spring.cloud.prototype.userservice.config.security.userdetails.CustomUserDetails
import net.spring.cloud.prototype.userservice.domain.dto.UserDto
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.security.Key
import java.util.*

class JwtProcessor {
    companion object {
        fun generateToken(key: Key, userDetails: CustomUserDetails): String {
            return Jwts.builder()
                .setSubject(userDetails.username)
                .setExpiration(Date(System.currentTimeMillis() + 864000000))
                .claim("id", userDetails.user.id)
                .claim("email", userDetails.user.email)
                .claim("username", userDetails.username)
                .claim("password", userDetails.password)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()
        }

        fun generateToken(key: Key, userDto: UserDto): String{
            return Jwts.builder()
                .setSubject(userDto.name)
                .setExpiration(Date(System.currentTimeMillis() + 864000000))
                .claim("id", userDto.id)
                .claim("email", userDto.email)
                .claim("username", userDto.name)
                .claim("password", userDto.encryptedPassword)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()
        }

        fun addJwtAtResponseHeader(jwt: String, response: HttpServletResponse){
            response.addHeader("Authorization", "Bearer $jwt")
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

        fun toAuthentication(key: Key, token: String): Authentication {
            val parser: JwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()

            val claimsJws : Jws<Claims> = parser.parseClaimsJws(token)
            val email = claimsJws.body["email"] as String
            val password = claimsJws.body["password"] as String
            return UsernamePasswordAuthenticationToken(email, password)
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
    }
}