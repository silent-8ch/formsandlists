package com.p4u1.formsandlists.android

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.PI
import kotlin.random.Random

data class ConfettiParticle(
    var position: Offset,
    var velocity: Offset,
    var rotation: Float,
    var rotationVelocity: Float,
    var size: Float,
    var color: Color,
    var alpha: Float = 1f,
    var oscillationAmplitude: Float = Random.nextFloat() * 50f + 25f,
    var oscillationOffset: Float = Random.nextFloat() * 2f * PI.toFloat()
) {
    private var time: Float = 0f
    
    fun update(deltaTime: Float) {
        time += deltaTime
        
        // Gentler gravity
        velocity = velocity.copy(y = velocity.y + 200f * deltaTime)
        
        // Add gentle sideways oscillation
        val oscillation = kotlin.math.sin(time * 2f + oscillationOffset) * oscillationAmplitude * deltaTime
        position = position + velocity * deltaTime + Offset(oscillation, 0f)
        
        // Slower rotation
        rotation += rotationVelocity * deltaTime * 0.5f
        
        // Slower fade out
        alpha = (alpha - deltaTime * 0.3f).coerceIn(0f, 1f)
    }
    
    companion object {
        fun create(startPosition: Offset, colors: List<Color>): ConfettiParticle {
            return ConfettiParticle(
                position = startPosition,
                velocity = Offset(
                    Random.nextFloat() * 100f - 50f, // Gentler horizontal spread
                    Random.nextFloat() * -300f - 100f // Gentler upward velocity
                ),
                rotation = Random.nextFloat() * 360f,
                rotationVelocity = Random.nextFloat() * 360f - 180f, // Slower rotation
                size = Random.nextFloat() * 8f + 4f, // Smaller pieces
                color = colors.random()
            )
        }
    }
} 