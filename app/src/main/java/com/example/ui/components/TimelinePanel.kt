package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
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
import com.example.model.ThreatSeverity
import com.example.model.TimelineEvent
import com.example.ui.theme.*

@Composable
fun TimelinePanel(
    timelineEvents: List<TimelineEvent>,
    onAddEvidence: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 2.dp)
            .testTag("timeline_panel")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SocSectionHeader)
                .border(1.dp, SocCardBorder, RoundedCornerShape(4.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = null,
                    tint = SocCyan,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "INCIDENT TIMELINE",
                    color = SocTextMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
            }
            Text(
                text = "${timelineEvents.size} EVENTS RECORDED",
                color = SocTextMuted,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(timelineEvents) { event ->
                TimelineEventCard(
                    event = event,
                    onPin = {
                        onAddEvidence("Timeline [${event.timestamp}] ${event.hostname}: ${event.summary} (${event.details})")
                    }
                )
            }
        }
    }
}

@Composable
fun TimelineEventCard(
    event: TimelineEvent,
    onPin: () -> Unit
) {
    val indicatorColor = when (event.severity) {
        ThreatSeverity.CRITICAL -> SocRed
        ThreatSeverity.HIGH -> SocAmber
        ThreatSeverity.MEDIUM -> SocCyan
        ThreatSeverity.LOW -> SocEmerald
        ThreatSeverity.INFO -> SocTextMuted
        ThreatSeverity.CLEAN -> SocTerminalGreen
    }

    val isCritical = event.severity == ThreatSeverity.CRITICAL

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (isCritical) SocRedBorder else SocCardBorder,
                RoundedCornerShape(4.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isCritical) SocRedDim.copy(alpha = 0.15f) else SocSurface
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Timestamp and Dot Indicator
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(60.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(indicatorColor)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = event.timestamp,
                    color = SocTextPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = event.hostname,
                    color = SocBlue,
                    fontSize = 8.5.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(55.dp)
                    .background(SocCardBorder)
            )
            Spacer(modifier = Modifier.width(8.dp))

            // Event Details
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = event.eventType.uppercase(),
                        color = indicatorColor,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = event.source,
                        color = SocTextMuted,
                        fontSize = 8.5.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = event.summary,
                    color = if (isCritical) SocRedLight else Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = event.details,
                    color = SocTextSecondary,
                    fontSize = 9.5.sp,
                    lineHeight = 13.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onPin,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Pin to Notes",
                            fontSize = 9.sp,
                            color = SocCyan,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}
