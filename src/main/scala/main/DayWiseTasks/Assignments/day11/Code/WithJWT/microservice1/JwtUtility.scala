package main.DayWiseTasks.Assignments.day11.Code.WithJWT.microservice1


import pdi.jwt.{Jwt, JwtAlgorithm}
import spray.json._

object JwtUtility {
  val secretKey = "mySecretKey"

  def validateToken(token: String): Boolean = {
    Jwt.isValid(token, secretKey, Seq(JwtAlgorithm.HS256))
  }

  def createToken(payload: JsObject): String = {
    Jwt.encode(payload.compactPrint, secretKey, JwtAlgorithm.HS256)
  }
}
