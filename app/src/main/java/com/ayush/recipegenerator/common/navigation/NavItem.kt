package com.ayush.recipegenerator.common.navigation

import androidx.compose.ui.graphics.painter.Painter

data class NavItem(
    var title: String,
    var icon: Painter,
    var screenRoute: String,
    var selected: Boolean
)
