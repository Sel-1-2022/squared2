package sel.group9.squared2

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull

import org.junit.Test
import sel.group9.squared2.data.*


class ServerUnitTests {
  
  @Test
  fun createGetDeleteUser() {
    val server = Backend(true)
    
    val id = runBlocking {
      server.postUser(
        UserInfo(
          "Backend Unit Test",
          UserLocation(0.0, 0.0),
          2
        )
      )
    }
    assertNotNull(id)
    val user = runBlocking { server.getUser(id!!) }
    assertNotNull(user)
    assertEquals("Backend Unit Test", user.nickname)
    assertEquals(2, user.color)
    assertEquals(0.0, user.location.coordinates[0], 0.00001)
    assertEquals(0.0, user.location.coordinates[1], 0.00001)
    val deleteStatus = runBlocking { server.deleteUser(id!!) }
    assertEquals(0, deleteStatus)
  }
  
  @Test
  fun createGetDeleteInvalidUser() {
    val server = Backend(true)
    val id = runBlocking {
      server.postUser(
        UserInfo(
          "%\$#^\$&^\$%&^*!@^%*&#%",
          UserLocation(0.0, 0.0),
          -1
        )
      )
    }
    assertNotNull(id)
    val user = runBlocking { server.getUser(id!!) }
    assertNotNull(user)
    assertEquals("%\$#^\$&^\$%&^*!@^%*&#%", user.nickname)
    assertEquals(-1, user.color)
    assertEquals(0.0, user.location.coordinates[0], 0.00001)
    assertEquals(0.0, user.location.coordinates[1], 0.00001)
    val deleteStatus = runBlocking { server.deleteUser(id!!) }
    assertEquals(0, deleteStatus)
  }
  
  @Test
  fun patchUser() {
    val server = Backend(true)
    val id = runBlocking {
      server.postUser(
        UserInfo("Backend Unit Test", UserLocation(0.0, 0.0), 2)
      )
    }
    assertNotNull(id)
    var user = runBlocking { server.getUser(id!!) }
    assertNotNull(user)
    assertEquals("Backend Unit Test", user.nickname)
    assertEquals(2, user.color)
    assertEquals(0.0, user.location.coordinates[0], 0.00001)
    assertEquals(0.0, user.location.coordinates[1], 0.00001)
    val newid = runBlocking {
      server.patchUser(
        id!!, UserInfo("Backend Unit Test patched", UserLocation(0.0, 0.0), 2)
      )
    }
    assertNotNull(newid)
    assertEquals(id,newid)
    user = runBlocking { server.getUser(newid!!) }
    assertEquals("Backend Unit Test patched", user.nickname)
    assertEquals(2, user.color)
    assertEquals(0.0, user.location.coordinates[0], 0.00001)
    assertEquals(0.0, user.location.coordinates[1], 0.00001)
    val deleteStatus = runBlocking { server.deleteUser(id!!) }
    assertEquals(0, deleteStatus)
  }
  
  
  @Test
  fun userValidLocations() {
    val longitudes = listOf(120.4646537657)
    val latitudes = listOf(0.0)
    assertEquals(longitudes.size, latitudes.size)
    
    longitudes.forEach { lon ->
      latitudes.forEach { lat ->
        val server = Backend(true)
        val id = runBlocking {
          server.postUser(
            UserInfo("Backend Unit Test", UserLocation(lat, lon), 2)
          )
        }
        assertNotNull(id)
        val user = runBlocking { server.getUser(id!!) }
        assertNotNull(user)
        assertEquals("Backend Unit Test", user.nickname)
        assertEquals(2, user.color)
        assertEquals(lon, user.location.coordinates[0], 0.00001)
        assertEquals(lat, user.location.coordinates[1], 0.00001)
        val deleteStatus2 = runBlocking { server.deleteUser(id!!) }
        assertEquals(0, deleteStatus2)
      }
    }
    
  }
  
  @Test
  fun userInvalidLocation() {
    val longitudes = listOf(1000.0)
    val latitudes = listOf(0.0)
    assertEquals(longitudes.size, latitudes.size)
    longitudes.forEach { lon ->
      latitudes.forEach { lat ->
        val server = Backend(true)
        val id = runBlocking {
          server.postUser(
            UserInfo("Backend Unit Test", UserLocation(lat, lon), 2)
          )
        }
        assertNull(id)
      }
    }
  }
  
  @Test
  fun nearbyUser() {
    val server = Backend(true)
    
    val p1Id = runBlocking {
      server.postUser(UserInfo("Backend Unit Test Nearby player 1", UserLocation(90.0, 90.0), 2))
    }
    assertNotNull(p1Id)
    val p1 = runBlocking { server.getUser(p1Id!!) }
    assertNotNull(p1)
    assertEquals("Backend Unit Test Nearby player 1", p1.nickname)
    assertEquals(2, p1.color)
    assertEquals(90.0, p1.location.coordinates[0], 0.00001)
    assertEquals(90.0, p1.location.coordinates[1], 0.00001)
    
    val p2Id = runBlocking {
      server.postUser(
        UserInfo("Backend Unit Test Nearby player 2", UserLocation(90.0, 90.0), 2)
      )
    }
    assertNotNull(p2Id)
    val p2 = runBlocking { server.getUser(p2Id!!) }
    assertNotNull(p2)
    assertEquals("Backend Unit Test Nearby player 2", p2.nickname)
    assertEquals(2, p2.color)
    assertEquals(90.0, p2.location.coordinates[0], 0.00001)
    assertEquals(90.0, p2.location.coordinates[1], 0.00001)
    
    val nearbyUsers = runBlocking {
      server.nearbyUsers(UserLocation(90.0, 90.0), 10.0)
    }
    
    assert(nearbyUsers.size >= 2)
    assert(
      ("Backend Unit Test Nearby player 1" == nearbyUsers[0].nickname) ||
          ("Backend Unit Test Nearby player 2" == nearbyUsers[0].nickname)
    )
    assert(
      ("Backend Unit Test Nearby player 1" == nearbyUsers[1].nickname) ||
          ("Backend Unit Test Nearby player 2" == nearbyUsers[1].nickname)
    )
    
    val deleteStatus1 = runBlocking { server.deleteUser(p1Id!!) }
    assertEquals(0, deleteStatus1)
    val deleteStatus2 = runBlocking { server.deleteUser(p2Id!!) }
    assertEquals(0, deleteStatus2)
  }
  
  @Test
  fun addTile()
  {
    val server = Backend(true)
    val id = runBlocking {
      server.postUser(
        UserInfo("Backend Unit Test", UserLocation(0.0, 0.0), 2))
    }
    assertNotNull(id)
    val result = runBlocking {
      server.addTile(id!!, UserLocation(0.0, 0.0), 2)
    }
    
    assertEquals("1000000010000000", result._id)
    assertEquals(2, result.color)
  }
  
  @Test
  fun nearbyTiles()
  {
    val server = Backend(true)
    val id = runBlocking {
      server.postUser(
        UserInfo("Backend Unit Test", UserLocation(0.0, 0.0), 2))
    }
    assertNotNull(id)
    runBlocking {
      server.addTile(id!!,UserLocation(0.0, 0.0),2)
    }
    val result = runBlocking {
      server.nearbyTiles(UserLocation(0.0, 0.0), 5.0)
    }
    assert(result.size == 1)
    assert(result[0].color == 2)
    assert(result[0].lat == 0.0)
    assert(result[0].lon == 0.0)
  }
  
}
