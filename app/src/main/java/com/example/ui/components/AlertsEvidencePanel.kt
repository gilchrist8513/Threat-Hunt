package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AlertItem
import com.example.model.ThreatSeverity
import com.example.ui.theme.*

@Composable
fun AlertsEvidencePanel(
    alerts: List<AlertItem>,
    onSelectAlert: (AlertItem) -> Unit,
    onToggleBookmark: (String) -> Unit,
    onNavigateToHost: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 2.dp)
            .testTag("alerts_evidence_panel")
    ) {
        // Section Header Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SocSectionHeader)
                .border(1.dp, SocCardBorder, RoundedCornerShape(4.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SECURITY ALERTS TRIAGE",
                color = SocRedLight,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.sp
            )
            Text(
                text = "${alerts.count { it.severity == ThreatSeverity.CRITICAL }} CRITICAL",
                color = SocRedLight,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(alerts) { alert ->
                AlertCard(
                    alert = alert,
                    onClick = { onSelectAlert(alert) },
                    onToggleBookmark = { onToggleBookmark(alert.id) },
                    onNavigateToHost = { onNavigateToHost(alert.hostId) }
                )
            }
        }
    }
}

@Composable
fun AlertCard(
    alert: AlertItem,
    onClick: () -> Unit,
    onToggleBookmark: () -> Unit,
    onNavigateToHost: () -> Unit
) {
    val isCritical = alert.severity == ThreatSeverity.CRITICAL

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (isCritical) SocRedBorder else SocCardBorder,
                RoundedCornerShape(4.dp)
            )
            .clickable(onClick = onClick)
            .testTag("alert_card_${alert.id.lowercase()}"),
        colors = CardDefaults.cardColors(
            containerColor = if (isCritical) SocRedDim.copy(alpha = 0.25f)
            else SocSurface
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(2.dp))
                            .background(if (isCritical) SocRedDim.copy(alpha = 0.8f) else SocAmber.copy(alpha = 0.2f))
                            .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = alert.severity.name,
                            color = if (isCritical) SocRedLight else SocAmber,
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Text(
                        text = alert.id,
                        color = SocBlue,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Text(
                    text = alert.timestamp,
                    color = SocTextMuted,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = alert.title,
                color = if (isCritical) Color.White else SocTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = alert.description,
                color = SocTextSecondary,
                fontSize = 10.5.sp,
                lineHeight = 14.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // MITRE Tag & Host
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MITRE: ${alert.mitreTechniqueId}",
                    color = SocBlue,
                    fontSize = 9.5.sp,
                    fontFamily = FontFamily.Monospace
                )

                Text(
                    text = "Target: ${alert.hostname}",
                    color = SocTextPrimary,
                    fontSize = 9.5.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            HorizontalDivider(color = SocCardBorder)
            Spacer(modifier = Modifier.height(4.dp))

            // Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onNavigateToHost,
                    colors = ButtonDefaults.buttonColors(containerColor = SocSlateButton),
                    shape = RoundedCornerShape(3.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.border(1.dp, SocCardBorderHighlight, RoundedCornerShape(3.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = SocCyan,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "INVESTIGATE HOST",
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SocTextPrimary
                    )
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(26.dp)
                ) {
                    Icon(
                        imageVector = if (alert.isBookmarkedEvidence) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark Evidence",
                        tint = if (alert.isBookmarkedEvidence) SocEmerald else SocTextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
