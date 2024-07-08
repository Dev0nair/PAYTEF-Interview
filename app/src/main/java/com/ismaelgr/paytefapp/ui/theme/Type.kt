package com.ismaelgr.paytefapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.ismaelgr.paytefapp.R

fun getGoogleFont(fontNameStr: String): FontFamily {
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    
    val fontName = GoogleFont(fontNameStr) // Lobster Two
    
    return FontFamily(
        Font(googleFont = fontName, fontProvider = provider)
    )
}

val fontFamily: FontFamily = getGoogleFont("Inter")

// Set of Material typography styles to start with
val Typography: (darkMode: Boolean) -> Typography = { darkMode ->
    Typography(
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            letterSpacing = 0.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.sp
        ),
        labelLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            letterSpacing = 0.sp,
            color = if (darkMode) Color.LightGray else Color.DarkGray
        )
    )
}
