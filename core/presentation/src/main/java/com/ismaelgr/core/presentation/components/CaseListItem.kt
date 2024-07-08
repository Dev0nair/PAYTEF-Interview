package com.ismaelgr.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ismaelgr.core.domain.entity.Case
import com.ismaelgr.core.domain.entity.CaseState
import com.ismaelgr.core.domain.entity.CaseType
import com.ismaelgr.core.domain.utils.formatToString
import com.ismaelgr.core.presentation.utils.onlyFirstUpper
import java.util.Calendar
import java.util.UUID

@Composable
fun CaseListItem(case: Case) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(15.dp))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            SecondaryTitle(text = case.material)
            Body(text = case.date)
            Description(text = case.type.name.onlyFirstUpper())
        }
        val icon: ImageVector = when (case.state) {
            CaseState.ACTIVE -> Icons.Default.Check
            CaseState.CANCELED -> Icons.Default.Warning
            CaseState.CLOSED -> Icons.Default.Close
        }
        
        Icon(imageVector = icon, contentDescription = case.state.name)
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    Column {
        CaseListItem(case = Case(id = UUID.randomUUID(), ownerId = UUID.randomUUID(), material = "Material", date = Calendar.getInstance().formatToString(), type = CaseType.entries.random(), state = CaseState.ACTIVE))
        CaseListItem(case = Case(id = UUID.randomUUID(), ownerId = UUID.randomUUID(), material = "Material", date = Calendar.getInstance().formatToString(), type = CaseType.entries.random(), state = CaseState.CANCELED))
        CaseListItem(case = Case(id = UUID.randomUUID(), ownerId = UUID.randomUUID(), material = "Material", date = Calendar.getInstance().formatToString(), type = CaseType.entries.random(), state = CaseState.CLOSED))
    }
}