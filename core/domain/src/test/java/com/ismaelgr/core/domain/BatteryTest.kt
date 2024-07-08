package com.ismaelgr.core.domain

import app.cash.turbine.test
import com.ismaelgr.core.domain.entity.Case
import com.ismaelgr.core.domain.entity.Case_Mock
import com.ismaelgr.core.domain.entity.Permission
import com.ismaelgr.core.domain.entity.PermissionType
import com.ismaelgr.core.domain.entity.PermissionVisibilityType
import com.ismaelgr.core.domain.entity.Permission_Mocked
import com.ismaelgr.core.domain.entity.User
import com.ismaelgr.core.domain.entity.User_Mocked
import com.ismaelgr.core.domain.repository.CaseRepository
import com.ismaelgr.core.domain.repository.PermissionsRepository
import com.ismaelgr.core.domain.repository.UsersRepository
import com.ismaelgr.core.domain.usecase.GetLawerCasesUseCase
import com.ismaelgr.core.domain.usecase.GetLawersWithAccessToGivenCaseUseCase
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class BatteryTest {
    // section Mocks
    private val usersRepository: UsersRepository = mockk()
    private val permissionsRepository: PermissionsRepository = mockk()
    private val caseRepository: CaseRepository = mockk()
    
    private lateinit var getLawerCasesUseCase: GetLawerCasesUseCase
    private lateinit var getLawersWithAccessToGivenCaseUseCase: GetLawersWithAccessToGivenCaseUseCase
    // section end
    
    // section Mocked Entities
    private val user1 = User_Mocked(name = "User1")
    private val user2 = User_Mocked(name = "User2")
    
    private val case1User1 = Case_Mock(ownerId = user1.id, material = "Material 11")
    private val case2User1 = Case_Mock(ownerId = user1.id, material = "Material 21")
    private val case1User2 = Case_Mock(ownerId = user2.id, material = "Material 12")
    private val case2User2 = Case_Mock(ownerId = user2.id, material = "Material 22")
    
    // Case12 indicates Case 1 from User 2
    private val permissionCase12ToUser1 = Permission_Mocked(caseId = case1User2.id, userId = user1.id)
    private val permissionUser2casesToUser1 = Permission_Mocked(fullAccessUserId = user2.id, userId = user1.id, permissionType = PermissionType.GLOBAL, permissionVisibilityType = PermissionVisibilityType.TOTAL_ACCESS)
    private val permissionCase22InvisibleToUser1 = Permission_Mocked(userId = user1.id, caseId = case2User2.id, permissionVisibilityType = PermissionVisibilityType.INVISIBLE)
    // end section
    
    @Before
    fun setup() {
        getLawerCasesUseCase = GetLawerCasesUseCase(caseRepository, permissionsRepository)
        getLawersWithAccessToGivenCaseUseCase = GetLawersWithAccessToGivenCaseUseCase(caseRepository, permissionsRepository, usersRepository)
    }
    
    @After
    fun cleanUp() {
        clearAllMocks()
    }
    
    // section - Test Cases
    @Test
    fun `User1 can only see its cases`() = runTest {
        mockResponses(
            userList = listOf(user1, user2),
            caseList = listOf(case1User1, case1User2, case2User1, case2User2),
            permissionList = emptyList()
        )
        
        val expectedCaseVisibilityOfUser1 = listOf(case1User1, case2User1)
        getLawerCasesUseCase(userId = user1.id).test {
            val item = awaitItem()
            assertEquals(expectedCaseVisibilityOfUser1.size, item.size)
            expectedCaseVisibilityOfUser1.forEach { case ->
                assert(item.contains(case))
            }
            awaitComplete()
        }
    }
    
    @Test
    fun `User1 can only see its cases and case1 from user2`() = runTest {
        mockResponses(
            userList = listOf(user1, user2),
            caseList = listOf(case1User1, case1User2, case2User1, case2User2),
            permissionList = listOf(permissionCase12ToUser1)
        )
        
        val expectedCaseVisibilityOfUser1 = listOf(case1User1, case2User1, case1User2)
        
        getLawerCasesUseCase(userId = user1.id).test {
            val item = awaitItem()
            assertEquals(expectedCaseVisibilityOfUser1.size, item.size)
            expectedCaseVisibilityOfUser1.forEach { case ->
                assert(item.contains(case))
            }
            awaitComplete()
        }
    }
    
    @Test
    fun `For Case1 will be expected to have access from user1 and user2`() = runTest {
        mockResponses(
            userList = listOf(user1, user2),
            caseList = listOf(case1User1, case1User2, case2User1, case2User2),
            permissionList = listOf(permissionCase12ToUser1)
        )
        
        val expectedList: List<Pair<User, Permission?>> = listOf(
            user1 to permissionCase12ToUser1,
            user2 to null
        )
        
        getLawersWithAccessToGivenCaseUseCase(caseId = case1User2.id).test {
            val item = awaitItem()
            assert(item.any { it.second == null }) // assert we get the owner
            assertEquals(expectedList.size, item.size)
            expectedList.forEach { expected ->
                assert(item.contains(expected))
            }
            awaitComplete()
        }
    }
    
    @Test
    fun `User1 can see every User 2 case`() = runTest {
        mockResponses(
            userList = listOf(user1, user2),
            caseList = listOf(case1User1, case1User2, case2User1, case2User2),
            permissionList = listOf(permissionUser2casesToUser1)
        )
        
        val expectedCaseVisibilityOfUser1 = listOf(case1User1, case2User1, case1User2, case2User2)
        // Check as user1 we can see our cases and user2 cases
        getLawerCasesUseCase(userId = user1.id).test {
            val item = awaitItem()
            assertEquals(expectedCaseVisibilityOfUser1.size, item.size)
            expectedCaseVisibilityOfUser1.forEach { expected ->
                assert(item.contains(expected))
            }
            awaitComplete()
        }
        
        // Check we have ownerPermission on our cases, and TOTAL_ACCESS to user2 cases
        getLawersWithAccessToGivenCaseUseCase(caseId = case1User1.id).test {
            val expectedResult = listOf(
                user1 to null
            )
            val item = awaitItem()
            assertEquals(expectedResult, item)
            awaitComplete()
        }
        
        getLawersWithAccessToGivenCaseUseCase(caseId = case2User1.id).test {
            val expectedResult = listOf(
                user1 to null
            )
            val item = awaitItem()
            assertEquals(expectedResult, item)
            awaitComplete()
        }
        
        getLawersWithAccessToGivenCaseUseCase(caseId = case1User2.id).test {
            val expectedResult = listOf(
                user1 to permissionUser2casesToUser1,
                user2 to null
            )
            val item = awaitItem()
            assertEquals(expectedResult, item)
            awaitComplete()
        }
        
        getLawersWithAccessToGivenCaseUseCase(caseId = case2User2.id).test {
            val expectedResult = listOf(
                user1 to permissionUser2casesToUser1,
                user2 to null
            )
            val item = awaitItem()
            assertEquals(expectedResult, item)
            awaitComplete()
        }
    }
    
    @Test
    fun `User 1 can see every User 2 case except Case 2 of User 2`() = runTest {
        mockResponses(
            userList = listOf(user1, user2),
            caseList = listOf(case1User1, case1User2, case2User1, case2User2),
            permissionList = listOf(permissionUser2casesToUser1, permissionCase22InvisibleToUser1)
        )
        
        val expectedCaseVisibilityOfUser1 = listOf(case1User1, case2User1, case1User2, case2User2)
        
        // Check as user1 we can see our cases and user2 cases
        getLawerCasesUseCase(userId = user1.id).test {
            val item = awaitItem()
            assertEquals(expectedCaseVisibilityOfUser1.size, item.size)
            expectedCaseVisibilityOfUser1.forEach { expected ->
                assert(item.contains(expected))
            }
            awaitComplete()
        }
        
        // Check we have ownerPermission on our cases, and TOTAL_ACCESS to user2 cases
        getLawersWithAccessToGivenCaseUseCase(caseId = case1User1.id).test {
            val expectedResult = listOf(
                user1 to null
            )
            val item = awaitItem()
            assertEquals(expectedResult, item)
            awaitComplete()
        }
        
        getLawersWithAccessToGivenCaseUseCase(caseId = case2User1.id).test {
            val expectedResult = listOf(
                user1 to null
            )
            val item = awaitItem()
            assertEquals(expectedResult, item)
            awaitComplete()
        }
        
        getLawersWithAccessToGivenCaseUseCase(caseId = case1User2.id).test {
            val expectedResult = listOf(
                user1 to permissionUser2casesToUser1,
                user2 to null
            )
            val item = awaitItem()
            assertEquals(expectedResult, item)
            awaitComplete()
        }
        
        getLawersWithAccessToGivenCaseUseCase(caseId = case2User2.id).test {
            val expectedResult = listOf(
                user2 to null
            )
            val item = awaitItem()
            assertEquals(expectedResult, item)
            awaitComplete()
        }
    }
    // end section
    
    // section Helper
    private fun mockResponses(userList: List<User>, caseList: List<Case>, permissionList: List<Permission>) {
        userList.forEach { user ->
            every { usersRepository.getUser(user.id) } returns user
            every { caseRepository.getCasesOf(userId = user.id) } returns  caseList.filter { it.ownerId == user.id }.map { it.id }
            every { permissionsRepository.getPermissionsOf(userId = user.id) } returns permissionList.filter { it.userId == user.id }
        }
        
        caseList.forEach { case ->
            every { caseRepository.getCase(case.id) } returns caseList.find { it.id == case.id }
            every { permissionsRepository.getPermissionRelatedTo(caseId = case.id) } returns permissionList.filter { it.caseId == case.id }
        }
        
        every { permissionsRepository.getAllPermission() } returns permissionList.map { it.id }
        permissionList.forEach { permission ->
            every { permissionsRepository.getPermission(permission.id) } returns permission
        }
    }
    // end section
}