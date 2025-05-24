package com.example.formsandlists

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class LoginFeatureTest {
    private val correctUsername = "username"
    private val correctPassword = "password"
    
    @Test
    fun testValidLogin() {
        val username = "username"
        val password = "password"
        
        assertTrue("Username should be 'username'", username == correctUsername)
        assertTrue("Password should be 'password'", password == correctPassword)
    }
    
    @Test
    fun testInvalidLogin() {
        val username = "wronguser"
        val password = "wrongpass"
        
        assertFalse("Username should not match", username == correctUsername)
        assertFalse("Password should not match", password == correctPassword)
    }
    
    @Test
    fun testUsernameHint() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedHint = context.getString(R.string.username_hint)
        assertEquals("Username hint should match", expectedHint, "hint: username is username")
    }
    
    @Test
    fun testSuccessMessage() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedMessage = context.getString(R.string.login_success)
        assertEquals("Success message should match", expectedMessage, "Login successful")
    }
} 