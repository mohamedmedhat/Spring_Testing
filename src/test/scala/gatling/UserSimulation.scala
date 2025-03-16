package gatling

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class UserSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8081/api/v1/users")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  val createUserFeeder = csv("users.csv").circular

  val createUserScenario = scenario("Create User")
    .feed(createUserFeeder)
    .exec(
      http("Create User")
        .post("")
        .body(StringBody("""{ "name": "${name}", "email": "${email}" }""")).asJson
        .check(status.is(201))
    )

  val getUsersScenario = scenario("Get All Users")
    .exec(
      http("Get All Users")
        .get("")
        .check(status.is(200))
    )

  val getUserByIdScenario = scenario("Get User by ID")
    .exec(
      http("Get User by ID")
        .get("/1")
        .check(status.is(200))
    )

  val updateUserScenario = scenario("Update User")
    .exec(
      http("Update User")
        .post("/1")
        .body(StringBody("""{ "name": "Updated Name" }""")).asJson
        .check(status.is(200))
    )

  val deleteUserScenario = scenario("Delete User")
    .exec(
      http("Delete User")
        .delete("/1")
        .check(status.is(204))
    )

  setUp(
    createUserScenario.inject(rampUsers(50).during(10.seconds)),
    getUsersScenario.inject(rampUsers(50).during(10.seconds)),
    getUserByIdScenario.inject(rampUsers(50).during(10.seconds)),
    updateUserScenario.inject(rampUsers(50).during(10.seconds)),
    deleteUserScenario.inject(rampUsers(50).during(10.seconds))
  ).protocols(httpProtocol)
}
